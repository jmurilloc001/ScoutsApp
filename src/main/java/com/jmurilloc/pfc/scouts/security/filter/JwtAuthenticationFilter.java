package com.jmurilloc.pfc.scouts.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmurilloc.pfc.scouts.entities.User;
import com.jmurilloc.pfc.scouts.exceptions.AttemptAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.jmurilloc.pfc.scouts.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class); //Aquí estoy creando un json
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new AttemptAuthenticationException("Fallo en la atentificación.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }
    //Este métdo es si tdo sale bien
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles =  authResult.getAuthorities();

        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username",username)
                .build();

        String token = Jwts.builder()
                .subject(username)
                .claims(claims) //Esto es para pasarle los roles
                .expiration(new Date(System.currentTimeMillis() + 3600000)) //la fecha actual más 1 hora, es lo que va a durar el token
                .issuedAt(new Date()) //Fecha en la que se creeo el token
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token); //Vienen de la clase TokenJwtConfig (Creada por mi)

        Map<String, String> body = new HashMap<>();
        body.put("token",token);
        body.put("username",username);
        body.put("message","Hola " + username + " has iniciado sesión con exito");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE); //De la clase creada por mi con las constantes
        response.setStatus(200);
    }
    //Si falla la autentificación
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message","Error en la autenticacion. Username o password incorrectos!");
        body.put("error",failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}

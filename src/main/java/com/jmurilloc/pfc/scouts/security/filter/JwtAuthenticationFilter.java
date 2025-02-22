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
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;
import java.util.*;

import static com.jmurilloc.pfc.scouts.security.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService; // Inyectamos el servicio de detalles de usuario

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService; // Inyectar el UserDetailsService
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (request.getHeader("Authorization") != null) {
            // La autenticación ya fue realizada, no intentar de nuevo
            return super.attemptAuthentication(request, response);
        }
        // Proceder con la lógica normal si no hay un token de autorización
        User user = null;
        String username = null;
        String password = null;

        try {
            // Deserializar el JSON enviado en el cuerpo de la solicitud
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (IOException e) {
            throw new AttemptAuthenticationException("Fallo en la autentificación.");
        }

        // Crear el token de autenticación
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }


    // Este método es llamado si la autenticación es exitosa
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        // Crear los claims para el JWT, agregando las autoridades (roles)
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .add("username", username)
                .build();

        // Crear el JWT con los claims y la fecha de expiración
        String token = Jwts.builder()
                .subject(username)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // Expiración en 1 hora
                .issuedAt(new Date()) // Fecha de emisión
                .signWith(SECRET_KEY) // Firmado con la clave secreta
                .compact();

        // Agregar el token al encabezado de la respuesta
        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        // Preparar la respuesta con el token y un mensaje de éxito
        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", "Hola " + username + ", has iniciado sesión con éxito");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(CONTENT_TYPE); // Tipo de contenido (definido en tu clase de constantes)
        response.setStatus(200);
    }

    // Este método se ejecuta si la autenticación falla
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticación. Username o password incorrectos!");
        body.put("error", failed.getMessage());
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }
}

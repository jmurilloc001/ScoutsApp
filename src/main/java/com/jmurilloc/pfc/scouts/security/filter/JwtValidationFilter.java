package com.jmurilloc.pfc.scouts.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmurilloc.pfc.scouts.security.SimpleGrantedAuthorityJsonCreator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.jmurilloc.pfc.scouts.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //Lo primero es obtener el token. (Para eso consigo la cabecera)
        String header = request.getHeader(HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(PREFIX_TOKEN)){ //Por si el recurso es público, y no necesita autorización
            chain.doFilter(request,response); //PAra que siga sin problema
            return;
        }
        String token = header.replace(PREFIX_TOKEN,""); //Que me quite el prefijo
        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String username = (String) claims.get("username");
            Object authoritiesClaims = claims.get("authorities"); //El problema que tiene esto, es que el constructor de SimpleGrantedAuthority, tiene el parámetro llamado con role, y por lo tanto, el json no lo encuentra como authority.
            //Lo que se hace es crear un nuevo constructor, con unas anotaciones, para que el json, lo busque con authority

            Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                    new ObjectMapper()
                            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class) //Esto lo que hace, es implementar también el nuevo constructor
                            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            //Aqui solo valido el token, por lo que me da igual la contraseña
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request,response);
        } catch (JwtException e) {
            Map<String,String> body = new HashMap<>();
            body.put("error",e.getMessage());
            body.put("message","El token JWT es invalido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(CONTENT_TYPE);
        }
    }
}

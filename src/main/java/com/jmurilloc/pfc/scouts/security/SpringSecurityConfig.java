package com.jmurilloc.pfc.scouts.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests( authz -> authz
                        .requestMatchers(HttpMethod.GET,"/api/users").permitAll()//la ruta /users no necesita autentificación
                        .requestMatchers(HttpMethod.POST,"/api/users").permitAll()
                        .anyRequest().authenticated()) //Las demás rutas si
                .csrf(config -> config.disable()) //TODO Esto hay que quitarlo después (Esta linea)
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Por defecto se guarda la sesión de conectado en la sesión http, pero de esta manera, ya no es así, y se guarda en el token
                .build();
    }
}

package com.jmurilloc.pfc.scouts.security;

import com.jmurilloc.pfc.scouts.security.filter.JwtAuthenticationFilter;
import com.jmurilloc.pfc.scouts.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity( prePostEnabled = true )
public class SpringSecurityConfig
{
    
    private AuthenticationConfiguration authenticationConfiguration;
    
    
    @Autowired
    private void setAuthenticationConfiguration( AuthenticationConfiguration authenticationConfiguration )
    {
        this.authenticationConfiguration = authenticationConfiguration;
    }
    
    
    @Bean
    public AuthenticationManager authenticationManager() throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    
    
    @Bean
    public SecurityFilterChain filterChain( HttpSecurity http ) throws Exception
    {
        return http.authorizeHttpRequests(
                        authz -> authz.requestMatchers( HttpMethod.GET, "/users", "/meetings", "/affiliates", "/users/{username}/roles", "/news", "/news/{id}", "/news/paginated", "/send-email" ).permitAll()
                                .requestMatchers( HttpMethod.POST, "/users/register" ).permitAll().anyRequest().authenticated() )
                .addFilter( new JwtAuthenticationFilter( authenticationManager() ) )  // Filtro JWT de autenticación
                .addFilter( new JwtValidationFilter( authenticationManager() ) ) // Filtro JWT de validación
                .csrf( csrf -> csrf.disable() ) // Desactivando CSRF, ya que se usa JWT
                .cors( cors -> cors.configurationSource( corsConfigurationSource() ) )
                .sessionManagement( session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) ) // No utilizar sesión HTTP, solo tokens
                .build();
    }
    
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns( List.of( "http://localhost:5173" ) );
        config.setAllowedMethods( Arrays.asList( "GET", "POST", "PUT", "DELETE", "PATCH" ) );
        config.setAllowedHeaders( Arrays.asList( "Authorization", "Content-Type" ) );
        config.setAllowCredentials( true );
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration( "/**", config );
        return source;
    }
}
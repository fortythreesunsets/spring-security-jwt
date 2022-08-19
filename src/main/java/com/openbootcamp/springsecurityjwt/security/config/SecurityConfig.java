package com.openbootcamp.springsecurityjwt.security.config;

import com.openbootcamp.springsecurityjwt.security.jwt.JwtAuthEntryPoint;
import com.openbootcamp.springsecurityjwt.security.jwt.JwtRequestFilter;
import com.openbootcamp.springsecurityjwt.security.service.UserDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Clase para la configuración de seguridad Spring Security
 */

@Configuration
@EnableWebSecurity  // Permite a Spring aplicar esta configuración a la configuración de seguridad global
public class SecurityConfig {

    private UserDetailsServiceImpl userDetailsService;
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtRequestFilter authenticationJwtTokenFilter() {
        return new JwtRequestFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configuración de AuthenticationManagerBuilder
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
         // Obtener AuthenticationManager
//        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
                .antMatchers("/api/hello/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated().and()
//                .addFilter(getAuthenticationFilter())  // Autenticación de usuario con URL de login personalizada
//                .addFilter(authenticationJwtTokenFilter())  // Autorización de usuario con JWT
                .authenticationManager(authenticationManager(new AuthenticationConfiguration()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200", "https://spring-security-jwt-cars-virid.vercel.app"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("Access-Control-Allow-Origin", "X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // Configuración de la URL personalizada
    //Customize the Spring default /login url to overwrite it with /auth.
//    @Bean
//    public AuthenticationFilter getAuthenticationFilter() throws Exception {
//        final AuthenticationFilter filter = new AuthenticationFilter(configure().authenticationManager);
//        filter.setFilterProcessesUrl("/api/auth/login");
//
//        return filter;
//    }

}

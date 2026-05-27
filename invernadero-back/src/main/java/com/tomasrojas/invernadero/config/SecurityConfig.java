/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  SecurityConfig.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Configuración de Spring Security con OAuth2 condicional y CORS.
 *              En desarrollo (oauth2-enabled: false) todas las rutas son públicas.
 *              En producción requiere autenticación con Google para /api/**.
 */
package com.tomasrojas.invernadero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración de seguridad HTTP de la aplicación.
 *
 * <p>El comportamiento depende de {@code app.security.oauth2-enabled}:
 * <ul>
 *   <li><b>false</b> (desarrollo): todas las rutas son públicas, sin login.</li>
 *   <li><b>true</b> (producción): {@code /api/**} requiere sesión OAuth2 de Google.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AppProperties appProperties;

    /**
     * @param appProperties propiedades de la aplicación con configuración de CORS y OAuth2
     */
    public SecurityConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * Define la cadena de filtros de seguridad HTTP.
     *
     * @param http constructor de configuración de Spring Security
     * @return cadena de filtros configurada
     * @throws Exception si hay un error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF deshabilitado: la API es stateless desde el punto de vista REST,
        // la sesión se maneja con cookies SameSite gestionadas por Spring Session.
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        if (appProperties.getSecurity().isOauth2Enabled()) {
            http.authorizeHttpRequests(auth -> auth
                    // Rutas públicas siempre accesibles
                    .requestMatchers(
                            "/swagger-ui/**", "/v3/api-docs/**",
                            "/actuator/**",
                            "/api/v1/auth/**",
                            "/oauth2/**", "/login/oauth2/**", "/logout"
                    ).permitAll()
                    // Todo lo demás requiere autenticación
                    .anyRequest().authenticated()
            );

            http.oauth2Login(oauth2 -> oauth2
                    .successHandler((request, response, authentication) ->
                            response.sendRedirect(appProperties.getOauth2().getPostLoginRedirect())
                    )
            );

            http.logout(logout -> logout
                    .logoutSuccessUrl(appProperties.getOauth2().getPostLoginRedirect())
            );
        } else {
            // Modo desarrollo: sin autenticación
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        return http.build();
    }

    /**
     * Configura CORS para permitir peticiones del frontend React.
     *
     * <p>Los orígenes se leen de {@code app.cors.allowed-origins} y pueden
     * ser una lista separada por comas para soportar múltiples entornos.</p>
     *
     * @return fuente de configuración CORS aplicada a todas las rutas de la API
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Soporta múltiples orígenes separados por coma (ej. local + staging)
        List<String> origins = Arrays.asList(
                appProperties.getCors().getAllowedOrigins().split(",")
        );
        config.setAllowedOriginPatterns(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  TaigaClientConfig.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Configura el cliente REST para consumir la API de Taiga.io.
 *              Inyecta el token de autenticación personal en cada petición.
 */
package com.tomasrojas.invernadero.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configura el bean {@link RestClient} utilizado para comunicarse con la API de Taiga.io.
 *
 * <p>Todas las peticiones incluyen el header {@code Authorization: Bearer <token>}
 * usando el token personal configurado en {@code app.taiga.personal-access-token}.</p>
 */
@Configuration
public class TaigaClientConfig {

    private final AppProperties appProperties;

    /**
     * @param appProperties propiedades de la aplicación con la configuración de Taiga
     */
    public TaigaClientConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * Crea el {@link RestClient} preconfigurado para la API de Taiga.
     *
     * @return cliente REST listo para usar con autenticación Bearer incluida
     */
    @Bean("taigaRestClient")
    public RestClient taigaRestClient() {
        String baseUrl = appProperties.getTaiga().getBaseUrl()
                .replaceAll("/+$", ""); // Elimina slashes finales para evitar doble barra

        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(
                        "Authorization",
                        "Bearer " + appProperties.getTaiga().getPersonalAccessToken()
                )
                .build();
    }
}

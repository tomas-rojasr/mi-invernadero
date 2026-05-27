/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  OpenApiConfig.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Configuración de Springdoc OpenAPI para generar la documentación
 *              interactiva del API en Swagger UI.
 */
package com.tomasrojas.invernadero.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura los metadatos del API que aparecen en la página de Swagger UI
 * ({@code /swagger-ui.html}).
 *
 * <p>Springdoc descubre automáticamente todos los controllers anotados con
 * {@code @RestController} y genera la especificación OpenAPI 3.0.</p>
 */
@Configuration
public class OpenApiConfig {

    /**
     * Define los metadatos generales del API: título, versión, descripción y contacto.
     *
     * @return instancia de {@link OpenAPI} con la información del proyecto
     */
    @Bean
    public OpenAPI invernaderoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Invernadero Inteligente API")
                        .version("1.0.0")
                        .description("""
                                API REST para la gestión de zonas, cultivos, lecturas ambientales
                                y umbrales de un invernadero inteligente.

                                Documentación generada automáticamente con Springdoc OpenAPI 3.0.
                                """)
                        .contact(new Contact()
                                .name("Tomas Rojas")
                                .email("tomas.rojas@example.com")
                        )
                );
    }
}

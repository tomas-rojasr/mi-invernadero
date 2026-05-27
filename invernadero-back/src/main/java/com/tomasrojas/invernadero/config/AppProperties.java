/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  AppProperties.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Propiedades personalizadas del sistema mapeadas desde application.yaml.
 *              Centraliza la configuración de CORS, OAuth2 y Taiga en un único bean tipado.
 */
package com.tomasrojas.invernadero.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Bean de configuración que mapea el bloque {@code app.*} de {@code application.yaml}.
 *
 * <p>Usar {@code @ConfigurationProperties} en lugar de {@code @Value} permite
 * agrupar propiedades relacionadas, facilita el autocompletado en el IDE
 * y hace más sencillo el testeo.</p>
 */
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Cors cors = new Cors();
    private final Security security = new Security();
    private final Oauth2 oauth2 = new Oauth2();
    private final Taiga taiga = new Taiga();
    private final SampleData sampleData = new SampleData();

    /** @return configuración de CORS */
    public Cors getCors() { return cors; }

    /** @return configuración de seguridad */
    public Security getSecurity() { return security; }

    /** @return configuración de OAuth2 post-login */
    public Oauth2 getOauth2() { return oauth2; }

    /** @return configuración de la integración con Taiga */
    public Taiga getTaiga() { return taiga; }

    /** @return configuración de datos de demostración */
    public SampleData getSampleData() { return sampleData; }

    // ── Clases de configuración anidadas ──────────────────────────────────

    /**
     * Configuración de CORS — orígenes permitidos para el frontend.
     * Mapeado desde {@code app.cors.*}.
     */
    public static class Cors {
        /** Orígenes permitidos separados por coma (ej. http://localhost:5173). */
        private String allowedOrigins = "http://localhost:5173";

        /** @return los orígenes CORS permitidos */
        public String getAllowedOrigins() { return allowedOrigins; }

        /** @param allowedOrigins orígenes CORS permitidos separados por coma */
        public void setAllowedOrigins(String allowedOrigins) { this.allowedOrigins = allowedOrigins; }
    }

    /**
     * Configuración de seguridad.
     * Mapeado desde {@code app.security.*}.
     */
    public static class Security {
        /** Si es false, todas las rutas son públicas (útil en desarrollo sin credenciales Google). */
        private boolean oauth2Enabled = false;

        /** @return true si OAuth2 está habilitado */
        public boolean isOauth2Enabled() { return oauth2Enabled; }

        /** @param oauth2Enabled activa o desactiva OAuth2 */
        public void setOauth2Enabled(boolean oauth2Enabled) { this.oauth2Enabled = oauth2Enabled; }
    }

    /**
     * Configuración post-login de OAuth2.
     * Mapeado desde {@code app.oauth2.*}.
     */
    public static class Oauth2 {
        /** URL del frontend a la que redirigir después de un login exitoso. */
        private String postLoginRedirect = "http://localhost:5173/panel";

        /** @return URL de redirección post-login */
        public String getPostLoginRedirect() { return postLoginRedirect; }

        /** @param postLoginRedirect URL de redirección post-login */
        public void setPostLoginRedirect(String postLoginRedirect) {
            this.postLoginRedirect = postLoginRedirect;
        }
    }

    /**
     * Configuración de la integración con Taiga.io.
     * Mapeado desde {@code app.taiga.*}.
     */
    public static class Taiga {
        /** URL base de la API de Taiga. */
        private String baseUrl = "https://api.taiga.io/api/v1";

        /** Token de acceso personal generado en Taiga → Perfil → Aplicaciones. */
        private String personalAccessToken = "";

        /** @return URL base de la API de Taiga */
        public String getBaseUrl() { return baseUrl; }

        /** @param baseUrl URL base de la API de Taiga */
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }

        /** @return token de acceso personal de Taiga */
        public String getPersonalAccessToken() { return personalAccessToken; }

        /** @param personalAccessToken token de acceso personal de Taiga */
        public void setPersonalAccessToken(String personalAccessToken) {
            this.personalAccessToken = personalAccessToken;
        }
    }

    /**
     * Configuración de datos de demostración.
     * Mapeado desde {@code app.sample-data.*}.
     */
    public static class SampleData {
        /** Si es true, carga datos de demostración al arrancar si la BD está vacía. */
        private boolean enabled = true;

        /** @return true si los datos de demostración están habilitados */
        public boolean isEnabled() { return enabled; }

        /** @param enabled activa o desactiva la carga de datos de demostración */
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
    }
}

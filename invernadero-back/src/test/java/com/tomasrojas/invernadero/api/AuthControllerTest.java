/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  AuthControllerTest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Pruebas del endpoint de estado de autenticación OAuth2.
 *              Verifica el comportamiento con y sin OAuth2 habilitado.
 */
package com.tomasrojas.invernadero.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas del controller {@link AuthController}.
 *
 * <p>En el perfil de test {@code app.security.oauth2-enabled=false},
 * el endpoint debe responder siempre con {@code authenticated: true}.</p>
 */
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/auth/status debe retornar HTTP 200")
    void status_debeRetornarOk() throws Exception {
        mockMvc.perform(get("/api/v1/auth/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/v1/auth/status sin OAuth2 debe retornar authenticated=true")
    void status_sinOauth2_debeRetornarAutenticado() throws Exception {
        // En test, oauth2-enabled=false → el servidor responde authenticated=true sin sesión
        mockMvc.perform(get("/api/v1/auth/status")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated").value(true))
                .andExpect(jsonPath("$.oauth2Enabled").value(false));
    }
}

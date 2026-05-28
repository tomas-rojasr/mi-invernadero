/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaControllerTest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Pruebas de integración del controller de zonas usando MockMvc.
 *              Verifica que los endpoints HTTP responden correctamente.
 */
package com.tomasrojas.invernadero.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomasrojas.invernadero.api.mapper.WebDtoMapper;
import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.service.ZonaApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas del controller {@link ZonaController} con {@code @WebMvcTest}.
 *
 * <p>{@code @WebMvcTest} carga solo la capa web (controllers, filters, converters)
 * sin levantar el contexto completo de Spring ni conectarse a la base de datos.
 * Los servicios se simulan con {@code @MockitoBean}.</p>
 */
@WebMvcTest(controllers = ZonaController.class)
@AutoConfigureMockMvc(addFilters = false)
class ZonaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ZonaApplicationService zonaService;

    @MockitoBean
    private WebDtoMapper webDtoMapper;

    @Test
    @DisplayName("GET /api/v1/zonas debe retornar lista de zonas con HTTP 200")
    void listar_debeRetornarListaConStatus200() throws Exception {
        Zona zona = new Zona(UUID.randomUUID(), "Zona Norte", "Descripción", null, null, null, Instant.now());
        when(zonaService.listarTodas()).thenReturn(List.of(zona));
        when(webDtoMapper.toZonaResponse(zona)).thenCallRealMethod();

        mockMvc.perform(get("/api/v1/zonas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        verify(zonaService, times(1)).listarTodas();
    }

    @Test
    @DisplayName("POST /api/v1/zonas debe crear zona y retornar HTTP 201")
    void crear_debeRetornarZonaCreada() throws Exception {
        Zona zona = new Zona(UUID.randomUUID(), "Zona Sur", "Nueva zona", null, null, null, Instant.now());
        when(zonaService.crear(anyString(), anyString(), any(), any(), any())).thenReturn(zona);
        when(webDtoMapper.toZonaResponse(zona)).thenCallRealMethod();

        String body = """
                { "nombre": "Zona Sur", "descripcion": "Nueva zona" }
                """;

        mockMvc.perform(post("/api/v1/zonas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        verify(zonaService, times(1)).crear(eq("Zona Sur"), eq("Nueva zona"), isNull(), isNull(), isNull());
    }

    @Test
    @DisplayName("POST /api/v1/zonas con nombre vacío debe retornar HTTP 400")
    void crear_conNombreVacio_debeRetornar400() throws Exception {
        String body = """
                { "nombre": "", "descripcion": "Sin nombre" }
                """;

        mockMvc.perform(post("/api/v1/zonas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(zonaService);
    }

    @Test
    @DisplayName("DELETE /api/v1/zonas/{id} debe retornar HTTP 204 al eliminar")
    void eliminar_debeRetornar204() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(zonaService).eliminar(id);

        mockMvc.perform(delete("/api/v1/zonas/{id}", id))
                .andExpect(status().isNoContent());

        verify(zonaService, times(1)).eliminar(id);
    }

    @Test
    @DisplayName("DELETE /api/v1/zonas/{id} debe retornar HTTP 404 si la zona no existe")
    void eliminar_zonaInexistente_debeRetornar404() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new RecursoNoEncontradoException("Zona", id)).when(zonaService).eliminar(id);

        mockMvc.perform(delete("/api/v1/zonas/{id}", id))
                .andExpect(status().isNotFound());
    }
}

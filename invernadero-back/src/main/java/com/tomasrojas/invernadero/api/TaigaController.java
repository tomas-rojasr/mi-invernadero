/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  TaigaController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST que consulta historias de usuario en Taiga.io
 *              para validar la conectividad y el estado del proyecto académico.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.model.taiga.TaigaHistoriaUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/**
 * Controller REST que actúa como proxy hacia la API de Taiga.io.
 *
 * <p>Cumple con el criterio 9 de la rúbrica: conectividad con Taiga para
 * validar historias de usuario y criterios de aceptación del proyecto.</p>
 */
@RestController
@RequestMapping("/api/v1/taiga")
@Tag(name = "Taiga", description = "Consulta de historias de usuario en Taiga.io")
public class TaigaController {

    private final RestClient taigaRestClient;

    /**
     * @param taigaRestClient cliente REST preconfigurado para Taiga, inyectado por nombre
     */
    public TaigaController(@Qualifier("taigaRestClient") RestClient taigaRestClient) {
        this.taigaRestClient = taigaRestClient;
    }

    /**
     * Consulta una historia de usuario en Taiga por su identificador numérico.
     *
     * @param id identificador numérico de la historia en Taiga
     * @return la historia de usuario si existe
     */
    @GetMapping("/historias/{id}")
    @Operation(summary = "Consultar una historia de usuario en Taiga.io")
    public TaigaHistoriaUsuario obtenerHistoria(@PathVariable Long id) {
        try {
            return taigaRestClient.get()
                    .uri("/userstories/{id}", id)
                    .retrieve()
                    .body(TaigaHistoriaUsuario.class);
        } catch (HttpClientErrorException.NotFound e) {
            ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND,
                    "Historia de usuario con id " + id + " no encontrada en Taiga"
            );
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.NOT_FOUND, detail.getDetail()
            );
        }
    }
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  TaigaController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-20
 * Descripción: Controller REST que consulta historias de usuario en Taiga.io
 *              para validar la conectividad y el estado del proyecto académico.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.config.AppProperties;
import com.tomasrojas.invernadero.model.taiga.TaigaHistoriaUsuario;
import com.tomasrojas.invernadero.model.taiga.TaigaProject;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

/**
 * Controller REST que actúa como proxy hacia la API de Taiga.io.
 */
@RestController
@RequestMapping("/api/v1/taiga")
@Tag(name = "Taiga", description = "Consulta de historias de usuario en Taiga.io")
public class TaigaController {

    private final RestClient taigaRestClient;
    private final AppProperties appProperties;

    public TaigaController(@Qualifier("taigaRestClient") RestClient taigaRestClient,
                           AppProperties appProperties) {
        this.taigaRestClient = taigaRestClient;
        this.appProperties = appProperties;
    }

    @GetMapping("/historias")
    @Operation(summary = "Listar todas las historias del proyecto en Taiga.io")
    public List<TaigaHistoriaUsuario> listarHistorias() {
        String slug = appProperties.getTaiga().getProjectSlug();
        if (slug == null || slug.isBlank()) {
            return List.of();
        }
        try {
            TaigaProject project = taigaRestClient.get()
                    .uri("/projects/by_slug?slug={slug}", slug)
                    .retrieve()
                    .body(TaigaProject.class);
            TaigaHistoriaUsuario[] historias = taigaRestClient.get()
                    .uri("/userstories?project={id}", project.getId())
                    .retrieve()
                    .body(TaigaHistoriaUsuario[].class);
            return historias != null ? Arrays.asList(historias) : List.of();
        } catch (HttpClientErrorException.NotFound e) {
            return List.of();
        }
    }

    @GetMapping("/historias/{id}")
    @Operation(summary = "Consultar una historia de usuario en Taiga.io por ID")
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

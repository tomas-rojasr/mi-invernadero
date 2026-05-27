/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST para la gestión de cultivos por zona.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.api.dto.CrearCultivoRequest;
import com.tomasrojas.invernadero.api.dto.CultivoResponse;
import com.tomasrojas.invernadero.api.mapper.WebDtoMapper;
import com.tomasrojas.invernadero.service.CultivoApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para cultivos anidados bajo una zona.
 * Prefijo: {@code /api/v1/zonas/{zonaId}/cultivos}.
 */
@RestController
@RequestMapping("/api/v1/zonas/{zonaId}/cultivos")
@Tag(name = "Cultivos", description = "Gestión de cultivos por zona")
public class CultivoController {

    private final CultivoApplicationService service;
    private final WebDtoMapper mapper;

    /**
     * @param service servicio de aplicación de cultivos
     * @param mapper  conversor dominio ↔ DTO
     */
    public CultivoController(CultivoApplicationService service, WebDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Retorna todos los cultivos de una zona.
     *
     * @param zonaId identificador de la zona
     * @return lista de cultivos con HTTP 200
     */
    @GetMapping
    @Operation(summary = "Listar cultivos de una zona")
    public List<CultivoResponse> listar(@PathVariable UUID zonaId) {
        return service.listarPorZona(zonaId).stream()
                .map(mapper::toCultivoResponse)
                .toList();
    }

    /**
     * Registra un nuevo cultivo en una zona.
     *
     * @param zonaId  identificador de la zona
     * @param request datos del cultivo
     * @return cultivo creado con HTTP 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar un cultivo en una zona")
    public CultivoResponse crear(
            @PathVariable UUID zonaId,
            @Valid @RequestBody CrearCultivoRequest request) {
        return mapper.toCultivoResponse(
                service.crear(zonaId, request.nombre(), request.variedad(),
                        request.notas(), request.plantadoEn())
        );
    }
}

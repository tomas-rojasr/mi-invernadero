/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST para el registro y consulta de lecturas ambientales.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.api.dto.LecturaResponse;
import com.tomasrojas.invernadero.api.dto.RegistrarLecturaRequest;
import com.tomasrojas.invernadero.api.mapper.WebDtoMapper;
import com.tomasrojas.invernadero.service.LecturaApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para lecturas ambientales anidadas bajo una zona.
 * Prefijo: {@code /api/v1/zonas/{zonaId}/lecturas}.
 */
@RestController
@RequestMapping("/api/v1/zonas/{zonaId}/lecturas")
@Tag(name = "Lecturas", description = "Registro y consulta de lecturas ambientales por zona")
public class LecturaController {

    private final LecturaApplicationService service;
    private final WebDtoMapper mapper;

    /**
     * @param service servicio de aplicación de lecturas
     * @param mapper  conversor dominio ↔ DTO
     */
    public LecturaController(LecturaApplicationService service, WebDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Retorna las últimas lecturas ambientales de una zona.
     *
     * @param zonaId identificador de la zona
     * @param limite número máximo de lecturas a retornar (por defecto 50)
     * @return lista de lecturas con HTTP 200
     */
    @GetMapping
    @Operation(summary = "Listar lecturas de una zona")
    public List<LecturaResponse> listar(
            @PathVariable UUID zonaId,
            @RequestParam(required = false, defaultValue = "50") int limite) {
        return service.listarPorZona(zonaId, limite).stream()
                .map(mapper::toLecturaResponse)
                .toList();
    }

    /**
     * Registra una nueva lectura ambiental en una zona.
     *
     * @param zonaId  identificador de la zona
     * @param request datos de la lectura
     * @return lectura registrada con HTTP 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una lectura ambiental en una zona")
    public LecturaResponse registrar(
            @PathVariable UUID zonaId,
            @Valid @RequestBody RegistrarLecturaRequest request) {
        return mapper.toLecturaResponse(
                service.registrar(zonaId, request.tipo(), request.valor())
        );
    }

    /**
     * Elimina una lectura por su identificador.
     *
     * @param zonaId    identificador de la zona (mantiene la URL REST)
     * @param lecturaId identificador de la lectura a eliminar
     */
    @DeleteMapping("/{lecturaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una lectura ambiental")
    public void eliminar(@PathVariable UUID zonaId, @PathVariable UUID lecturaId) {
        service.eliminar(lecturaId);
    }
}

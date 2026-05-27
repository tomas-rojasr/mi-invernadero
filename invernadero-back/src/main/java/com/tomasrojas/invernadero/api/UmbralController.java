/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST para la gestión de umbrales ambientales por zona.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.api.dto.DefinirUmbralRequest;
import com.tomasrojas.invernadero.api.dto.UmbralResponse;
import com.tomasrojas.invernadero.api.mapper.WebDtoMapper;
import com.tomasrojas.invernadero.service.UmbralApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para umbrales ambientales anidados bajo una zona.
 * Prefijo: {@code /api/v1/zonas/{zonaId}/umbrales}.
 */
@RestController
@RequestMapping("/api/v1/zonas/{zonaId}/umbrales")
@Tag(name = "Umbrales", description = "Definición de umbrales ambientales por zona")
public class UmbralController {

    private final UmbralApplicationService service;
    private final WebDtoMapper mapper;

    /**
     * @param service servicio de aplicación de umbrales
     * @param mapper  conversor dominio ↔ DTO
     */
    public UmbralController(UmbralApplicationService service, WebDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Retorna todos los umbrales configurados para una zona.
     *
     * @param zonaId identificador de la zona
     * @return lista de umbrales con HTTP 200
     */
    @GetMapping
    @Operation(summary = "Listar umbrales de una zona")
    public List<UmbralResponse> listar(@PathVariable UUID zonaId) {
        return service.listarPorZona(zonaId).stream()
                .map(mapper::toUmbralResponse)
                .toList();
    }

    /**
     * Define o actualiza el umbral de una métrica para una zona (upsert).
     * Si ya existe un umbral del mismo tipo, se actualiza; si no, se crea.
     *
     * @param zonaId  identificador de la zona
     * @param request datos del umbral
     * @return umbral creado o actualizado con HTTP 200
     */
    @PutMapping
    @Operation(summary = "Definir o actualizar un umbral ambiental (upsert por tipo)")
    public UmbralResponse definir(
            @PathVariable UUID zonaId,
            @Valid @RequestBody DefinirUmbralRequest request) {
        return mapper.toUmbralResponse(
                service.definir(zonaId, request.tipo(), request.valorMin(), request.valorMax())
        );
    }

    /**
     * Elimina un umbral por su identificador.
     *
     * @param zonaId   identificador de la zona (no usado en la operación, mantiene la URL REST)
     * @param umbralId identificador del umbral a eliminar
     */
    @DeleteMapping("/{umbralId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un umbral ambiental")
    public void eliminar(@PathVariable UUID zonaId, @PathVariable UUID umbralId) {
        service.eliminar(umbralId);
    }
}

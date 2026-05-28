/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST para la gestión de zonas del invernadero.
 *              Expone los endpoints de creación, consulta y eliminación.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.api.dto.CrearZonaRequest;
import com.tomasrojas.invernadero.api.dto.ZonaResponse;
import com.tomasrojas.invernadero.api.mapper.WebDtoMapper;
import com.tomasrojas.invernadero.service.ZonaApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST que expone los endpoints de gestión de zonas del invernadero.
 *
 * <p>Todas las rutas tienen el prefijo {@code /api/v1/zonas}.
 * La validación del cuerpo de la petición es manejada por {@code @Valid}
 * y los errores son capturados por {@code RestExceptionHandler}.</p>
 */
@RestController
@RequestMapping("/api/v1/zonas")
@Tag(name = "Zonas", description = "Gestión de zonas del invernadero")
public class ZonaController {

    private final ZonaApplicationService service;
    private final WebDtoMapper mapper;

    /**
     * @param service servicio de aplicación de zonas
     * @param mapper  conversor dominio ↔ DTO
     */
    public ZonaController(ZonaApplicationService service, WebDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Retorna todas las zonas registradas en el invernadero.
     *
     * @return lista de zonas con HTTP 200
     */
    @GetMapping
    @Operation(summary = "Listar todas las zonas")
    public List<ZonaResponse> listar() {
        return service.listarTodas().stream()
                .map(mapper::toZonaResponse)
                .toList();
    }

    /**
     * Crea una nueva zona en el invernadero.
     *
     * @param request datos de la zona a crear
     * @return zona creada con HTTP 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva zona")
    public ZonaResponse crear(@Valid @RequestBody CrearZonaRequest request) {
        return mapper.toZonaResponse(
                service.crear(request.nombre(), request.descripcion(),
                        request.ubicacion(), request.tipo(), request.areaM2())
        );
    }

    /**
     * Elimina una zona y todos sus datos asociados (lecturas, cultivos, umbrales).
     *
     * @param id identificador de la zona a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar una zona y todos sus datos")
    public void eliminar(@PathVariable UUID id) {
        service.eliminar(id);
    }
}

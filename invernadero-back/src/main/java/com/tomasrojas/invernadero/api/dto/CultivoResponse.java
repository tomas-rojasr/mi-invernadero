/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa un cultivo en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.EstadoCultivo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CultivoResponse(
        UUID id,
        UUID zonaId,
        String nombre,
        String variedad,
        String notas,
        Instant plantadoEn,
        Instant fechaCosechaEstimada,
        BigDecimal areaM2,
        Integer cantidadSembrada,
        BigDecimal rendimientoEsperadoKg,
        EstadoCultivo estado,
        Instant creadoEn
) {}

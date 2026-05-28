/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa una zona en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.TipoZona;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ZonaResponse(
        UUID id,
        String nombre,
        String descripcion,
        String ubicacion,
        TipoZona tipo,
        BigDecimal areaM2,
        Instant creadoEn
) {}

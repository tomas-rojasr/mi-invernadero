/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa una lectura ambiental en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.FuenteLectura;
import com.tomasrojas.invernadero.model.MetricaTipo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record LecturaResponse(
        UUID id,
        UUID zonaId,
        MetricaTipo tipo,
        BigDecimal valor,
        FuenteLectura fuente,
        String notas,
        Instant registradoEn
) {}

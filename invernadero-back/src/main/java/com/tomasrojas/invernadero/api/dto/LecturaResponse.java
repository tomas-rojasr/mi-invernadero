/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa una lectura ambiental en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.MetricaTipo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Representación de una lectura ambiental devuelta por la API REST.
 *
 * @param id           identificador único de la lectura
 * @param zonaId       identificador de la zona donde se tomó la medición
 * @param tipo         tipo de métrica ambiental medida
 * @param valor        valor de la medición
 * @param registradoEn instante de registro en UTC (formato ISO-8601)
 */
public record LecturaResponse(
        UUID id,
        UUID zonaId,
        MetricaTipo tipo,
        BigDecimal valor,
        Instant registradoEn
) {}

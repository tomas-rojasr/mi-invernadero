/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa un umbral ambiental en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.MetricaTipo;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Representación de un umbral ambiental devuelta por la API REST.
 *
 * @param id           identificador único del umbral
 * @param zonaId       identificador de la zona a la que aplica
 * @param tipo         tipo de métrica ambiental
 * @param valorMin     límite inferior aceptable (puede ser null)
 * @param valorMax     límite superior aceptable (puede ser null)
 * @param actualizadoEn fecha de última actualización en UTC
 */
public record UmbralResponse(
        UUID id,
        UUID zonaId,
        MetricaTipo tipo,
        BigDecimal valorMin,
        BigDecimal valorMax,
        Instant actualizadoEn
) {}

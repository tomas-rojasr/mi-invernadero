/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa una zona en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Representación de una zona devuelta por la API REST.
 *
 * @param id          identificador único de la zona
 * @param nombre      nombre de la zona
 * @param descripcion descripción de la zona
 * @param creadoEn    fecha y hora de creación en UTC (formato ISO-8601)
 */
public record ZonaResponse(
        UUID id,
        String nombre,
        String descripcion,
        Instant creadoEn
) {}

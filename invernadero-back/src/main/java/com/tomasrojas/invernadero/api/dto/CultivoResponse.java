/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida que representa un cultivo en las respuestas HTTP.
 */
package com.tomasrojas.invernadero.api.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Representación de un cultivo devuelta por la API REST.
 *
 * @param id         identificador único del cultivo
 * @param zonaId     identificador de la zona a la que pertenece
 * @param nombre     nombre del cultivo
 * @param variedad   variedad del cultivo (puede ser null)
 * @param notas      notas de seguimiento (puede ser null)
 * @param plantadoEn fecha de siembra en UTC
 * @param creadoEn   fecha de registro en el sistema en UTC
 */
public record CultivoResponse(
        UUID id,
        UUID zonaId,
        String nombre,
        String variedad,
        String notas,
        Instant plantadoEn,
        Instant creadoEn
) {}

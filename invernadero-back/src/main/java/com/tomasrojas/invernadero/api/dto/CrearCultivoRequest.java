/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CrearCultivoRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para registrar un cultivo en una zona.
 */
package com.tomasrojas.invernadero.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

/**
 * Datos requeridos en el cuerpo de la petición {@code POST /api/v1/zonas/{id}/cultivos}.
 *
 * @param nombre     nombre del cultivo (obligatorio)
 * @param variedad   variedad del cultivo (opcional)
 * @param notas      notas de seguimiento (opcional)
 * @param plantadoEn fecha de siembra en UTC; si es null se usa el momento actual
 */
public record CrearCultivoRequest(

        @NotBlank(message = "El nombre del cultivo es obligatorio")
        @Size(max = 200)
        String nombre,

        @Size(max = 120)
        String variedad,

        @Size(max = 2000)
        String notas,

        Instant plantadoEn
) {}

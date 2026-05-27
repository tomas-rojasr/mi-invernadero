/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ActualizarCultivoRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-27
 * Descripción: DTO de entrada para actualizar los datos de un cultivo existente.
 */
package com.tomasrojas.invernadero.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos editables en el cuerpo de la petición {@code PUT /api/v1/zonas/{id}/cultivos/{cultivoId}}.
 *
 * @param nombre   nuevo nombre del cultivo (obligatorio)
 * @param variedad nueva variedad (opcional)
 * @param notas    nuevas notas de seguimiento (opcional)
 */
public record ActualizarCultivoRequest(

        @NotBlank(message = "El nombre del cultivo es obligatorio")
        @Size(max = 200)
        String nombre,

        @Size(max = 120)
        String variedad,

        @Size(max = 2000)
        String notas
) {}

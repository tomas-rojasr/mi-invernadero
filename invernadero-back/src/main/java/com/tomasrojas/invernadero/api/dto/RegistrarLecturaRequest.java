/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  RegistrarLecturaRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para registrar una lectura ambiental.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.MetricaTipo;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Datos requeridos en el cuerpo de la petición {@code POST /api/v1/zonas/{id}/lecturas}.
 *
 * @param tipo  tipo de métrica ambiental (obligatorio)
 * @param valor valor de la medición (obligatorio)
 */
public record RegistrarLecturaRequest(

        @NotNull(message = "El tipo de métrica es obligatorio")
        MetricaTipo tipo,

        @NotNull(message = "El valor de la lectura es obligatorio")
        BigDecimal valor
) {}

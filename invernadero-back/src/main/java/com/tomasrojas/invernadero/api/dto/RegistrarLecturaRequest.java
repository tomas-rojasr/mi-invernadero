/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  RegistrarLecturaRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para registrar una lectura ambiental.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.FuenteLectura;
import com.tomasrojas.invernadero.model.MetricaTipo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record RegistrarLecturaRequest(
        @NotNull MetricaTipo tipo,
        @NotNull BigDecimal valor,
        FuenteLectura fuente,
        @Size(max = 500) String notas
) {}

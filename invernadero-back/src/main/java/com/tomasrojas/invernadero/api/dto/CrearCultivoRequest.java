/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CrearCultivoRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para registrar un cultivo en una zona.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.EstadoCultivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;

public record CrearCultivoRequest(
        @NotBlank @Size(max = 200) String nombre,
        @Size(max = 120) String variedad,
        @Size(max = 2000) String notas,
        Instant plantadoEn,
        Instant fechaCosechaEstimada,
        BigDecimal areaM2,
        Integer cantidadSembrada,
        BigDecimal rendimientoEsperadoKg,
        EstadoCultivo estado
) {}

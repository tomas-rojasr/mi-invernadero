/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CrearZonaRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para la creación de una zona.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.TipoZona;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CrearZonaRequest(
        @NotBlank @Size(max = 120) String nombre,
        @Size(max = 2000) String descripcion,
        @Size(max = 200) String ubicacion,
        TipoZona tipo,
        BigDecimal areaM2
) {}

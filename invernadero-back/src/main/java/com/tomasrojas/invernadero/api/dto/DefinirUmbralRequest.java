/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  DefinirUmbralRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para definir o actualizar un umbral ambiental.
 */
package com.tomasrojas.invernadero.api.dto;

import com.tomasrojas.invernadero.model.MetricaTipo;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Datos requeridos en el cuerpo de la petición {@code PUT /api/v1/zonas/{id}/umbrales}.
 *
 * <p>Al menos uno de {@code valorMin} o {@code valorMax} debería estar presente,
 * aunque la API los acepta ambos nulos (umbral sin restricción, útil para borrar límites).</p>
 *
 * @param tipo     tipo de métrica para la que se define el umbral (obligatorio)
 * @param valorMin límite inferior aceptable (opcional)
 * @param valorMax límite superior aceptable (opcional)
 */
public record DefinirUmbralRequest(

        @NotNull(message = "El tipo de métrica es obligatorio")
        MetricaTipo tipo,

        BigDecimal valorMin,
        BigDecimal valorMax
) {}

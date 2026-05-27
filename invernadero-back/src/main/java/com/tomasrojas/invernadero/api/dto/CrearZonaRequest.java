/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CrearZonaRequest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de entrada para la creación de una zona.
 */
package com.tomasrojas.invernadero.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Datos requeridos en el cuerpo de la petición {@code POST /api/v1/zonas}.
 *
 * <p>Las anotaciones de validación son procesadas por Spring antes de
 * llegar al controller; si fallan, {@code RestExceptionHandler} devuelve 400.</p>
 *
 * @param nombre      nombre de la zona (obligatorio, máximo 120 caracteres)
 * @param descripcion descripción de la zona (opcional, máximo 2000 caracteres)
 */
public record CrearZonaRequest(

        @NotBlank(message = "El nombre de la zona es obligatorio")
        @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
        String nombre,

        @Size(max = 2000, message = "La descripción no puede superar los 2000 caracteres")
        String descripcion
) {}

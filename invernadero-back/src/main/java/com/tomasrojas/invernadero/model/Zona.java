/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  Zona.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Clase de dominio que representa una zona física del invernadero.
 *              No contiene anotaciones de persistencia para mantener la capa
 *              de dominio independiente de la infraestructura (arquitectura hexagonal).
 */
package com.tomasrojas.invernadero.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Representa una zona física delimitada dentro del invernadero.
 *
 * <p>Una zona agrupa cultivos y lecturas ambientales bajo un mismo espacio
 * geográfico (ej. "Zona Norte", "Invernadero 2"). Es el agregado raíz
 * del modelo de dominio.</p>
 */
public class Zona {

    /** Identificador único de la zona. */
    private UUID id;

    /** Nombre descriptivo de la zona (máximo 120 caracteres). */
    private String nombre;

    /** Descripción detallada del propósito o características de la zona. */
    private String descripcion;

    /** Ubicación física dentro del invernadero. */
    private String ubicacion;

    /** Tipo de infraestructura de la zona. */
    private TipoZona tipo;

    /** Área en metros cuadrados. */
    private BigDecimal areaM2;

    /** Marca de tiempo de creación en UTC. */
    private Instant creadoEn;

    /** Constructor vacío requerido para mapeos internos. */
    public Zona() {}

    public Zona(UUID id, String nombre, String descripcion, String ubicacion,
                TipoZona tipo, BigDecimal areaM2, Instant creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
        this.areaM2 = areaM2;
        this.creadoEn = creadoEn;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único de la zona */
    public UUID getId() { return id; }

    /** @param id identificador único de la zona */
    public void setId(UUID id) { this.id = id; }

    /** @return el nombre de la zona */
    public String getNombre() { return nombre; }

    /** @param nombre nombre de la zona */
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public TipoZona getTipo() { return tipo; }
    public void setTipo(TipoZona tipo) { this.tipo = tipo; }

    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }

    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

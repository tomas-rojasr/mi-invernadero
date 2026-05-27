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

    /** Marca de tiempo de creación en UTC. */
    private Instant creadoEn;

    /** Constructor vacío requerido para mapeos internos. */
    public Zona() {}

    /**
     * Crea una zona con todos sus atributos definidos.
     *
     * @param id          identificador único
     * @param nombre      nombre de la zona
     * @param descripcion descripción de la zona
     * @param creadoEn    fecha y hora de creación en UTC
     */
    public Zona(UUID id, String nombre, String descripcion, Instant creadoEn) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    /** @return la descripción de la zona */
    public String getDescripcion() { return descripcion; }

    /** @param descripcion descripción de la zona */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /** @return la marca de tiempo de creación en UTC */
    public Instant getCreadoEn() { return creadoEn; }

    /** @param creadoEn marca de tiempo de creación en UTC */
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

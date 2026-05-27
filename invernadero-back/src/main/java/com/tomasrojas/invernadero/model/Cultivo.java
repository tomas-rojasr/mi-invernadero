/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  Cultivo.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Clase de dominio que representa un cultivo asociado a una zona.
 */
package com.tomasrojas.invernadero.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Representa un cultivo sembrado en una zona del invernadero.
 *
 * <p>Registra el nombre del cultivo, su variedad, notas de seguimiento
 * y la fecha de siembra. Pertenece a una {@link Zona} específica.</p>
 */
public class Cultivo {

    /** Identificador único del cultivo. */
    private UUID id;

    /** Zona a la que pertenece este cultivo. */
    private Zona zona;

    /** Nombre común del cultivo (ej. "Lechuga"). */
    private String nombre;

    /** Variedad específica del cultivo (ej. "Romana"). Puede ser nula. */
    private String variedad;

    /** Notas de seguimiento o cuidados especiales del cultivo. */
    private String notas;

    /** Fecha y hora de siembra en UTC. */
    private Instant plantadoEn;

    /** Marca de tiempo de registro en el sistema en UTC. */
    private Instant creadoEn;

    /** Constructor vacío requerido para mapeos internos. */
    public Cultivo() {}

    /**
     * Crea un cultivo con todos sus atributos.
     *
     * @param id         identificador único
     * @param zona       zona a la que pertenece
     * @param nombre     nombre del cultivo
     * @param variedad   variedad del cultivo (nullable)
     * @param notas      notas de seguimiento (nullable)
     * @param plantadoEn fecha de siembra en UTC
     * @param creadoEn   fecha de registro en UTC
     */
    public Cultivo(UUID id, Zona zona, String nombre, String variedad,
                   String notas, Instant plantadoEn, Instant creadoEn) {
        this.id = id;
        this.zona = zona;
        this.nombre = nombre;
        this.variedad = variedad;
        this.notas = notas;
        this.plantadoEn = plantadoEn;
        this.creadoEn = creadoEn;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único del cultivo */
    public UUID getId() { return id; }

    /** @param id identificador único del cultivo */
    public void setId(UUID id) { this.id = id; }

    /** @return la zona a la que pertenece el cultivo */
    public Zona getZona() { return zona; }

    /** @param zona zona a la que pertenece el cultivo */
    public void setZona(Zona zona) { this.zona = zona; }

    /** @return el nombre del cultivo */
    public String getNombre() { return nombre; }

    /** @param nombre nombre del cultivo */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return la variedad del cultivo, puede ser nula */
    public String getVariedad() { return variedad; }

    /** @param variedad variedad del cultivo */
    public void setVariedad(String variedad) { this.variedad = variedad; }

    /** @return las notas de seguimiento del cultivo */
    public String getNotas() { return notas; }

    /** @param notas notas de seguimiento del cultivo */
    public void setNotas(String notas) { this.notas = notas; }

    /** @return la fecha de siembra en UTC */
    public Instant getPlantadoEn() { return plantadoEn; }

    /** @param plantadoEn fecha de siembra en UTC */
    public void setPlantadoEn(Instant plantadoEn) { this.plantadoEn = plantadoEn; }

    /** @return la marca de tiempo de registro en UTC */
    public Instant getCreadoEn() { return creadoEn; }

    /** @param creadoEn marca de tiempo de registro en UTC */
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

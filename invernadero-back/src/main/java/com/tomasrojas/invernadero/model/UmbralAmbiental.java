/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralAmbiental.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Clase de dominio que define los límites aceptables
 *              para una métrica ambiental en una zona del invernadero.
 */
package com.tomasrojas.invernadero.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Define el rango aceptable (mínimo y máximo) para una métrica ambiental
 * en una zona específica del invernadero.
 *
 * <p>Los valores {@code valorMin} y {@code valorMax} son opcionales:
 * si {@code valorMin} es nulo, no hay límite inferior; si {@code valorMax}
 * es nulo, no hay límite superior. Esto permite configurar umbrales
 * unilaterales (ej. "temperatura no mayor a 30°C").</p>
 *
 * @see MetricaTipo
 */
public class UmbralAmbiental {

    /** Identificador único del umbral. */
    private UUID id;

    /** Zona a la que aplica este umbral. */
    private Zona zona;

    /** Tipo de métrica para la que se define el umbral. */
    private MetricaTipo tipo;

    /** Valor mínimo aceptable. Nulo si no hay límite inferior. */
    private BigDecimal valorMin;

    /** Valor máximo aceptable. Nulo si no hay límite superior. */
    private BigDecimal valorMax;

    /** Última vez que se actualizó este umbral (UTC). */
    private Instant actualizadoEn;

    /** Constructor vacío requerido para mapeos internos. */
    public UmbralAmbiental() {}

    /**
     * Crea un umbral ambiental con todos sus atributos.
     *
     * @param id           identificador único
     * @param zona         zona a la que aplica el umbral
     * @param tipo         tipo de métrica ambiental
     * @param valorMin     límite inferior aceptable (nullable)
     * @param valorMax     límite superior aceptable (nullable)
     * @param actualizadoEn fecha de última actualización en UTC
     */
    public UmbralAmbiental(UUID id, Zona zona, MetricaTipo tipo,
                           BigDecimal valorMin, BigDecimal valorMax,
                           Instant actualizadoEn) {
        this.id = id;
        this.zona = zona;
        this.tipo = tipo;
        this.valorMin = valorMin;
        this.valorMax = valorMax;
        this.actualizadoEn = actualizadoEn;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único del umbral */
    public UUID getId() { return id; }

    /** @param id identificador único del umbral */
    public void setId(UUID id) { this.id = id; }

    /** @return la zona a la que aplica el umbral */
    public Zona getZona() { return zona; }

    /** @param zona zona a la que aplica el umbral */
    public void setZona(Zona zona) { this.zona = zona; }

    /** @return el tipo de métrica para la que aplica el umbral */
    public MetricaTipo getTipo() { return tipo; }

    /** @param tipo tipo de métrica ambiental */
    public void setTipo(MetricaTipo tipo) { this.tipo = tipo; }

    /** @return el valor mínimo aceptable, puede ser nulo */
    public BigDecimal getValorMin() { return valorMin; }

    /** @param valorMin límite inferior aceptable */
    public void setValorMin(BigDecimal valorMin) { this.valorMin = valorMin; }

    /** @return el valor máximo aceptable, puede ser nulo */
    public BigDecimal getValorMax() { return valorMax; }

    /** @param valorMax límite superior aceptable */
    public void setValorMax(BigDecimal valorMax) { this.valorMax = valorMax; }

    /** @return la fecha de última actualización en UTC */
    public Instant getActualizadoEn() { return actualizadoEn; }

    /** @param actualizadoEn fecha de última actualización en UTC */
    public void setActualizadoEn(Instant actualizadoEn) { this.actualizadoEn = actualizadoEn; }
}

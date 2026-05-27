/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaAmbiental.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Clase de dominio que representa una medición ambiental
 *              registrada en una zona del invernadero.
 */
package com.tomasrojas.invernadero.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Representa una medición puntual de una variable ambiental en una zona.
 *
 * <p>Cada lectura registra un único valor para un tipo de métrica
 * (temperatura, humedad, luz o humedad del suelo) en un instante de tiempo.
 * Es inmutable conceptualmente: una vez registrada no se modifica.</p>
 *
 * @see MetricaTipo
 */
public class LecturaAmbiental {

    /** Identificador único de la lectura. */
    private UUID id;

    /** Zona en la que se tomó la medición. */
    private Zona zona;

    /** Tipo de variable ambiental medida. */
    private MetricaTipo tipo;

    /**
     * Valor de la medición.
     * Se usa BigDecimal para evitar errores de precisión flotante
     * al almacenar y comparar valores decimales críticos.
     */
    private BigDecimal valor;

    /** Momento exacto en que se registró la medición (UTC). */
    private Instant registradoEn;

    /** Constructor vacío requerido para mapeos internos. */
    public LecturaAmbiental() {}

    /**
     * Crea una lectura ambiental completa.
     *
     * @param id           identificador único
     * @param zona         zona donde se realizó la medición
     * @param tipo         tipo de métrica ambiental
     * @param valor        valor medido
     * @param registradoEn instante de registro en UTC
     */
    public LecturaAmbiental(UUID id, Zona zona, MetricaTipo tipo,
                            BigDecimal valor, Instant registradoEn) {
        this.id = id;
        this.zona = zona;
        this.tipo = tipo;
        this.valor = valor;
        this.registradoEn = registradoEn;
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único de la lectura */
    public UUID getId() { return id; }

    /** @param id identificador único de la lectura */
    public void setId(UUID id) { this.id = id; }

    /** @return la zona donde se realizó la medición */
    public Zona getZona() { return zona; }

    /** @param zona zona donde se realizó la medición */
    public void setZona(Zona zona) { this.zona = zona; }

    /** @return el tipo de métrica ambiental medida */
    public MetricaTipo getTipo() { return tipo; }

    /** @param tipo tipo de métrica ambiental */
    public void setTipo(MetricaTipo tipo) { this.tipo = tipo; }

    /** @return el valor de la medición */
    public BigDecimal getValor() { return valor; }

    /** @param valor valor de la medición */
    public void setValor(BigDecimal valor) { this.valor = valor; }

    /** @return el instante de registro en UTC */
    public Instant getRegistradoEn() { return registradoEn; }

    /** @param registradoEn instante de registro en UTC */
    public void setRegistradoEn(Instant registradoEn) { this.registradoEn = registradoEn; }
}

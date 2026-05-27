/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralEntity.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Entidad JPA que mapea la tabla "umbrales_ambientales".
 */
package com.tomasrojas.invernadero.model.entity;

import com.tomasrojas.invernadero.model.MetricaTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad de persistencia que define los límites aceptables para
 * una métrica ambiental en una zona del invernadero.
 *
 * <p>Por zona solo existe un umbral por tipo de métrica. La restricción
 * se aplica a nivel de servicio: si ya existe un umbral para ese tipo,
 * se actualiza en lugar de crear uno nuevo.</p>
 */
@Entity
@Table(name = "umbrales_ambientales")
public class UmbralEntity {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /** Zona a la que aplica este umbral. Carga diferida para evitar N+1. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private ZonaEntity zona;

    /**
     * Tipo de métrica para la que se define el umbral.
     * Se persiste como String para resistir reordenamientos del enum.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MetricaTipo tipo;

    /** Límite inferior aceptable. Nulo si no hay restricción mínima. */
    @Column(precision = 10, scale = 2)
    private BigDecimal valorMin;

    /** Límite superior aceptable. Nulo si no hay restricción máxima. */
    @Column(precision = 10, scale = 2)
    private BigDecimal valorMax;

    /** Última actualización del umbral en UTC. */
    @Column(nullable = false)
    private Instant actualizadoEn;

    /** Establece la fecha de actualización antes de cada inserción o modificación. */
    @PrePersist
    @PreUpdate
    void antesDeGuardar() {
        this.actualizadoEn = Instant.now();
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único del umbral */
    public UUID getId() { return id; }

    /** @param id identificador único del umbral */
    public void setId(UUID id) { this.id = id; }

    /** @return la entidad zona a la que aplica el umbral */
    public ZonaEntity getZona() { return zona; }

    /** @param zona entidad zona a la que aplica el umbral */
    public void setZona(ZonaEntity zona) { this.zona = zona; }

    /** @return el tipo de métrica para el que aplica el umbral */
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

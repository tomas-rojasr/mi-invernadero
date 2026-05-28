/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaEntity.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Entidad JPA que mapea la tabla "lecturas_ambientales".
 */
package com.tomasrojas.invernadero.model.entity;

import com.tomasrojas.invernadero.model.FuenteLectura;
import com.tomasrojas.invernadero.model.MetricaTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad de persistencia de una medición ambiental puntual en una zona.
 *
 * <p>El campo {@code tipo} se almacena como cadena de texto en la BD
 * ({@code EnumType.STRING}) para que los registros históricos no se
 * corrompan si en el futuro se reordena el enum.</p>
 */
@Entity
@Table(name = "lecturas_ambientales")
public class LecturaEntity {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /** Zona donde se tomó la medición. Carga diferida para evitar N+1. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private ZonaEntity zona;

    /**
     * Tipo de métrica ambiental medida.
     * Se persiste como String para resistir reordenamientos del enum.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MetricaTipo tipo;

    /** Valor de la medición con precisión decimal exacta. */
    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    /** Origen de la lectura (manual o sensor automático). */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private FuenteLectura fuente = FuenteLectura.MANUAL;

    /** Notas adicionales sobre la lectura. */
    @Size(max = 500)
    @Column(length = 500)
    private String notas;

    /** Instante exacto del registro en UTC. */
    @Column(nullable = false, updatable = false)
    private Instant registradoEn;

    /** Establece el instante de registro automáticamente antes de insertar. */
    @PrePersist
    void antesDeInsertar() {
        this.registradoEn = Instant.now();
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único de la lectura */
    public UUID getId() { return id; }

    /** @param id identificador único de la lectura */
    public void setId(UUID id) { this.id = id; }

    /** @return la entidad zona donde se realizó la medición */
    public ZonaEntity getZona() { return zona; }

    /** @param zona entidad zona donde se realizó la medición */
    public void setZona(ZonaEntity zona) { this.zona = zona; }

    /** @return el tipo de métrica ambiental */
    public MetricaTipo getTipo() { return tipo; }

    /** @param tipo tipo de métrica ambiental */
    public void setTipo(MetricaTipo tipo) { this.tipo = tipo; }

    /** @return el valor de la medición */
    public BigDecimal getValor() { return valor; }

    /** @param valor valor de la medición */
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public FuenteLectura getFuente() { return fuente; }
    public void setFuente(FuenteLectura fuente) { this.fuente = fuente; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public Instant getRegistradoEn() { return registradoEn; }
    public void setRegistradoEn(Instant registradoEn) { this.registradoEn = registradoEn; }
}

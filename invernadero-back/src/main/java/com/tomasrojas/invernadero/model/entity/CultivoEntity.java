/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoEntity.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Entidad JPA que mapea la tabla "cultivos" en la base de datos.
 */
package com.tomasrojas.invernadero.model.entity;

import com.tomasrojas.invernadero.model.EstadoCultivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad de persistencia que representa un cultivo asociado a una zona.
 *
 * <p>Mantiene una relación {@code ManyToOne} con {@link ZonaEntity},
 * ya que una zona puede tener múltiples cultivos activos.</p>
 */
@Entity
@Table(name = "cultivos")
public class CultivoEntity {

    /** Identificador único generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /** Zona a la que pertenece este cultivo. Carga diferida para evitar N+1. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private ZonaEntity zona;

    /** Nombre del cultivo. Obligatorio, máximo 200 caracteres. */
    @NotBlank(message = "El nombre del cultivo es obligatorio")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String nombre;

    /** Variedad específica del cultivo. Opcional, máximo 120 caracteres. */
    @Size(max = 120)
    @Column(length = 120)
    private String variedad;

    /** Notas de seguimiento o cuidados. Opcional, máximo 2000 caracteres. */
    @Size(max = 2000)
    @Column(length = 2000)
    private String notas;

    /** Fecha de siembra del cultivo en UTC. */
    @Column(name = "plantado_en")
    private Instant plantadoEn;

    /** Fecha estimada de cosecha en UTC. */
    @Column(name = "fecha_cosecha_estimada")
    private Instant fechaCosechaEstimada;

    /** Área sembrada en metros cuadrados. */
    @Column(name = "area_m2", precision = 8, scale = 2)
    private BigDecimal areaM2;

    /** Cantidad de unidades sembradas. */
    @Column(name = "cantidad_sembrada")
    private Integer cantidadSembrada;

    /** Rendimiento esperado en kilogramos. */
    @Column(name = "rendimiento_esperado_kg", precision = 8, scale = 2)
    private BigDecimal rendimientoEsperadoKg;

    /** Estado del ciclo de vida del cultivo. */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EstadoCultivo estado;

    /** Marca de tiempo de registro en el sistema en UTC. */
    @Column(nullable = false, updatable = false)
    private Instant creadoEn;

    /** Establece la fecha de creación automáticamente antes de insertar. */
    @PrePersist
    void antesDeInsertar() {
        this.creadoEn = Instant.now();
    }

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el identificador único del cultivo */
    public UUID getId() { return id; }

    /** @param id identificador único del cultivo */
    public void setId(UUID id) { this.id = id; }

    /** @return la entidad zona a la que pertenece el cultivo */
    public ZonaEntity getZona() { return zona; }

    /** @param zona entidad zona a la que pertenece el cultivo */
    public void setZona(ZonaEntity zona) { this.zona = zona; }

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

    public Instant getFechaCosechaEstimada() { return fechaCosechaEstimada; }
    public void setFechaCosechaEstimada(Instant fechaCosechaEstimada) { this.fechaCosechaEstimada = fechaCosechaEstimada; }

    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }

    public Integer getCantidadSembrada() { return cantidadSembrada; }
    public void setCantidadSembrada(Integer cantidadSembrada) { this.cantidadSembrada = cantidadSembrada; }

    public BigDecimal getRendimientoEsperadoKg() { return rendimientoEsperadoKg; }
    public void setRendimientoEsperadoKg(BigDecimal rendimientoEsperadoKg) { this.rendimientoEsperadoKg = rendimientoEsperadoKg; }

    public EstadoCultivo getEstado() { return estado; }
    public void setEstado(EstadoCultivo estado) { this.estado = estado; }

    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

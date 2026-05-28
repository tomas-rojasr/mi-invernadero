/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaEntity.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-14
 * Descripción: Entidad JPA que mapea la tabla "zonas" en la base de datos.
 *              Corresponde a la clase de dominio Zona.
 */
package com.tomasrojas.invernadero.model.entity;

import com.tomasrojas.invernadero.model.TipoZona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad de persistencia que representa una zona del invernadero
 * en la base de datos relacional.
 *
 * <p>Esta clase es la contraparte de infraestructura de {@code Zona}.
 * La separación existe para que la lógica de negocio no dependa
 * de JPA ni de la estructura de la base de datos.</p>
 */
@Entity
@Table(name = "zonas")
public class ZonaEntity {

    /** Identificador único generado automáticamente por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    /** Nombre de la zona. Obligatorio, máximo 120 caracteres. */
    @NotBlank(message = "El nombre de la zona es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    @Column(nullable = false, length = 120)
    private String nombre;

    /** Descripción opcional de la zona, máximo 2000 caracteres. */
    @Size(max = 2000)
    @Column(length = 2000)
    private String descripcion;

    /** Ubicación física de la zona dentro del invernadero. */
    @Size(max = 200)
    @Column(length = 200)
    private String ubicacion;

    /** Tipo de infraestructura de la zona. */
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoZona tipo;

    /** Área de la zona en metros cuadrados. */
    @Column(precision = 8, scale = 2)
    private BigDecimal areaM2;

    /** Marca de tiempo de creación. Se asigna antes de persistir por primera vez. */
    @Column(nullable = false, updatable = false)
    private Instant creadoEn;

    /** Establece la fecha de creación automáticamente antes de insertar. */
    @PrePersist
    void antesDeInsertar() {
        this.creadoEn = Instant.now();
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

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public TipoZona getTipo() { return tipo; }
    public void setTipo(TipoZona tipo) { this.tipo = tipo; }

    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }

    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

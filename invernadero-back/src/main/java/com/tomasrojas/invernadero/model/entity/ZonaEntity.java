/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaEntity.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Entidad JPA que mapea la tabla "zonas" en la base de datos.
 *              Corresponde a la clase de dominio Zona.
 */
package com.tomasrojas.invernadero.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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

    /** @return la marca de tiempo de creación en UTC */
    public Instant getCreadoEn() { return creadoEn; }

    /** @param creadoEn marca de tiempo de creación en UTC */
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

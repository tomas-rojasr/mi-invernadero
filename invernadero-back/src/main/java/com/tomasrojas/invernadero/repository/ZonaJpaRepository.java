/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaJpaRepository.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Repositorio Spring Data JPA para ZonaEntity.
 *              Spring genera la implementación en tiempo de ejecución.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad {@link ZonaEntity}.
 *
 * <p>Spring genera automáticamente la implementación de todos los métodos
 * heredados de {@link JpaRepository}: {@code findAll}, {@code findById},
 * {@code save}, {@code deleteById}, {@code existsById}, entre otros.</p>
 */
public interface ZonaJpaRepository extends JpaRepository<ZonaEntity, UUID> {
    // Spring Data provee todas las operaciones CRUD estándar.
    // Aquí se agregarían queries personalizados si fueran necesarios.
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoJpaRepository.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Repositorio Spring Data JPA para CultivoEntity.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.entity.CultivoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad {@link CultivoEntity}.
 */
public interface CultivoJpaRepository extends JpaRepository<CultivoEntity, UUID> {

    /**
     * Retorna todos los cultivos de una zona, ordenados por fecha de creación descendente.
     *
     * @param zonaId identificador de la zona
     * @return lista de cultivos de la zona
     */
    List<CultivoEntity> findByZonaIdOrderByCreadoEnDesc(UUID zonaId);

    /**
     * Elimina todos los cultivos de una zona.
     * Usado en cascada al borrar una zona completa.
     *
     * @param zonaId identificador de la zona
     */
    void deleteByZonaId(UUID zonaId);
}

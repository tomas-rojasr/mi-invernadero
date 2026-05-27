/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralJpaRepository.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Repositorio Spring Data JPA para UmbralEntity.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.model.entity.UmbralEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad {@link UmbralEntity}.
 */
public interface UmbralJpaRepository extends JpaRepository<UmbralEntity, UUID> {

    /**
     * Retorna todos los umbrales configurados para una zona.
     *
     * @param zonaId identificador de la zona
     * @return lista de umbrales de la zona
     */
    List<UmbralEntity> findByZonaId(UUID zonaId);

    /**
     * Busca el umbral de una zona para un tipo de métrica específico.
     * Usa {@code findFirst} para tolerar filas duplicadas heredadas de migraciones.
     *
     * @param zonaId identificador de la zona
     * @param tipo   tipo de métrica ambiental
     * @return el umbral existente, o vacío si no está configurado
     */
    Optional<UmbralEntity> findFirstByZonaIdAndTipo(UUID zonaId, MetricaTipo tipo);

    /**
     * Elimina todos los umbrales de una zona.
     * Usado en cascada al borrar una zona completa.
     *
     * @param zonaId identificador de la zona
     */
    void deleteByZonaId(UUID zonaId);
}

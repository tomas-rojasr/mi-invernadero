/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaJpaRepository.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Repositorio Spring Data JPA para LecturaEntity.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.entity.LecturaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repositorio Spring Data JPA para la entidad {@link LecturaEntity}.
 *
 * <p>Los métodos con nombre derivado (ej. {@code findByZonaId}) son
 * interpretados y generados por Spring Data en tiempo de ejecución
 * a partir del nombre del método.</p>
 */
public interface LecturaJpaRepository extends JpaRepository<LecturaEntity, UUID> {

    /**
     * Retorna lecturas de una zona ordenadas de más reciente a más antigua,
     * limitadas por el {@link Pageable} recibido.
     *
     * @param zonaId   identificador de la zona
     * @param pageable configuración de límite y orden
     * @return lista de lecturas paginada
     */
    List<LecturaEntity> findByZonaIdOrderByRegistradoEnDesc(UUID zonaId, Pageable pageable);

    /**
     * Elimina todas las lecturas de una zona.
     * Usado en cascada al borrar una zona completa.
     *
     * @param zonaId identificador de la zona
     */
    void deleteByZonaId(UUID zonaId);
}

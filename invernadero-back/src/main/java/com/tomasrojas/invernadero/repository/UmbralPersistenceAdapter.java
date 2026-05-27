/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralPersistenceAdapter.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Implementación de UmbralPersistencePort usando Spring Data JPA.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.model.UmbralAmbiental;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import com.tomasrojas.invernadero.repository.mapper.UmbralMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de persistencia para {@link UmbralAmbiental}.
 */
@Repository
public class UmbralPersistenceAdapter implements UmbralPersistencePort {

    private final UmbralJpaRepository jpaRepository;
    private final ZonaJpaRepository zonaJpaRepository;
    private final UmbralMapper mapper;

    /**
     * @param jpaRepository     repositorio JPA de umbrales
     * @param zonaJpaRepository repositorio JPA de zonas
     * @param mapper            conversor entre dominio y entidad JPA
     */
    public UmbralPersistenceAdapter(UmbralJpaRepository jpaRepository,
                                    ZonaJpaRepository zonaJpaRepository,
                                    UmbralMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.zonaJpaRepository = zonaJpaRepository;
        this.mapper = mapper;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public UmbralAmbiental guardar(UmbralAmbiental umbral) {
        ZonaEntity zonaRef = zonaJpaRepository.getReferenceById(umbral.getZona().getId());
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(umbral, zonaRef)));
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<UmbralAmbiental> listarPorZona(UUID zonaId) {
        return jpaRepository.findByZonaId(zonaId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<UmbralAmbiental> buscarPorZonaYTipo(UUID zonaId, MetricaTipo tipo) {
        return jpaRepository.findByZonaIdAndTipo(zonaId, tipo).map(mapper::toDomain);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void eliminarPorZona(UUID zonaId) {
        jpaRepository.deleteByZonaId(zonaId);
    }
}

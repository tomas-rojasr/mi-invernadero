/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoPersistenceAdapter.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Implementación de CultivoPersistencePort usando Spring Data JPA.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.Cultivo;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import com.tomasrojas.invernadero.repository.mapper.CultivoMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de persistencia para {@link Cultivo}.
 */
@Repository
public class CultivoPersistenceAdapter implements CultivoPersistencePort {

    private final CultivoJpaRepository jpaRepository;
    private final ZonaJpaRepository zonaJpaRepository;
    private final CultivoMapper mapper;

    /**
     * @param jpaRepository     repositorio JPA de cultivos
     * @param zonaJpaRepository repositorio JPA de zonas
     * @param mapper            conversor entre dominio y entidad JPA
     */
    public CultivoPersistenceAdapter(CultivoJpaRepository jpaRepository,
                                     ZonaJpaRepository zonaJpaRepository,
                                     CultivoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.zonaJpaRepository = zonaJpaRepository;
        this.mapper = mapper;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Cultivo guardar(Cultivo cultivo) {
        ZonaEntity zonaRef = zonaJpaRepository.getReferenceById(cultivo.getZona().getId());
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(cultivo, zonaRef)));
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public List<Cultivo> listarPorZona(UUID zonaId) {
        return jpaRepository.findByZonaIdOrderByCreadoEnDesc(zonaId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cultivo> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void eliminarPorId(UUID id) {
        jpaRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void eliminarPorZona(UUID zonaId) {
        jpaRepository.deleteByZonaId(zonaId);
    }
}

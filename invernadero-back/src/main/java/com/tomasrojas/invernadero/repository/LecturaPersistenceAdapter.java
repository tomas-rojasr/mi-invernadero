/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaPersistenceAdapter.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Implementación de LecturaPersistencePort usando Spring Data JPA.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.LecturaAmbiental;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import com.tomasrojas.invernadero.repository.mapper.LecturaMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Adaptador de persistencia para {@link LecturaAmbiental}.
 */
@Repository
public class LecturaPersistenceAdapter implements LecturaPersistencePort {

    private final LecturaJpaRepository jpaRepository;
    private final ZonaJpaRepository zonaJpaRepository;
    private final LecturaMapper mapper;

    /**
     * @param jpaRepository     repositorio JPA de lecturas
     * @param zonaJpaRepository repositorio JPA de zonas, para obtener la referencia al guardar
     * @param mapper            conversor entre dominio y entidad JPA
     */
    public LecturaPersistenceAdapter(LecturaJpaRepository jpaRepository,
                                     ZonaJpaRepository zonaJpaRepository,
                                     LecturaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.zonaJpaRepository = zonaJpaRepository;
        this.mapper = mapper;
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LecturaAmbiental guardar(LecturaAmbiental lectura) {
        // Se obtiene la referencia JPA de la zona sin cargar todos sus campos (getReferenceById)
        ZonaEntity zonaRef = zonaJpaRepository.getReferenceById(lectura.getZona().getId());
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(lectura, zonaRef)));
    }

    /** {@inheritDoc} */
    @Override
    public List<LecturaAmbiental> listarPorZona(UUID zonaId, int limite) {
        return jpaRepository
                .findByZonaIdOrderByRegistradoEnDesc(zonaId, PageRequest.of(0, limite))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void eliminarPorZona(UUID zonaId) {
        jpaRepository.deleteByZonaId(zonaId);
    }
}

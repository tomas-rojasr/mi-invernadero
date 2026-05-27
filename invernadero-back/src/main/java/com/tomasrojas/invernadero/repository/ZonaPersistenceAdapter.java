/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaPersistenceAdapter.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Implementación de ZonaPersistencePort usando Spring Data JPA.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.repository.mapper.ZonaMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de persistencia para {@link Zona} que delega en
 * {@link ZonaJpaRepository} y usa {@link ZonaMapper} para la conversión.
 *
 * <p>Es el único punto del sistema donde el dominio toca JPA.
 * Los servicios solo ven {@link ZonaPersistencePort}.</p>
 */
@Repository
public class ZonaPersistenceAdapter implements ZonaPersistencePort {

    private final ZonaJpaRepository jpaRepository;
    private final ZonaMapper mapper;

    /**
     * @param jpaRepository repositorio Spring Data JPA
     * @param mapper        conversor entre dominio y entidad JPA
     */
    public ZonaPersistenceAdapter(ZonaJpaRepository jpaRepository, ZonaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    /** {@inheritDoc} */
    @Override
    public Zona guardar(Zona zona) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(zona)));
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Zona> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    /** {@inheritDoc} */
    @Override
    public List<Zona> listarTodas() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public void eliminar(UUID id) {
        jpaRepository.deleteById(id);
    }

    /** {@inheritDoc} */
    @Override
    public boolean existePorId(UUID id) {
        return jpaRepository.existsById(id);
    }
}

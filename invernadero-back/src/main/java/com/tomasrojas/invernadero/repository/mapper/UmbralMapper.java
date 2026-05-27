/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralMapper.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Conversor entre UmbralEntity (JPA) y UmbralAmbiental (dominio).
 */
package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.UmbralAmbiental;
import com.tomasrojas.invernadero.model.entity.UmbralEntity;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre la entidad JPA {@link UmbralEntity} y
 * la clase de dominio {@link UmbralAmbiental}.
 */
@Component
public class UmbralMapper {

    private final ZonaMapper zonaMapper;

    /**
     * @param zonaMapper conversor de zona, inyectado por Spring
     */
    public UmbralMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    /**
     * Convierte una entidad JPA en un objeto de dominio.
     *
     * @param entity entidad proveniente de la base de datos
     * @return objeto de dominio correspondiente
     */
    public UmbralAmbiental toDomain(UmbralEntity entity) {
        return new UmbralAmbiental(
                entity.getId(),
                zonaMapper.toDomain(entity.getZona()),
                entity.getTipo(),
                entity.getValorMin(),
                entity.getValorMax(),
                entity.getActualizadoEn()
        );
    }

    /**
     * Convierte un objeto de dominio en una entidad JPA lista para persistir.
     *
     * @param umbral     objeto de dominio
     * @param zonaEntity entidad JPA de la zona a la que pertenece el umbral
     * @return entidad JPA correspondiente
     */
    public UmbralEntity toEntity(UmbralAmbiental umbral, ZonaEntity zonaEntity) {
        UmbralEntity entity = new UmbralEntity();
        entity.setId(umbral.getId());   // preservar id para que JPA haga UPDATE en lugar de INSERT
        entity.setZona(zonaEntity);
        entity.setTipo(umbral.getTipo());
        entity.setValorMin(umbral.getValorMin());
        entity.setValorMax(umbral.getValorMax());
        return entity;
    }
}

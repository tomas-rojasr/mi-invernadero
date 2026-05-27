/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaMapper.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Conversor entre LecturaEntity (JPA) y LecturaAmbiental (dominio).
 */
package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.LecturaAmbiental;
import com.tomasrojas.invernadero.model.entity.LecturaEntity;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre la entidad JPA {@link LecturaEntity} y
 * la clase de dominio {@link LecturaAmbiental}.
 */
@Component
public class LecturaMapper {

    private final ZonaMapper zonaMapper;

    /**
     * @param zonaMapper conversor de zona, inyectado por Spring
     */
    public LecturaMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    /**
     * Convierte una entidad JPA en un objeto de dominio.
     *
     * @param entity entidad proveniente de la base de datos
     * @return objeto de dominio correspondiente
     */
    public LecturaAmbiental toDomain(LecturaEntity entity) {
        return new LecturaAmbiental(
                entity.getId(),
                zonaMapper.toDomain(entity.getZona()),
                entity.getTipo(),
                entity.getValor(),
                entity.getRegistradoEn()
        );
    }

    /**
     * Convierte un objeto de dominio en una entidad JPA lista para persistir.
     *
     * @param lectura    objeto de dominio
     * @param zonaEntity entidad JPA de la zona a la que pertenece la lectura
     * @return entidad JPA correspondiente
     */
    public LecturaEntity toEntity(LecturaAmbiental lectura, ZonaEntity zonaEntity) {
        LecturaEntity entity = new LecturaEntity();
        entity.setZona(zonaEntity);
        entity.setTipo(lectura.getTipo());
        entity.setValor(lectura.getValor());
        return entity;
    }
}

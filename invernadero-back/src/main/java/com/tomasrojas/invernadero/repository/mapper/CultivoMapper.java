/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoMapper.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Conversor entre CultivoEntity (JPA) y Cultivo (dominio).
 */
package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.Cultivo;
import com.tomasrojas.invernadero.model.entity.CultivoEntity;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre la entidad JPA {@link CultivoEntity} y
 * la clase de dominio {@link Cultivo}.
 */
@Component
public class CultivoMapper {

    private final ZonaMapper zonaMapper;

    /**
     * @param zonaMapper conversor de zona, inyectado por Spring
     */
    public CultivoMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    /**
     * Convierte una entidad JPA en un objeto de dominio.
     *
     * @param entity entidad proveniente de la base de datos
     * @return objeto de dominio correspondiente
     */
    public Cultivo toDomain(CultivoEntity entity) {
        return new Cultivo(
                entity.getId(),
                zonaMapper.toDomain(entity.getZona()),
                entity.getNombre(),
                entity.getVariedad(),
                entity.getNotas(),
                entity.getPlantadoEn(),
                entity.getCreadoEn()
        );
    }

    /**
     * Convierte un objeto de dominio en una entidad JPA lista para persistir.
     *
     * @param cultivo    objeto de dominio
     * @param zonaEntity entidad JPA de la zona a la que pertenece el cultivo
     * @return entidad JPA correspondiente
     */
    public CultivoEntity toEntity(Cultivo cultivo, ZonaEntity zonaEntity) {
        CultivoEntity entity = new CultivoEntity();
        entity.setZona(zonaEntity);
        entity.setNombre(cultivo.getNombre());
        entity.setVariedad(cultivo.getVariedad());
        entity.setNotas(cultivo.getNotas());
        entity.setPlantadoEn(cultivo.getPlantadoEn());
        return entity;
    }
}

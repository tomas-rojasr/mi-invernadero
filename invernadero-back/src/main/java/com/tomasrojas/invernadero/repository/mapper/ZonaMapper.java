/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaMapper.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Conversor entre ZonaEntity (JPA) y Zona (dominio).
 */
package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

/**
 * Conversor entre la entidad JPA {@link ZonaEntity} y la clase de dominio {@link Zona}.
 *
 * <p>Mantiene el desacoplamiento entre la capa de dominio y la de infraestructura:
 * el servicio trabaja con {@code Zona} y nunca ve {@code ZonaEntity}.</p>
 */
@Component
public class ZonaMapper {

    /**
     * Convierte una entidad JPA en un objeto de dominio.
     *
     * @param entity entidad proveniente de la base de datos
     * @return objeto de dominio correspondiente
     */
    public Zona toDomain(ZonaEntity entity) {
        return new Zona(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCreadoEn()
        );
    }

    /**
     * Convierte un objeto de dominio en una entidad JPA lista para persistir.
     *
     * @param zona objeto de dominio
     * @return entidad JPA correspondiente
     */
    public ZonaEntity toEntity(Zona zona) {
        ZonaEntity entity = new ZonaEntity();
        entity.setId(zona.getId());
        entity.setNombre(zona.getNombre());
        entity.setDescripcion(zona.getDescripcion());
        entity.setCreadoEn(zona.getCreadoEn());
        return entity;
    }
}

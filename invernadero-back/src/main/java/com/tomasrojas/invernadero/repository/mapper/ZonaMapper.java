package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

@Component
public class ZonaMapper {

    public Zona toDomain(ZonaEntity entity) {
        Zona zona = new Zona();
        zona.setId(entity.getId());
        zona.setNombre(entity.getNombre());
        zona.setDescripcion(entity.getDescripcion());
        zona.setUbicacion(entity.getUbicacion());
        zona.setTipo(entity.getTipo());
        zona.setAreaM2(entity.getAreaM2());
        zona.setCreadoEn(entity.getCreadoEn());
        return zona;
    }

    public ZonaEntity toEntity(Zona zona) {
        ZonaEntity entity = new ZonaEntity();
        entity.setId(zona.getId());
        entity.setNombre(zona.getNombre());
        entity.setDescripcion(zona.getDescripcion());
        entity.setUbicacion(zona.getUbicacion());
        entity.setTipo(zona.getTipo());
        entity.setAreaM2(zona.getAreaM2());
        entity.setCreadoEn(zona.getCreadoEn());
        return entity;
    }
}

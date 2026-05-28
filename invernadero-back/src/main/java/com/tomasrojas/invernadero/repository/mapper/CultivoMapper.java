package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.Cultivo;
import com.tomasrojas.invernadero.model.entity.CultivoEntity;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

@Component
public class CultivoMapper {

    private final ZonaMapper zonaMapper;

    public CultivoMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    public Cultivo toDomain(CultivoEntity entity) {
        Cultivo c = new Cultivo();
        c.setId(entity.getId());
        c.setZona(zonaMapper.toDomain(entity.getZona()));
        c.setNombre(entity.getNombre());
        c.setVariedad(entity.getVariedad());
        c.setNotas(entity.getNotas());
        c.setPlantadoEn(entity.getPlantadoEn());
        c.setFechaCosechaEstimada(entity.getFechaCosechaEstimada());
        c.setAreaM2(entity.getAreaM2());
        c.setCantidadSembrada(entity.getCantidadSembrada());
        c.setRendimientoEsperadoKg(entity.getRendimientoEsperadoKg());
        c.setEstado(entity.getEstado());
        c.setCreadoEn(entity.getCreadoEn());
        return c;
    }

    public CultivoEntity toEntity(Cultivo cultivo, ZonaEntity zonaEntity) {
        CultivoEntity entity = new CultivoEntity();
        entity.setId(cultivo.getId());
        entity.setZona(zonaEntity);
        entity.setNombre(cultivo.getNombre());
        entity.setVariedad(cultivo.getVariedad());
        entity.setNotas(cultivo.getNotas());
        entity.setPlantadoEn(cultivo.getPlantadoEn());
        entity.setFechaCosechaEstimada(cultivo.getFechaCosechaEstimada());
        entity.setAreaM2(cultivo.getAreaM2());
        entity.setCantidadSembrada(cultivo.getCantidadSembrada());
        entity.setRendimientoEsperadoKg(cultivo.getRendimientoEsperadoKg());
        entity.setEstado(cultivo.getEstado());
        return entity;
    }
}

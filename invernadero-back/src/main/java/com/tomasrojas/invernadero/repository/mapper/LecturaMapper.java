package com.tomasrojas.invernadero.repository.mapper;

import com.tomasrojas.invernadero.model.LecturaAmbiental;
import com.tomasrojas.invernadero.model.entity.LecturaEntity;
import com.tomasrojas.invernadero.model.entity.ZonaEntity;
import org.springframework.stereotype.Component;

@Component
public class LecturaMapper {

    private final ZonaMapper zonaMapper;

    public LecturaMapper(ZonaMapper zonaMapper) {
        this.zonaMapper = zonaMapper;
    }

    public LecturaAmbiental toDomain(LecturaEntity entity) {
        LecturaAmbiental l = new LecturaAmbiental();
        l.setId(entity.getId());
        l.setZona(zonaMapper.toDomain(entity.getZona()));
        l.setTipo(entity.getTipo());
        l.setValor(entity.getValor());
        l.setFuente(entity.getFuente());
        l.setNotas(entity.getNotas());
        l.setRegistradoEn(entity.getRegistradoEn());
        return l;
    }

    public LecturaEntity toEntity(LecturaAmbiental lectura, ZonaEntity zonaEntity) {
        LecturaEntity entity = new LecturaEntity();
        entity.setZona(zonaEntity);
        entity.setTipo(lectura.getTipo());
        entity.setValor(lectura.getValor());
        entity.setFuente(lectura.getFuente());
        entity.setNotas(lectura.getNotas());
        return entity;
    }
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  WebDtoMapper.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Conversor entre clases de dominio y DTOs de la capa HTTP.
 *              Centraliza todas las conversiones para evitar duplicación
 *              en cada controller.
 */
package com.tomasrojas.invernadero.api.mapper;

import com.tomasrojas.invernadero.api.dto.*;
import com.tomasrojas.invernadero.model.Cultivo;
import com.tomasrojas.invernadero.model.LecturaAmbiental;
import com.tomasrojas.invernadero.model.UmbralAmbiental;
import com.tomasrojas.invernadero.model.Zona;
import org.springframework.stereotype.Component;

/**
 * Conversor centralizado entre el modelo de dominio y los DTOs de la API REST.
 *
 * <p>Cada controller inyecta este componente para transformar los objetos
 * de dominio que devuelven los servicios en los DTOs que se serializan a JSON.</p>
 */
@Component
public class WebDtoMapper {

    /**
     * Convierte una {@link Zona} de dominio en su DTO de respuesta.
     *
     * @param zona objeto de dominio
     * @return DTO listo para serializar a JSON
     */
    public ZonaResponse toZonaResponse(Zona zona) {
        return new ZonaResponse(
                zona.getId(),
                zona.getNombre(),
                zona.getDescripcion(),
                zona.getCreadoEn()
        );
    }

    /**
     * Convierte una {@link LecturaAmbiental} de dominio en su DTO de respuesta.
     *
     * @param lectura objeto de dominio
     * @return DTO listo para serializar a JSON
     */
    public LecturaResponse toLecturaResponse(LecturaAmbiental lectura) {
        return new LecturaResponse(
                lectura.getId(),
                lectura.getZona().getId(),
                lectura.getTipo(),
                lectura.getValor(),
                lectura.getRegistradoEn()
        );
    }

    /**
     * Convierte un {@link Cultivo} de dominio en su DTO de respuesta.
     *
     * @param cultivo objeto de dominio
     * @return DTO listo para serializar a JSON
     */
    public CultivoResponse toCultivoResponse(Cultivo cultivo) {
        return new CultivoResponse(
                cultivo.getId(),
                cultivo.getZona().getId(),
                cultivo.getNombre(),
                cultivo.getVariedad(),
                cultivo.getNotas(),
                cultivo.getPlantadoEn(),
                cultivo.getCreadoEn()
        );
    }

    /**
     * Convierte un {@link UmbralAmbiental} de dominio en su DTO de respuesta.
     *
     * @param umbral objeto de dominio
     * @return DTO listo para serializar a JSON
     */
    public UmbralResponse toUmbralResponse(UmbralAmbiental umbral) {
        return new UmbralResponse(
                umbral.getId(),
                umbral.getZona().getId(),
                umbral.getTipo(),
                umbral.getValorMin(),
                umbral.getValorMax(),
                umbral.getActualizadoEn()
        );
    }
}

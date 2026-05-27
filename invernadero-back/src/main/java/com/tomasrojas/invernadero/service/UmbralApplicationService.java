/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralApplicationService.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Servicio de aplicación para la gestión de umbrales ambientales.
 *              Implementa la lógica de upsert: crea el umbral si no existe
 *              para ese tipo de métrica en la zona, o lo actualiza si ya existe.
 */
package com.tomasrojas.invernadero.service;

import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.model.UmbralAmbiental;
import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.repository.UmbralPersistencePort;
import com.tomasrojas.invernadero.repository.ZonaPersistencePort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Servicio de aplicación que gestiona los {@link UmbralAmbiental} de cada zona.
 *
 * <p>La regla de negocio principal es el <strong>upsert por tipo</strong>:
 * cada zona tiene como máximo un umbral por tipo de métrica. Si el usuario
 * define un umbral para {@code TEMPERATURA_C} en una zona que ya tiene uno,
 * el existente se actualiza en lugar de crear un duplicado.</p>
 */
@Service
public class UmbralApplicationService {

    private final UmbralPersistencePort umbralPort;
    private final ZonaPersistencePort zonaPort;

    /**
     * @param umbralPort puerto de persistencia de umbrales
     * @param zonaPort   puerto de persistencia de zonas, para validar existencia
     */
    public UmbralApplicationService(UmbralPersistencePort umbralPort,
                                     ZonaPersistencePort zonaPort) {
        this.umbralPort = umbralPort;
        this.zonaPort = zonaPort;
    }

    /**
     * Define o actualiza el umbral de una métrica para una zona (upsert).
     *
     * <p>Si ya existe un umbral del mismo tipo en la zona, se reutiliza su id
     * y se actualizan los valores. Si no existe, se crea uno nuevo.</p>
     *
     * @param zonaId   identificador de la zona
     * @param tipo     tipo de métrica ambiental
     * @param valorMin límite inferior aceptable (nullable)
     * @param valorMax límite superior aceptable (nullable)
     * @return el umbral creado o actualizado
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public UmbralAmbiental definir(UUID zonaId, MetricaTipo tipo,
                                   BigDecimal valorMin, BigDecimal valorMax) {
        Zona zona = zonaPort.buscarPorId(zonaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Zona", zonaId));

        // Upsert: reutiliza el id del umbral existente si ya hay uno para este tipo
        UmbralAmbiental umbral = umbralPort.buscarPorZonaYTipo(zonaId, tipo)
                .orElse(new UmbralAmbiental());

        umbral.setZona(zona);
        umbral.setTipo(tipo);
        umbral.setValorMin(valorMin);
        umbral.setValorMax(valorMax);
        return umbralPort.guardar(umbral);
    }

    /**
     * Retorna todos los umbrales configurados para una zona.
     *
     * @param zonaId identificador de la zona
     * @return lista de umbrales, vacía si no hay ninguno definido
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public List<UmbralAmbiental> listarPorZona(UUID zonaId) {
        if (!zonaPort.existePorId(zonaId)) {
            throw new RecursoNoEncontradoException("Zona", zonaId);
        }
        return umbralPort.listarPorZona(zonaId);
    }
}

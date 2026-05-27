/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  UmbralPersistencePort.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Puerto de persistencia para umbrales ambientales.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.model.UmbralAmbiental;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistencia para {@link UmbralAmbiental}.
 */
public interface UmbralPersistencePort {

    /**
     * Persiste un umbral nuevo o actualiza uno existente.
     *
     * @param umbral umbral a guardar
     * @return el umbral guardado con su id asignado
     */
    UmbralAmbiental guardar(UmbralAmbiental umbral);

    /**
     * Retorna todos los umbrales definidos para una zona.
     *
     * @param zonaId identificador de la zona
     * @return lista de umbrales, vacía si no hay ninguno configurado
     */
    List<UmbralAmbiental> listarPorZona(UUID zonaId);

    /**
     * Busca el umbral existente para una zona y tipo de métrica específicos.
     *
     * <p>Se usa para decidir si crear un umbral nuevo o actualizar el existente.</p>
     *
     * @param zonaId identificador de la zona
     * @param tipo   tipo de métrica ambiental
     * @return umbral existente si hay uno, vacío si no
     */
    Optional<UmbralAmbiental> buscarPorZonaYTipo(UUID zonaId, MetricaTipo tipo);

    /**
     * Elimina todos los umbrales de una zona.
     * Se usa en cascada al eliminar una zona.
     *
     * @param zonaId identificador de la zona
     */
    void eliminarPorZona(UUID zonaId);
}

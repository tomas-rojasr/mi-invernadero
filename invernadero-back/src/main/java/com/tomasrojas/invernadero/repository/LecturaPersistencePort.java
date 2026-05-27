/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaPersistencePort.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Puerto de persistencia para lecturas ambientales.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.LecturaAmbiental;

import java.util.List;
import java.util.UUID;

/**
 * Contrato de persistencia para {@link LecturaAmbiental}.
 *
 * <p>Las lecturas son inmutables una vez registradas: solo se soportan
 * operaciones de escritura y consulta, nunca de actualización individual.</p>
 */
public interface LecturaPersistencePort {

    /**
     * Persiste una nueva lectura ambiental.
     *
     * @param lectura lectura a guardar
     * @return la lectura guardada con su id asignado
     */
    LecturaAmbiental guardar(LecturaAmbiental lectura);

    /**
     * Retorna las últimas {@code limite} lecturas de una zona, ordenadas
     * de más reciente a más antigua.
     *
     * @param zonaId identificador de la zona
     * @param limite número máximo de lecturas a retornar
     * @return lista de lecturas, vacía si la zona no tiene ninguna
     */
    List<LecturaAmbiental> listarPorZona(UUID zonaId, int limite);

    /**
     * Elimina una lectura por su identificador.
     *
     * @param id identificador de la lectura
     */
    void eliminarPorId(UUID id);

    /**
     * Elimina todas las lecturas asociadas a una zona.
     * Se usa en cascada al eliminar una zona.
     *
     * @param zonaId identificador de la zona
     */
    void eliminarPorZona(UUID zonaId);
}

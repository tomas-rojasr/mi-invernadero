/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoPersistencePort.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Puerto de persistencia para cultivos del invernadero.
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.Cultivo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistencia para {@link Cultivo}.
 */
public interface CultivoPersistencePort {

    /**
     * Persiste un cultivo nuevo.
     *
     * @param cultivo cultivo a guardar
     * @return el cultivo guardado con su id asignado
     */
    Cultivo guardar(Cultivo cultivo);

    /**
     * Retorna todos los cultivos de una zona ordenados por fecha de creación descendente.
     *
     * @param zonaId identificador de la zona
     * @return lista de cultivos, vacía si la zona no tiene ninguno
     */
    List<Cultivo> listarPorZona(UUID zonaId);

    /**
     * Busca un cultivo por su identificador.
     *
     * @param id identificador del cultivo
     * @return el cultivo encontrado, o vacío si no existe
     */
    Optional<Cultivo> buscarPorId(UUID id);

    /**
     * Elimina un cultivo por su identificador.
     *
     * @param id identificador del cultivo
     */
    void eliminarPorId(UUID id);

    /**
     * Elimina todos los cultivos asociados a una zona.
     * Se usa en cascada al eliminar una zona.
     *
     * @param zonaId identificador de la zona
     */
    void eliminarPorZona(UUID zonaId);
}

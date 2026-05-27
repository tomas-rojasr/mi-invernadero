/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaPersistencePort.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Puerto de persistencia para la entidad de dominio Zona.
 *              Define el contrato que debe cumplir cualquier implementación
 *              de almacenamiento (PostgreSQL, en memoria, etc.).
 */
package com.tomasrojas.invernadero.repository;

import com.tomasrojas.invernadero.model.Zona;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Contrato de persistencia para {@link Zona}.
 *
 * <p>Al ser una interfaz del paquete {@code repository}, los servicios
 * de aplicación dependen de esta abstracción y no de JPA directamente.
 * Esto permite sustituir la implementación sin modificar la lógica de negocio.</p>
 */
public interface ZonaPersistencePort {

    /**
     * Persiste una zona nueva o actualiza una existente.
     *
     * @param zona zona a guardar
     * @return la zona guardada con su id asignado
     */
    Zona guardar(Zona zona);

    /**
     * Busca una zona por su identificador único.
     *
     * @param id identificador de la zona
     * @return un {@link Optional} con la zona si existe, vacío si no
     */
    Optional<Zona> buscarPorId(UUID id);

    /**
     * Retorna todas las zonas registradas en el sistema.
     *
     * @return lista de zonas, vacía si no hay ninguna
     */
    List<Zona> listarTodas();

    /**
     * Elimina la zona con el identificador dado.
     *
     * @param id identificador de la zona a eliminar
     */
    void eliminar(UUID id);

    /**
     * Verifica si existe una zona con el identificador dado.
     *
     * @param id identificador a verificar
     * @return {@code true} si existe, {@code false} si no
     */
    boolean existePorId(UUID id);
}

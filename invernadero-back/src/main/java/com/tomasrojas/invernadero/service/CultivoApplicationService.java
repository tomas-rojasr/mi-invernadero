/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  CultivoApplicationService.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Servicio de aplicación para la gestión de cultivos por zona.
 */
package com.tomasrojas.invernadero.service;

import com.tomasrojas.invernadero.model.Cultivo;
import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.repository.CultivoPersistencePort;
import com.tomasrojas.invernadero.repository.ZonaPersistencePort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Servicio de aplicación que gestiona los {@link Cultivo} de cada zona.
 */
@Service
public class CultivoApplicationService {

    private final CultivoPersistencePort cultivoPort;
    private final ZonaPersistencePort zonaPort;

    /**
     * @param cultivoPort puerto de persistencia de cultivos
     * @param zonaPort    puerto de persistencia de zonas, para validar existencia
     */
    public CultivoApplicationService(CultivoPersistencePort cultivoPort,
                                      ZonaPersistencePort zonaPort) {
        this.cultivoPort = cultivoPort;
        this.zonaPort = zonaPort;
    }

    /**
     * Registra un nuevo cultivo en una zona del invernadero.
     *
     * @param zonaId      identificador de la zona donde se siembra
     * @param nombre      nombre del cultivo
     * @param variedad    variedad del cultivo (opcional, puede ser null)
     * @param notas       notas de seguimiento (opcional, puede ser null)
     * @param plantadoEn  fecha de siembra; si es null se usa el momento actual
     * @return el cultivo creado con su id asignado
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public Cultivo crear(UUID zonaId, String nombre, String variedad,
                         String notas, Instant plantadoEn) {
        Zona zona = zonaPort.buscarPorId(zonaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Zona", zonaId));

        Cultivo cultivo = new Cultivo();
        cultivo.setZona(zona);
        cultivo.setNombre(nombre);
        cultivo.setVariedad(variedad);
        cultivo.setNotas(notas);
        cultivo.setPlantadoEn(plantadoEn != null ? plantadoEn : Instant.now());
        return cultivoPort.guardar(cultivo);
    }

    /**
     * Retorna todos los cultivos de una zona ordenados del más reciente al más antiguo.
     *
     * @param zonaId identificador de la zona
     * @return lista de cultivos de la zona
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public List<Cultivo> listarPorZona(UUID zonaId) {
        if (!zonaPort.existePorId(zonaId)) {
            throw new RecursoNoEncontradoException("Zona", zonaId);
        }
        return cultivoPort.listarPorZona(zonaId);
    }

    /**
     * Actualiza los datos editables de un cultivo existente.
     *
     * @param cultivoId identificador del cultivo
     * @param nombre    nuevo nombre del cultivo
     * @param variedad  nueva variedad (puede ser null)
     * @param notas     nuevas notas (puede ser null)
     * @return el cultivo actualizado
     * @throws RecursoNoEncontradoException si el cultivo no existe
     */
    public Cultivo actualizar(UUID cultivoId, String nombre, String variedad, String notas) {
        Cultivo cultivo = cultivoPort.buscarPorId(cultivoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cultivo", cultivoId));
        cultivo.setNombre(nombre);
        cultivo.setVariedad(variedad);
        cultivo.setNotas(notas);
        return cultivoPort.guardar(cultivo);
    }

    /**
     * Elimina un cultivo por su identificador.
     *
     * @param cultivoId identificador del cultivo a eliminar
     */
    public void eliminar(UUID cultivoId) {
        cultivoPort.eliminarPorId(cultivoId);
    }
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  LecturaApplicationService.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Servicio de aplicación para el registro y consulta
 *              de lecturas ambientales por zona.
 */
package com.tomasrojas.invernadero.service;

import com.tomasrojas.invernadero.model.LecturaAmbiental;
import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.repository.LecturaPersistencePort;
import com.tomasrojas.invernadero.repository.ZonaPersistencePort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Servicio de aplicación que gestiona el registro y consulta
 * de {@link LecturaAmbiental} para una zona del invernadero.
 */
@Service
public class LecturaApplicationService {

    /** Número máximo de lecturas devueltas si el cliente no especifica límite. */
    private static final int LIMITE_DEFAULT = 50;

    private final LecturaPersistencePort lecturaPort;
    private final ZonaPersistencePort zonaPort;

    /**
     * @param lecturaPort puerto de persistencia de lecturas
     * @param zonaPort    puerto de persistencia de zonas, para validar existencia
     */
    public LecturaApplicationService(LecturaPersistencePort lecturaPort,
                                      ZonaPersistencePort zonaPort) {
        this.lecturaPort = lecturaPort;
        this.zonaPort = zonaPort;
    }

    /**
     * Registra una nueva lectura ambiental en una zona.
     *
     * @param zonaId identificador de la zona
     * @param tipo   tipo de métrica ambiental medida
     * @param valor  valor de la medición
     * @return la lectura registrada con su id y marca de tiempo asignados
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public LecturaAmbiental registrar(UUID zonaId, MetricaTipo tipo, BigDecimal valor,
                                       com.tomasrojas.invernadero.model.FuenteLectura fuente,
                                       String notas) {
        Zona zona = zonaPort.buscarPorId(zonaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Zona", zonaId));

        LecturaAmbiental lectura = new LecturaAmbiental();
        lectura.setZona(zona);
        lectura.setTipo(tipo);
        lectura.setValor(valor);
        lectura.setFuente(fuente != null ? fuente : com.tomasrojas.invernadero.model.FuenteLectura.MANUAL);
        lectura.setNotas(notas);
        return lecturaPort.guardar(lectura);
    }

    /**
     * Retorna las lecturas más recientes de una zona.
     *
     * @param zonaId identificador de la zona
     * @param limite número máximo de lecturas; si es nulo o menor a 1 usa el límite por defecto
     * @return lista de lecturas ordenadas de más reciente a más antigua
     * @throws RecursoNoEncontradoException si la zona no existe
     */
    public List<LecturaAmbiental> listarPorZona(UUID zonaId, Integer limite) {
        if (!zonaPort.existePorId(zonaId)) {
            throw new RecursoNoEncontradoException("Zona", zonaId);
        }
        int limiteFinal = (limite == null || limite < 1) ? LIMITE_DEFAULT : limite;
        return lecturaPort.listarPorZona(zonaId, limiteFinal);
    }

    /**
     * Elimina una lectura por su identificador.
     *
     * @param lecturaId identificador de la lectura a eliminar
     */
    public void eliminar(UUID lecturaId) {
        lecturaPort.eliminarPorId(lecturaId);
    }
}

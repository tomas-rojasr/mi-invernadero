/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaApplicationService.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Servicio de aplicación para la gestión de zonas del invernadero.
 *              Contiene la lógica de negocio principal: creación, consulta
 *              y eliminación en cascada de zonas con sus datos asociados.
 */
package com.tomasrojas.invernadero.service;

import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.repository.CultivoPersistencePort;
import com.tomasrojas.invernadero.repository.LecturaPersistencePort;
import com.tomasrojas.invernadero.repository.UmbralPersistencePort;
import com.tomasrojas.invernadero.repository.ZonaPersistencePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Servicio de aplicación que orquesta los casos de uso relacionados con {@link Zona}.
 *
 * <p>Depende exclusivamente de los puertos de persistencia (interfaces), no de
 * implementaciones concretas. Esto garantiza que la lógica de negocio no está
 * acoplada a ninguna tecnología de base de datos.</p>
 */
@Service
public class ZonaApplicationService {

    private final ZonaPersistencePort zonaPort;
    private final LecturaPersistencePort lecturaPort;
    private final CultivoPersistencePort cultivoPort;
    private final UmbralPersistencePort umbralPort;

    /**
     * @param zonaPort    puerto de persistencia de zonas
     * @param lecturaPort puerto de persistencia de lecturas
     * @param cultivoPort puerto de persistencia de cultivos
     * @param umbralPort  puerto de persistencia de umbrales
     */
    public ZonaApplicationService(ZonaPersistencePort zonaPort,
                                   LecturaPersistencePort lecturaPort,
                                   CultivoPersistencePort cultivoPort,
                                   UmbralPersistencePort umbralPort) {
        this.zonaPort = zonaPort;
        this.lecturaPort = lecturaPort;
        this.cultivoPort = cultivoPort;
        this.umbralPort = umbralPort;
    }

    /**
     * Crea y persiste una nueva zona en el invernadero.
     *
     * @param nombre      nombre de la zona
     * @param descripcion descripción de la zona
     * @return la zona creada con su id asignado
     */
    public Zona crear(String nombre, String descripcion, String ubicacion,
                      com.tomasrojas.invernadero.model.TipoZona tipo,
                      java.math.BigDecimal areaM2) {
        Zona zona = new Zona();
        zona.setNombre(nombre);
        zona.setDescripcion(descripcion);
        zona.setUbicacion(ubicacion);
        zona.setTipo(tipo);
        zona.setAreaM2(areaM2);
        return zonaPort.guardar(zona);
    }

    /**
     * Busca una zona por su identificador.
     *
     * @param id identificador de la zona
     * @return la zona encontrada
     * @throws RecursoNoEncontradoException si no existe una zona con ese id
     */
    public Zona buscarPorId(UUID id) {
        return zonaPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Zona", id));
    }

    /**
     * Retorna todas las zonas registradas en el sistema.
     *
     * @return lista de zonas, vacía si no hay ninguna
     */
    public List<Zona> listarTodas() {
        return zonaPort.listarTodas();
    }

    /**
     * Elimina una zona y todos sus datos asociados (lecturas, cultivos y umbrales).
     *
     * <p>El orden de eliminación importa: primero se borran los registros dependientes
     * para evitar violaciones de restricciones de clave foránea en la base de datos.</p>
     *
     * @param id identificador de la zona a eliminar
     * @throws RecursoNoEncontradoException si no existe una zona con ese id
     */
    @Transactional
    public void eliminar(UUID id) {
        if (!zonaPort.existePorId(id)) {
            throw new RecursoNoEncontradoException("Zona", id);
        }
        // Primero se eliminan los datos dependientes, luego la zona
        lecturaPort.eliminarPorZona(id);
        cultivoPort.eliminarPorZona(id);
        umbralPort.eliminarPorZona(id);
        zonaPort.eliminar(id);
    }
}

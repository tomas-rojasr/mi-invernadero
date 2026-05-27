/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  SampleDataLoader.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Carga datos de demostración al arrancar el servidor si la base de datos
 *              está vacía y la propiedad app.sample-data.enabled es true.
 *              Permite probar la API inmediatamente sin insertar datos manualmente.
 */
package com.tomasrojas.invernadero.config;

import com.tomasrojas.invernadero.model.MetricaTipo;
import com.tomasrojas.invernadero.service.CultivoApplicationService;
import com.tomasrojas.invernadero.service.LecturaApplicationService;
import com.tomasrojas.invernadero.service.UmbralApplicationService;
import com.tomasrojas.invernadero.service.ZonaApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Cargador de datos de demostración que se ejecuta una sola vez al inicio.
 *
 * <p>Solo inserta datos si la base de datos está vacía ({@code listarTodas().isEmpty()})
 * y la propiedad {@code app.sample-data.enabled} es {@code true}. En pruebas
 * se desactiva mediante {@code application.yaml} del perfil de test.</p>
 */
@Component
public class SampleDataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataLoader.class);

    private final AppProperties appProperties;
    private final ZonaApplicationService zonaService;
    private final LecturaApplicationService lecturaService;
    private final CultivoApplicationService cultivoService;
    private final UmbralApplicationService umbralService;

    /**
     * @param appProperties  propiedades de la aplicación
     * @param zonaService    servicio de zonas
     * @param lecturaService servicio de lecturas
     * @param cultivoService servicio de cultivos
     * @param umbralService  servicio de umbrales
     */
    public SampleDataLoader(AppProperties appProperties,
                            ZonaApplicationService zonaService,
                            LecturaApplicationService lecturaService,
                            CultivoApplicationService cultivoService,
                            UmbralApplicationService umbralService) {
        this.appProperties = appProperties;
        this.zonaService = zonaService;
        this.lecturaService = lecturaService;
        this.cultivoService = cultivoService;
        this.umbralService = umbralService;
    }

    /**
     * Punto de entrada del cargador. Se ejecuta después de que Spring arranca completamente.
     *
     * @param args argumentos de la aplicación (no usados)
     */
    @Override
    public void run(ApplicationArguments args) {
        if (!appProperties.getSampleData().isEnabled()) {
            return;
        }

        if (!zonaService.listarTodas().isEmpty()) {
            log.info("La base de datos ya contiene datos. Se omite la carga de demostración.");
            return;
        }

        log.info("Cargando datos de demostración...");

        // Zona demo
        var zona = zonaService.crear(
                "Zona Norte",
                "Zona principal de cultivos de hoja. Automatización en curso."
        );

        // Lectura demo
        lecturaService.registrar(zona.getId(), MetricaTipo.TEMPERATURA_C, new BigDecimal("22.5"));
        lecturaService.registrar(zona.getId(), MetricaTipo.HUMEDAD_RELATIVA_PCT, new BigDecimal("65.0"));

        // Cultivo demo
        cultivoService.crear(zona.getId(), "Lechuga", "Romana", "Riego cada 2 días", null);

        // Umbral demo
        umbralService.definir(zona.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("18.0"), new BigDecimal("26.0"));

        log.info("Datos de demostración cargados correctamente para zona: {}", zona.getNombre());
    }
}

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

import com.tomasrojas.invernadero.model.*;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;

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

        // ── Zona 1: Invernadero principal ─────────────────────────────────────
        var zonaNorte = zonaService.crear(
                "Zona Norte — Invernadero A",
                "Zona principal de cultivos de hoja bajo cubierta plástica.",
                "Bloque A, nave 1",
                TipoZona.INVERNADERO,
                new BigDecimal("120.00")
        );

        cultivoService.crear(
                zonaNorte.getId(),
                "Lechuga",
                "Batavia",
                "Riego por goteo, 3 veces por semana",
                Instant.now().minus(30, ChronoUnit.DAYS),
                Instant.now().plus(15, ChronoUnit.DAYS),
                new BigDecimal("40.00"),
                200,
                new BigDecimal("80.00"),
                EstadoCultivo.ACTIVO
        );

        cultivoService.crear(
                zonaNorte.getId(),
                "Espinaca",
                "Baby Leaf",
                "Cosecha escalonada cada 10 días",
                Instant.now().minus(45, ChronoUnit.DAYS),
                Instant.now().plus(5, ChronoUnit.DAYS),
                new BigDecimal("30.00"),
                150,
                new BigDecimal("45.00"),
                EstadoCultivo.ACTIVO
        );

        lecturaService.registrar(zonaNorte.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("22.5"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaNorte.getId(), MetricaTipo.HUMEDAD_RELATIVA_PCT,
                new BigDecimal("68.0"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaNorte.getId(), MetricaTipo.LUZ_LUX,
                new BigDecimal("4200.0"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaNorte.getId(), MetricaTipo.HUMEDAD_SUELO_PCT,
                new BigDecimal("55.0"), FuenteLectura.MANUAL, "Medición matutina");

        umbralService.definir(zonaNorte.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("18.0"), new BigDecimal("28.0"));
        umbralService.definir(zonaNorte.getId(), MetricaTipo.HUMEDAD_RELATIVA_PCT,
                new BigDecimal("55.0"), new BigDecimal("80.0"));

        // ── Zona 2: Campo abierto ─────────────────────────────────────────────
        var zonaSur = zonaService.crear(
                "Zona Sur — Campo Abierto",
                "Cultivos de raíz y legumbres al aire libre.",
                "Parcela B, sector sur",
                TipoZona.CAMPO_ABIERTO,
                new BigDecimal("250.00")
        );

        cultivoService.crear(
                zonaSur.getId(),
                "Tomate",
                "Cherry Roma",
                "Tutorado con estacas. Revisión semanal de plagas.",
                Instant.now().minus(60, ChronoUnit.DAYS),
                Instant.now().plus(30, ChronoUnit.DAYS),
                new BigDecimal("80.00"),
                120,
                new BigDecimal("240.00"),
                EstadoCultivo.ACTIVO
        );

        cultivoService.crear(
                zonaSur.getId(),
                "Zanahoria",
                "Nantes",
                "Suelo suelto y profundo. Cosecha ya completada.",
                Instant.now().minus(90, ChronoUnit.DAYS),
                Instant.now().minus(5, ChronoUnit.DAYS),
                new BigDecimal("60.00"),
                300,
                new BigDecimal("180.00"),
                EstadoCultivo.COSECHADO
        );

        lecturaService.registrar(zonaSur.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("25.3"), FuenteLectura.MANUAL, "Medición tarde");
        lecturaService.registrar(zonaSur.getId(), MetricaTipo.HUMEDAD_SUELO_PCT,
                new BigDecimal("42.0"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaSur.getId(), MetricaTipo.LUZ_LUX,
                new BigDecimal("18500.0"), FuenteLectura.SENSOR_AUTOMATICO, "Alta radiación solar");

        umbralService.definir(zonaSur.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("15.0"), new BigDecimal("35.0"));
        umbralService.definir(zonaSur.getId(), MetricaTipo.HUMEDAD_SUELO_PCT,
                new BigDecimal("30.0"), new BigDecimal("70.0"));

        // ── Zona 3: Hidropónico ────────────────────────────────────────────────
        var zonaHidro = zonaService.crear(
                "Zona Hidropónica — Sistema NFT",
                "Cultivo sin suelo en canal de película nutritiva (NFT).",
                "Módulo C, nivel 2",
                TipoZona.HIDROPONICO,
                new BigDecimal("45.00")
        );

        cultivoService.crear(
                zonaHidro.getId(),
                "Albahaca",
                "Genovese",
                "Alta demanda de luz. Cosecha de hojas cada 3 semanas.",
                Instant.now().minus(20, ChronoUnit.DAYS),
                Instant.now().plus(40, ChronoUnit.DAYS),
                new BigDecimal("15.00"),
                80,
                new BigDecimal("24.00"),
                EstadoCultivo.ACTIVO
        );

        lecturaService.registrar(zonaHidro.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("23.8"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaHidro.getId(), MetricaTipo.HUMEDAD_RELATIVA_PCT,
                new BigDecimal("72.5"), FuenteLectura.SENSOR_AUTOMATICO, null);
        lecturaService.registrar(zonaHidro.getId(), MetricaTipo.LUZ_LUX,
                new BigDecimal("6800.0"), FuenteLectura.SENSOR_AUTOMATICO, "LED 18h/día");

        umbralService.definir(zonaHidro.getId(), MetricaTipo.TEMPERATURA_C,
                new BigDecimal("20.0"), new BigDecimal("26.0"));
        umbralService.definir(zonaHidro.getId(), MetricaTipo.HUMEDAD_RELATIVA_PCT,
                new BigDecimal("60.0"), new BigDecimal("85.0"));

        log.info("Datos de demostración cargados: 3 zonas, cultivos, lecturas y umbrales.");
    }
}

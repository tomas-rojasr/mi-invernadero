/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  MetricaTipo.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Enumeración de los tipos de métricas ambientales
 *              que se pueden registrar por zona del invernadero.
 */
package com.tomasrojas.invernadero.model;

/**
 * Define los tipos de métricas ambientales soportados por el sistema.
 *
 * <p>Cada valor representa una magnitud física distinta que puede medirse
 * en una zona del invernadero y almacenarse como {@code LecturaAmbiental}.</p>
 */
public enum MetricaTipo {

    /** Temperatura del ambiente en grados Celsius. */
    TEMPERATURA_C,

    /** Humedad relativa del ambiente expresada en porcentaje (0–100). */
    HUMEDAD_RELATIVA_PCT,

    /** Intensidad lumínica medida en lux. */
    LUZ_LUX,

    /** Humedad del suelo expresada en porcentaje (0–100). */
    HUMEDAD_SUELO_PCT
}

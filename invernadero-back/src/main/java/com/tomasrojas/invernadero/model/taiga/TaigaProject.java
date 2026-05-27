/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  TaigaProject.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-27
 */
package com.tomasrojas.invernadero.model.taiga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/** Modelo mínimo de proyecto de Taiga (solo se necesita el id). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaigaProject {

    private Long id;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}

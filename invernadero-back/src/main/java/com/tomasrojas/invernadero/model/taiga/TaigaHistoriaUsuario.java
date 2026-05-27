/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  TaigaHistoriaUsuario.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Modelo de respuesta de la API de Taiga para una historia de usuario.
 *              Solo contiene los campos relevantes para el proyecto.
 */
package com.tomasrojas.invernadero.model.taiga;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa una historia de usuario retornada por la API de Taiga.io.
 *
 * <p>{@code @JsonIgnoreProperties(ignoreUnknown = true)} permite que Jackson
 * ignore los campos de Taiga que no se mapean aquí, evitando errores de
 * deserialización cuando Taiga agrega nuevos campos a su API.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaigaHistoriaUsuario {

    /** Identificador numérico de la historia en Taiga. */
    private Long id;

    /** Referencia legible de la historia (ej. #12). */
    private Integer ref;

    /** Título de la historia de usuario. */
    private String subject;

    /** Estado actual de la historia (ej. "New", "In Progress", "Done"). */
    private String status;

    // ── Getters y Setters ──────────────────────────────────────────────────

    /** @return el id de la historia en Taiga */
    public Long getId() { return id; }

    /** @param id id de la historia */
    public void setId(Long id) { this.id = id; }

    /** @return la referencia legible de la historia */
    public Integer getRef() { return ref; }

    /** @param ref referencia legible */
    public void setRef(Integer ref) { this.ref = ref; }

    /** @return el título de la historia */
    public String getSubject() { return subject; }

    /** @param subject título de la historia */
    public void setSubject(String subject) { this.subject = subject; }

    /** @return el estado actual de la historia */
    public String getStatus() { return status; }

    /** @param status estado de la historia */
    public void setStatus(String status) { this.status = status; }
}

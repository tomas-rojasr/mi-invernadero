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
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa una historia de usuario retornada por la API de Taiga.io.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaigaHistoriaUsuario {

    private Long id;
    private Integer ref;
    private String subject;

    @JsonProperty("status_extra_info")
    private StatusExtraInfo statusExtraInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusExtraInfo {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public String getStatusNombre() {
        return statusExtraInfo != null && statusExtraInfo.getName() != null
                ? statusExtraInfo.getName() : "—";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getRef() { return ref; }
    public void setRef(Integer ref) { this.ref = ref; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public StatusExtraInfo getStatusExtraInfo() { return statusExtraInfo; }
    public void setStatusExtraInfo(StatusExtraInfo statusExtraInfo) { this.statusExtraInfo = statusExtraInfo; }
}

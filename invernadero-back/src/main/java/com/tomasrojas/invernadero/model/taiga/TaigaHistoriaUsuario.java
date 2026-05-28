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

    @JsonProperty("assigned_to_extra_info")
    private AsignadoExtraInfo assignedToExtraInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StatusExtraInfo {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AsignadoExtraInfo {
        @JsonProperty("full_name_display")
        private String fullNameDisplay;
        private String username;

        public String getFullNameDisplay() { return fullNameDisplay; }
        public void setFullNameDisplay(String fullNameDisplay) { this.fullNameDisplay = fullNameDisplay; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }

    public String getStatusNombre() {
        return statusExtraInfo != null && statusExtraInfo.getName() != null
                ? statusExtraInfo.getName() : "—";
    }

    public String getAsignadoA() {
        if (assignedToExtraInfo == null) return null;
        String nombre = assignedToExtraInfo.getFullNameDisplay();
        return (nombre != null && !nombre.isBlank()) ? nombre : assignedToExtraInfo.getUsername();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getRef() { return ref; }
    public void setRef(Integer ref) { this.ref = ref; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public StatusExtraInfo getStatusExtraInfo() { return statusExtraInfo; }
    public void setStatusExtraInfo(StatusExtraInfo statusExtraInfo) { this.statusExtraInfo = statusExtraInfo; }

    public AsignadoExtraInfo getAssignedToExtraInfo() { return assignedToExtraInfo; }
    public void setAssignedToExtraInfo(AsignadoExtraInfo assignedToExtraInfo) { this.assignedToExtraInfo = assignedToExtraInfo; }
}

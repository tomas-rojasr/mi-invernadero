package com.tomasrojas.invernadero.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** Cultivo sembrado en una zona del invernadero. */
public class Cultivo {

    private UUID id;
    private Zona zona;
    private String nombre;
    private String variedad;
    private String notas;
    private Instant plantadoEn;
    private Instant fechaCosechaEstimada;
    private BigDecimal areaM2;
    private Integer cantidadSembrada;
    private BigDecimal rendimientoEsperadoKg;
    private EstadoCultivo estado;
    private Instant creadoEn;

    public Cultivo() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public Instant getPlantadoEn() { return plantadoEn; }
    public void setPlantadoEn(Instant plantadoEn) { this.plantadoEn = plantadoEn; }

    public Instant getFechaCosechaEstimada() { return fechaCosechaEstimada; }
    public void setFechaCosechaEstimada(Instant v) { this.fechaCosechaEstimada = v; }

    public BigDecimal getAreaM2() { return areaM2; }
    public void setAreaM2(BigDecimal areaM2) { this.areaM2 = areaM2; }

    public Integer getCantidadSembrada() { return cantidadSembrada; }
    public void setCantidadSembrada(Integer cantidadSembrada) { this.cantidadSembrada = cantidadSembrada; }

    public BigDecimal getRendimientoEsperadoKg() { return rendimientoEsperadoKg; }
    public void setRendimientoEsperadoKg(BigDecimal v) { this.rendimientoEsperadoKg = v; }

    public EstadoCultivo getEstado() { return estado; }
    public void setEstado(EstadoCultivo estado) { this.estado = estado; }

    public Instant getCreadoEn() { return creadoEn; }
    public void setCreadoEn(Instant creadoEn) { this.creadoEn = creadoEn; }
}

package com.tomasrojas.invernadero.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** Medición puntual de una variable ambiental en una zona. */
public class LecturaAmbiental {

    private UUID id;
    private Zona zona;
    private MetricaTipo tipo;
    private BigDecimal valor;
    private FuenteLectura fuente = FuenteLectura.MANUAL;
    private String notas;
    private Instant registradoEn;

    public LecturaAmbiental() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Zona getZona() { return zona; }
    public void setZona(Zona zona) { this.zona = zona; }

    public MetricaTipo getTipo() { return tipo; }
    public void setTipo(MetricaTipo tipo) { this.tipo = tipo; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public FuenteLectura getFuente() { return fuente; }
    public void setFuente(FuenteLectura fuente) { this.fuente = fuente; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public Instant getRegistradoEn() { return registradoEn; }
    public void setRegistradoEn(Instant registradoEn) { this.registradoEn = registradoEn; }
}

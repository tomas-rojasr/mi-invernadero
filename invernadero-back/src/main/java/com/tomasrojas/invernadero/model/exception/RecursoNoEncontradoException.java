/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  RecursoNoEncontradoException.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Excepción de dominio lanzada cuando un recurso solicitado
 *              no existe en la base de datos.
 */
package com.tomasrojas.invernadero.model.exception;

import java.util.UUID;

/**
 * Excepción lanzada cuando un recurso identificado por su UUID
 * no se encuentra en el sistema.
 *
 * <p>Al ser una {@code RuntimeException}, no requiere ser declarada
 * en las firmas de los métodos. El manejador global {@code RestExceptionHandler}
 * la captura y devuelve una respuesta HTTP 404.</p>
 */
public class RecursoNoEncontradoException extends RuntimeException {

    /**
     * Crea la excepción con un mensaje que incluye el tipo y el id del recurso ausente.
     *
     * @param tipo nombre del tipo de recurso (ej. "Zona", "Cultivo")
     * @param id   identificador UUID del recurso no encontrado
     */
    public RecursoNoEncontradoException(String tipo, UUID id) {
        super(tipo + " no encontrado con id: " + id);
    }
}

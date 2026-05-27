/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  RestExceptionHandler.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Manejador global de excepciones para la capa REST.
 *              Intercepta excepciones y las convierte en respuestas HTTP
 *              con el código de estado y mensaje apropiados.
 */
package com.tomasrojas.invernadero.api.exception;

import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interceptor global de excepciones que aplica a todos los controllers REST.
 *
 * <p>Usa {@link ProblemDetail} (RFC 9457) como formato estándar de error,
 * compatible con Spring Boot 3.x. Incluye mensajes i18n usando el
 * {@code Accept-Language} enviado por el cliente.</p>
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private final MessageSource messageSource;

    /**
     * @param messageSource fuente de mensajes i18n inyectada por Spring
     */
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Maneja recursos no encontrados y devuelve HTTP 404.
     *
     * @param ex     excepción lanzada por el servicio
     * @param locale idioma del cliente tomado del header {@code Accept-Language}
     * @return detalle del problema con mensaje localizado
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail handleNotFound(RecursoNoEncontradoException ex, Locale locale) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        detail.setTitle(messageSource.getMessage("error.not_found", null, "Resource not found", locale));
        return detail;
    }

    /**
     * Maneja errores de validación de DTOs ({@code @Valid}) y devuelve HTTP 400.
     * Incluye un mapa con los campos inválidos y sus mensajes de error.
     *
     * @param ex     excepción lanzada por Spring Validation
     * @param locale idioma del cliente
     * @return detalle del problema con todos los errores de campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, Locale locale) {
        Map<String, String> errores = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        e -> e.getDefaultMessage() != null ? e.getDefaultMessage() : "inválido"
                ));

        ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        detail.setTitle(messageSource.getMessage("error.validation", null, "Validation failed", locale));
        detail.setProperty("errores", errores);
        return detail;
    }
}

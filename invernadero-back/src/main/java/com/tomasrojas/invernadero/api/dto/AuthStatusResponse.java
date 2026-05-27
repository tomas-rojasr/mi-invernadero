/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  AuthStatusResponse.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: DTO de salida con el estado de autenticación de la sesión actual.
 */
package com.tomasrojas.invernadero.api.dto;

/**
 * Estado de autenticación OAuth2 devuelto por {@code GET /api/v1/auth/status}.
 *
 * <p>El frontend consulta este endpoint al iniciar para saber si el usuario
 * ya tiene sesión activa o debe redirigirlo a la pantalla de login.</p>
 *
 * @param authenticated  {@code true} si el usuario tiene sesión activa
 * @param oauth2Enabled  {@code true} si OAuth2 está habilitado en el servidor
 * @param loginUrl       URL de inicio de sesión Google (null si no autenticado o sin OAuth2)
 * @param email          correo del usuario autenticado (null si no autenticado)
 * @param name           nombre del usuario autenticado (null si no autenticado)
 */
public record AuthStatusResponse(
        boolean authenticated,
        boolean oauth2Enabled,
        String loginUrl,
        String email,
        String name
) {}

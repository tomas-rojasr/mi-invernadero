/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  AuthController.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Controller REST que expone el estado de autenticación OAuth2
 *              de la sesión actual para que el frontend pueda decidir
 *              si mostrar el panel o redirigir al login.
 */
package com.tomasrojas.invernadero.api;

import com.tomasrojas.invernadero.api.dto.AuthStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST que informa al frontend sobre el estado de autenticación.
 *
 * <p>El frontend llama a {@code GET /api/v1/auth/status} al iniciar para saber:
 * <ul>
 *   <li>Si el usuario ya tiene sesión activa (cookie de sesión válida).</li>
 *   <li>Si OAuth2 está habilitado y cuál es la URL de login.</li>
 *   <li>El email y nombre del usuario autenticado.</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Estado de la sesión OAuth2")
public class AuthController {

    /** Indica si OAuth2 está habilitado en esta instancia del servidor. */
    @Value("${app.security.oauth2-enabled:false}")
    private boolean oauth2Enabled;

    /**
     * Devuelve el estado de autenticación de la sesión actual.
     *
     * <p>Si OAuth2 está deshabilitado (entorno de desarrollo), siempre
     * responde {@code authenticated: true} para no bloquear el acceso al panel.</p>
     *
     * @param principal usuario autenticado por OAuth2, o null si no hay sesión
     * @return estado de autenticación serializado como JSON
     */
    @GetMapping("/status")
    @Operation(summary = "Consultar el estado de autenticación de la sesión actual")
    public AuthStatusResponse status(@AuthenticationPrincipal OAuth2User principal) {
        if (!oauth2Enabled) {
            // En desarrollo, OAuth2 está deshabilitado: acceso libre sin credenciales Google
            return new AuthStatusResponse(true, false, null, null, null);
        }

        if (principal != null) {
            return new AuthStatusResponse(
                    true,
                    true,
                    null,
                    principal.getAttribute("email"),
                    principal.getAttribute("name")
            );
        }

        return new AuthStatusResponse(false, true, "/oauth2/authorization/google", null, null);
    }
}

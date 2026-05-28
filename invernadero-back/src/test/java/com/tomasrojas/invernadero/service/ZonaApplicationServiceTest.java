/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  ZonaApplicationServiceTest.java
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Pruebas unitarias del servicio de zonas usando Mockito.
 *              Verifica la lógica de negocio aislada de la base de datos.
 */
package com.tomasrojas.invernadero.service;

import com.tomasrojas.invernadero.model.Zona;
import com.tomasrojas.invernadero.model.exception.RecursoNoEncontradoException;
import com.tomasrojas.invernadero.repository.CultivoPersistencePort;
import com.tomasrojas.invernadero.repository.LecturaPersistencePort;
import com.tomasrojas.invernadero.repository.UmbralPersistencePort;
import com.tomasrojas.invernadero.repository.ZonaPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias de {@link ZonaApplicationService}.
 *
 * <p>Se usa Mockito para simular los puertos de persistencia y verificar
 * que la lógica del servicio es correcta sin tocar la base de datos.</p>
 */
@ExtendWith(MockitoExtension.class)
class ZonaApplicationServiceTest {

    @Mock
    private ZonaPersistencePort zonaPort;

    @Mock
    private LecturaPersistencePort lecturaPort;

    @Mock
    private CultivoPersistencePort cultivoPort;

    @Mock
    private UmbralPersistencePort umbralPort;

    @InjectMocks
    private ZonaApplicationService service;

    private Zona zonaDemo;

    @BeforeEach
    void setUp() {
        zonaDemo = new Zona(
                UUID.randomUUID(),
                "Zona Norte",
                "Zona de prueba",
                null,
                null,
                null,
                Instant.now()
        );
    }

    @Test
    @DisplayName("crear() debe persistir y retornar la zona con los datos correctos")
    void crear_debePersistirYRetornarZona() {
        when(zonaPort.guardar(any(Zona.class))).thenReturn(zonaDemo);

        Zona resultado = service.crear("Zona Norte", "Zona de prueba", null, null, null);

        assertThat(resultado.getNombre()).isEqualTo("Zona Norte");
        assertThat(resultado.getDescripcion()).isEqualTo("Zona de prueba");
        verify(zonaPort, times(1)).guardar(any(Zona.class));
    }

    @Test
    @DisplayName("buscarPorId() debe retornar la zona cuando existe")
    void buscarPorId_debeRetornarZonaSiExiste() {
        when(zonaPort.buscarPorId(zonaDemo.getId())).thenReturn(Optional.of(zonaDemo));

        Zona resultado = service.buscarPorId(zonaDemo.getId());

        assertThat(resultado.getId()).isEqualTo(zonaDemo.getId());
        assertThat(resultado.getNombre()).isEqualTo("Zona Norte");
    }

    @Test
    @DisplayName("buscarPorId() debe lanzar excepción cuando la zona no existe")
    void buscarPorId_debeLanzarExcepcionSiNoExiste() {
        UUID idInexistente = UUID.randomUUID();
        when(zonaPort.buscarPorId(idInexistente)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(idInexistente))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining(idInexistente.toString());

        // No se deben invocar otros puertos cuando la zona no existe
        verifyNoInteractions(lecturaPort, cultivoPort, umbralPort);
    }

    @Test
    @DisplayName("listarTodas() debe retornar la lista completa de zonas")
    void listarTodas_debeRetornarListaCompleta() {
        when(zonaPort.listarTodas()).thenReturn(List.of(zonaDemo));

        List<Zona> resultado = service.listarTodas();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Zona Norte");
    }

    @Test
    @DisplayName("eliminar() debe borrar zona y todos sus datos en cascada")
    void eliminar_debeBorrarZonaYDatosDependientes() {
        when(zonaPort.existePorId(zonaDemo.getId())).thenReturn(true);

        service.eliminar(zonaDemo.getId());

        // Verifica que se eliminaron lecturas, cultivos y umbrales antes de la zona
        verify(lecturaPort).eliminarPorZona(zonaDemo.getId());
        verify(cultivoPort).eliminarPorZona(zonaDemo.getId());
        verify(umbralPort).eliminarPorZona(zonaDemo.getId());
        verify(zonaPort).eliminar(zonaDemo.getId());
    }

    @Test
    @DisplayName("eliminar() debe lanzar excepción si la zona no existe")
    void eliminar_debeLanzarExcepcionSiZonaNoExiste() {
        UUID idInexistente = UUID.randomUUID();
        when(zonaPort.existePorId(idInexistente)).thenReturn(false);

        assertThatThrownBy(() -> service.eliminar(idInexistente))
                .isInstanceOf(RecursoNoEncontradoException.class);

        // No se debe intentar borrar datos si la zona no existe
        verifyNoInteractions(lecturaPort, cultivoPort, umbralPort);
        verify(zonaPort, never()).eliminar(any());
    }
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  api/client.ts
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Cliente HTTP centralizado para comunicarse con el backend.
 *              Todas las peticiones a la API pasan por este módulo.
 */
import axios from 'axios';

const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

export const client = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
  headers: { 'Content-Type': 'application/json' },
});

// ── Tipos ──────────────────────────────────────────────────────────────────

export interface AuthStatus {
  authenticated: boolean;
  oauth2Enabled: boolean;
  email?: string;
}

export interface Zona {
  id: string;
  nombre: string;
  descripcion: string;
  creadoEn: string;
}

export interface Lectura {
  id: string;
  zonaId: string;
  tipo: string;
  valor: number;
  registradoEn: string;
}

export interface CrearZonaRequest {
  nombre: string;
  descripcion: string;
}

export interface RegistrarLecturaRequest {
  tipo: string;
  valor: number;
}

// ── Endpoints ──────────────────────────────────────────────────────────────

export const api = {
  auth: {
    status: () => client.get<AuthStatus>('/api/v1/auth/status'),
  },
  zonas: {
    listar: () => client.get<Zona[]>('/api/v1/zonas'),
    crear: (body: CrearZonaRequest) => client.post<Zona>('/api/v1/zonas', body),
    eliminar: (id: string) => client.delete(`/api/v1/zonas/${id}`),
  },
  lecturas: {
    listar: (zonaId: string) =>
      client.get<Lectura[]>(`/api/v1/zonas/${zonaId}/lecturas`),
    registrar: (zonaId: string, body: RegistrarLecturaRequest) =>
      client.post<Lectura>(`/api/v1/zonas/${zonaId}/lecturas`, body),
  },
};

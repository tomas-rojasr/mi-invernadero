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

export interface Cultivo {
  id: string;
  zonaId: string;
  nombre: string;
  descripcion: string;
  plantadoEn: string;
  creadoEn: string;
}

export interface Umbral {
  id: string;
  zonaId: string;
  tipo: string;
  minimo: number;
  maximo: number;
  actualizadoEn: string;
}

export interface CrearCultivoRequest {
  nombre: string;
  descripcion: string;
}

export interface DefinirUmbralRequest {
  tipo: string;
  minimo: number;
  maximo: number;
}

export interface TaigaHistoria {
  id: number;
  ref: number;
  subject: string;
  status: string;
}

export const METRICAS = [
  'TEMPERATURA_C',
  'HUMEDAD_RELATIVA_PCT',
  'LUZ_LUX',
  'HUMEDAD_SUELO_PCT',
] as const;

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
  cultivos: {
    listar: (zonaId: string) =>
      client.get<Cultivo[]>(`/api/v1/zonas/${zonaId}/cultivos`),
    crear: (zonaId: string, body: CrearCultivoRequest) =>
      client.post<Cultivo>(`/api/v1/zonas/${zonaId}/cultivos`, body),
  },
  umbrales: {
    listar: (zonaId: string) =>
      client.get<Umbral[]>(`/api/v1/zonas/${zonaId}/umbrales`),
    definir: (zonaId: string, body: DefinirUmbralRequest) =>
      client.put<Umbral>(`/api/v1/zonas/${zonaId}/umbrales`, body),
  },
  taiga: {
    buscar: (id: number) =>
      client.get<TaigaHistoria>(`/api/v1/taiga/historias/${id}`),
  },
};

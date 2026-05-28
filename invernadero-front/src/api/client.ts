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

export type TipoZona = 'CAMPO_ABIERTO' | 'INVERNADERO' | 'HIDROPONICO' | 'MACETAS';
export type FuenteLectura = 'MANUAL' | 'SENSOR_AUTOMATICO';
export type EstadoCultivo = 'ACTIVO' | 'EN_PREPARACION' | 'COSECHADO' | 'PERDIDO';

export interface Zona {
  id: string;
  nombre: string;
  descripcion: string;
  ubicacion: string | null;
  tipo: TipoZona | null;
  areaM2: number | null;
  creadoEn: string;
}

export interface Lectura {
  id: string;
  zonaId: string;
  tipo: string;
  valor: number;
  fuente: FuenteLectura;
  notas: string | null;
  registradoEn: string;
}

export interface Cultivo {
  id: string;
  zonaId: string;
  nombre: string;
  variedad: string | null;
  notas: string | null;
  plantadoEn: string;
  fechaCosechaEstimada: string | null;
  areaM2: number | null;
  cantidadSembrada: number | null;
  rendimientoEsperadoKg: number | null;
  estado: EstadoCultivo | null;
  creadoEn: string;
}

export interface Umbral {
  id: string;
  zonaId: string;
  tipo: string;
  valorMin: number;
  valorMax: number;
  actualizadoEn: string;
}

export interface TaigaHistoria {
  id: number;
  ref: number;
  subject: string;
  statusNombre: string;
  asignadoA: string | null;
}

// ── Request interfaces ────────────────────────────────────────────────────

export interface CrearZonaRequest {
  nombre: string;
  descripcion: string;
  ubicacion?: string;
  tipo?: TipoZona;
  areaM2?: number;
}

export interface RegistrarLecturaRequest {
  tipo: string;
  valor: number;
  fuente?: FuenteLectura;
  notas?: string;
}

export interface CrearCultivoRequest {
  nombre: string;
  variedad?: string;
  notas?: string;
  plantadoEn?: string;
  fechaCosechaEstimada?: string;
  areaM2?: number;
  cantidadSembrada?: number;
  rendimientoEsperadoKg?: number;
  estado?: EstadoCultivo;
}

export interface ActualizarCultivoRequest {
  nombre: string;
  variedad?: string;
  notas?: string;
  fechaCosechaEstimada?: string;
  areaM2?: number;
  cantidadSembrada?: number;
  rendimientoEsperadoKg?: number;
  estado?: EstadoCultivo;
}

export interface DefinirUmbralRequest {
  tipo: string;
  valorMin: number;
  valorMax: number;
}

// ── Constantes ────────────────────────────────────────────────────────────

export const METRICAS = [
  'TEMPERATURA_C',
  'HUMEDAD_RELATIVA_PCT',
  'LUZ_LUX',
  'HUMEDAD_SUELO_PCT',
] as const;

export const TIPOS_ZONA: TipoZona[] = ['INVERNADERO', 'CAMPO_ABIERTO', 'HIDROPONICO', 'MACETAS'];
export const FUENTES_LECTURA: FuenteLectura[] = ['MANUAL', 'SENSOR_AUTOMATICO'];
export const ESTADOS_CULTIVO: EstadoCultivo[] = ['ACTIVO', 'EN_PREPARACION', 'COSECHADO', 'PERDIDO'];

// ── Endpoints ─────────────────────────────────────────────────────────────

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
    eliminar: (zonaId: string, lecturaId: string) =>
      client.delete(`/api/v1/zonas/${zonaId}/lecturas/${lecturaId}`),
  },
  cultivos: {
    listar: (zonaId: string) =>
      client.get<Cultivo[]>(`/api/v1/zonas/${zonaId}/cultivos`),
    crear: (zonaId: string, body: CrearCultivoRequest) =>
      client.post<Cultivo>(`/api/v1/zonas/${zonaId}/cultivos`, body),
    actualizar: (zonaId: string, cultivoId: string, body: ActualizarCultivoRequest) =>
      client.put<Cultivo>(`/api/v1/zonas/${zonaId}/cultivos/${cultivoId}`, body),
    eliminar: (zonaId: string, cultivoId: string) =>
      client.delete(`/api/v1/zonas/${zonaId}/cultivos/${cultivoId}`),
  },
  umbrales: {
    listar: (zonaId: string) =>
      client.get<Umbral[]>(`/api/v1/zonas/${zonaId}/umbrales`),
    definir: (zonaId: string, body: DefinirUmbralRequest) =>
      client.put<Umbral>(`/api/v1/zonas/${zonaId}/umbrales`, body),
    eliminar: (zonaId: string, umbralId: string) =>
      client.delete(`/api/v1/zonas/${zonaId}/umbrales/${umbralId}`),
  },
  taiga: {
    listar: () => client.get<TaigaHistoria[]>('/api/v1/taiga/historias'),
    buscar: (id: number) =>
      client.get<TaigaHistoria>(`/api/v1/taiga/historias/${id}`),
  },
};

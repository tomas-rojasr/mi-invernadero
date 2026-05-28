/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/DiagramaPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-27
 * Descripción: Diagrama entidad-relación interactivo con React Flow.
 *              Nodos arrastrables, zoom y conexiones 1:N entre entidades.
 */
import { useCallback } from 'react';
import {
  ReactFlow,
  Background,
  Controls,
  MiniMap,
  Handle,
  Position,
  type NodeTypes,
  type Node,
  type Edge,
  BackgroundVariant,
} from '@xyflow/react';
import '@xyflow/react/dist/style.css';
import { useTranslation } from 'react-i18next';

// ── Tipos de campo ─────────────────────────────────────────────────────────

interface Campo {
  nombre: string;
  tipo: string;
  pk?: boolean;
  fk?: boolean;
  nullable?: boolean;
}

// ── Nodo personalizado: tarjeta de entidad ─────────────────────────────────

function EntidadNode({ data }: { data: { label: string; color: string; campos: Campo[] } }) {
  return (
    <div style={{ background: '#fff', borderRadius: 10, overflow: 'hidden', boxShadow: '0 4px 16px rgba(0,0,0,0.15)', minWidth: 260, border: '1.5px solid #e0e0e0' }}>
      <Handle type="target" position={Position.Left} style={{ background: '#555', width: 10, height: 10 }} />
      <Handle type="source" position={Position.Right} style={{ background: '#555', width: 10, height: 10 }} />
      {/* Header */}
      <div style={{ background: data.color, color: '#fff', padding: '10px 16px', fontWeight: 800, fontSize: '0.88rem', letterSpacing: '0.06em', textTransform: 'uppercase' as const }}>
        {data.label}
      </div>
      {/* Campos */}
      {data.campos.map((c, i) => (
        <div key={c.nombre} style={{
          display: 'flex', alignItems: 'center', gap: 6,
          padding: '5px 14px', fontSize: '0.8rem',
          borderBottom: i < data.campos.length - 1 ? '1px solid #f3f3f3' : 'none',
          background: c.pk ? '#fffbf0' : c.fk ? '#fff5f2' : '#fff',
        }}>
          {/* Badge PK / FK / espacio */}
          <span style={{ minWidth: 28, display: 'flex', gap: 2 }}>
            {c.pk && <span style={badge('#e6a817')}>PK</span>}
            {c.fk && <span style={badge('#e06c3a')}>FK</span>}
          </span>
          <span style={{ flex: 1, fontFamily: 'monospace', color: c.nullable ? '#b0b0b0' : '#222' }}>
            {c.nombre}
          </span>
          <span style={{ fontFamily: 'monospace', fontSize: '0.75rem', color: '#2d6a4f', background: '#edf7f1', padding: '1px 6px', borderRadius: 4 }}>
            {c.tipo}
          </span>
          {c.nullable && <span style={{ fontSize: '0.65rem', color: '#ccc', fontStyle: 'italic' }}>null</span>}
        </div>
      ))}
    </div>
  );
}

// ── Nodo enum ──────────────────────────────────────────────────────────────

function EnumNode({ data }: { data: { label: string; color: string; valores: string[] } }) {
  return (
    <div style={{ background: '#fff', borderRadius: 8, overflow: 'hidden', boxShadow: '0 3px 10px rgba(0,0,0,0.10)', minWidth: 170, border: '1.5px dashed #ccc' }}>
      <Handle type="target" position={Position.Left} style={{ background: '#aaa', width: 8, height: 8 }} />
      <div style={{ background: data.color, color: '#fff', padding: '7px 14px', fontSize: '0.75rem', fontWeight: 700 }}>
        «enum» {data.label}
      </div>
      {data.valores.map((v, i) => (
        <div key={v} style={{ padding: '4px 14px', fontSize: '0.76rem', fontFamily: 'monospace', color: '#444', borderBottom: i < data.valores.length - 1 ? '1px solid #f5f5f5' : 'none' }}>
          {v}
        </div>
      ))}
    </div>
  );
}

function badge(bg: string): React.CSSProperties {
  return { background: bg, color: '#fff', fontSize: '0.6rem', fontWeight: 800, padding: '1px 4px', borderRadius: 3, minWidth: 20, textAlign: 'center' as const };
}

// ── Definición de nodos ────────────────────────────────────────────────────

const nodeTypes: NodeTypes = {
  entidad: EntidadNode as any,
  enum: EnumNode as any,
};

const NODOS: Node[] = [
  {
    id: 'zona',
    type: 'entidad',
    position: { x: 30, y: 180 },
    data: {
      label: 'zona',
      color: '#1b4332',
      campos: [
        { nombre: 'id',          tipo: 'UUID',     pk: true },
        { nombre: 'nombre',      tipo: 'String' },
        { nombre: 'descripcion', tipo: 'String',   nullable: true },
        { nombre: 'ubicacion',   tipo: 'String',   nullable: true },
        { nombre: 'tipo',        tipo: 'TipoZona', nullable: true },
        { nombre: 'area_m2',     tipo: 'Decimal',  nullable: true },
        { nombre: 'creado_en',   tipo: 'Timestamp' },
      ],
    },
  },
  {
    id: 'cultivo',
    type: 'entidad',
    position: { x: 420, y: 20 },
    data: {
      label: 'cultivo',
      color: '#2d6a4f',
      campos: [
        { nombre: 'id',                      tipo: 'UUID',          pk: true },
        { nombre: 'zona_id',                 tipo: 'UUID',          fk: true },
        { nombre: 'nombre',                  tipo: 'String' },
        { nombre: 'variedad',                tipo: 'String',        nullable: true },
        { nombre: 'notas',                   tipo: 'String',        nullable: true },
        { nombre: 'plantado_en',             tipo: 'Timestamp' },
        { nombre: 'fecha_cosecha_estimada',  tipo: 'Timestamp',     nullable: true },
        { nombre: 'area_m2',                 tipo: 'Decimal',       nullable: true },
        { nombre: 'cantidad_sembrada',       tipo: 'Integer',       nullable: true },
        { nombre: 'rendimiento_esperado_kg', tipo: 'Decimal',       nullable: true },
        { nombre: 'estado',                  tipo: 'EstadoCultivo', nullable: true },
        { nombre: 'creado_en',               tipo: 'Timestamp' },
      ],
    },
  },
  {
    id: 'lectura',
    type: 'entidad',
    position: { x: 420, y: 420 },
    data: {
      label: 'lectura_ambiental',
      color: '#40916c',
      campos: [
        { nombre: 'id',            tipo: 'UUID',          pk: true },
        { nombre: 'zona_id',       tipo: 'UUID',          fk: true },
        { nombre: 'tipo',          tipo: 'MetricaTipo' },
        { nombre: 'valor',         tipo: 'Decimal' },
        { nombre: 'fuente',        tipo: 'FuenteLectura' },
        { nombre: 'notas',         tipo: 'String',        nullable: true },
        { nombre: 'registrado_en', tipo: 'Timestamp' },
      ],
    },
  },
  {
    id: 'umbral',
    type: 'entidad',
    position: { x: 420, y: 720 },
    data: {
      label: 'umbral_ambiental',
      color: '#52b788',
      campos: [
        { nombre: 'id',             tipo: 'UUID',       pk: true },
        { nombre: 'zona_id',        tipo: 'UUID',       fk: true },
        { nombre: 'tipo',           tipo: 'MetricaTipo' },
        { nombre: 'valor_min',      tipo: 'Decimal' },
        { nombre: 'valor_max',      tipo: 'Decimal' },
        { nombre: 'actualizado_en', tipo: 'Timestamp' },
      ],
    },
  },
  // Enums
  {
    id: 'enum-tipozona',
    type: 'enum',
    position: { x: 820, y: 20 },
    data: { label: 'TipoZona', color: '#6c757d', valores: ['INVERNADERO', 'CAMPO_ABIERTO', 'HIDROPONICO', 'MACETAS'] },
  },
  {
    id: 'enum-estado',
    type: 'enum',
    position: { x: 820, y: 200 },
    data: { label: 'EstadoCultivo', color: '#6c757d', valores: ['ACTIVO', 'EN_PREPARACION', 'COSECHADO', 'PERDIDO'] },
  },
  {
    id: 'enum-metrica',
    type: 'enum',
    position: { x: 820, y: 420 },
    data: { label: 'MetricaTipo', color: '#6c757d', valores: ['TEMPERATURA_C', 'HUMEDAD_RELATIVA_PCT', 'LUZ_LUX', 'HUMEDAD_SUELO_PCT'] },
  },
  {
    id: 'enum-fuente',
    type: 'enum',
    position: { x: 820, y: 640 },
    data: { label: 'FuenteLectura', color: '#6c757d', valores: ['MANUAL', 'SENSOR_AUTOMATICO'] },
  },
];

const ARISTAS: Edge[] = [
  { id: 'z-c', source: 'zona', target: 'cultivo', label: '1 : N', animated: false, style: { stroke: '#2d6a4f', strokeWidth: 2 }, labelStyle: { fill: '#1b4332', fontWeight: 700, fontSize: 11 }, labelBgStyle: { fill: '#d8f3dc' } },
  { id: 'z-l', source: 'zona', target: 'lectura', label: '1 : N', animated: false, style: { stroke: '#40916c', strokeWidth: 2 }, labelStyle: { fill: '#1b4332', fontWeight: 700, fontSize: 11 }, labelBgStyle: { fill: '#d8f3dc' } },
  { id: 'z-u', source: 'zona', target: 'umbral',  label: '1 : N', animated: false, style: { stroke: '#52b788', strokeWidth: 2 }, labelStyle: { fill: '#1b4332', fontWeight: 700, fontSize: 11 }, labelBgStyle: { fill: '#d8f3dc' } },
  { id: 'c-te', source: 'cultivo', target: 'enum-tipozona', style: { stroke: '#ccc', strokeWidth: 1, strokeDasharray: '4 3' }, markerEnd: undefined },
  { id: 'c-es', source: 'cultivo', target: 'enum-estado',   style: { stroke: '#ccc', strokeWidth: 1, strokeDasharray: '4 3' }, markerEnd: undefined },
  { id: 'l-me', source: 'lectura', target: 'enum-metrica',  style: { stroke: '#ccc', strokeWidth: 1, strokeDasharray: '4 3' }, markerEnd: undefined },
  { id: 'l-fu', source: 'lectura', target: 'enum-fuente',   style: { stroke: '#ccc', strokeWidth: 1, strokeDasharray: '4 3' }, markerEnd: undefined },
];

// ── Página ─────────────────────────────────────────────────────────────────

export default function DiagramaPage() {
  const { t } = useTranslation();
  const onInit = useCallback(() => {}, []);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: 'calc(100vh - 120px)' }}>
      <div style={{ marginBottom: '0.8rem' }}>
        <h2 style={{ margin: 0, color: '#1b4332' }}>{t('diagrama.title')}</h2>
        <p style={{ margin: '4px 0 0', color: '#666', fontSize: '0.88rem' }}>{t('diagrama.subtitle')}</p>
      </div>

      <div style={{ flex: 1, borderRadius: 12, overflow: 'hidden', border: '1px solid #e0e0e0', background: '#fafafa' }}>
        <ReactFlow
          nodes={NODOS}
          edges={ARISTAS}
          nodeTypes={nodeTypes}
          onInit={onInit}
          fitView
          fitViewOptions={{ padding: 0.15 }}
          minZoom={0.3}
          maxZoom={2}
          attributionPosition="bottom-left"
        >
          <Background variant={BackgroundVariant.Dots} gap={16} size={1} color="#d1d5db" />
          <Controls showInteractive={false} />
          <MiniMap
            nodeColor={(n) => {
              if (n.type === 'enum') return '#6c757d';
              const colores: Record<string, string> = { zona: '#1b4332', cultivo: '#2d6a4f', lectura: '#40916c', umbral: '#52b788' };
              return colores[n.id] ?? '#ccc';
            }}
            maskColor="rgba(240,244,240,0.7)"
          />
        </ReactFlow>
      </div>
    </div>
  );
}

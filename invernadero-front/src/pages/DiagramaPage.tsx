/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/DiagramaPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-27
 * Descripción: Página de diagrama entidad-relación de la base de datos.
 *              Visualiza las tablas, sus campos y las relaciones entre ellas.
 */
import { useTranslation } from 'react-i18next';

interface Campo {
  nombre: string;
  tipo: string;
  pk?: boolean;
  fk?: boolean;
  nullable?: boolean;
}

const ZONA: Campo[] = [
  { nombre: 'id',          tipo: 'UUID',      pk: true  },
  { nombre: 'nombre',      tipo: 'String'               },
  { nombre: 'descripcion', tipo: 'String',    nullable: true },
  { nombre: 'ubicacion',   tipo: 'String',    nullable: true },
  { nombre: 'tipo',        tipo: 'TipoZona',  nullable: true },
  { nombre: 'area_m2',     tipo: 'Decimal',   nullable: true },
  { nombre: 'creado_en',   tipo: 'Timestamp'             },
];

const CULTIVO: Campo[] = [
  { nombre: 'id',                      tipo: 'UUID',          pk: true  },
  { nombre: 'zona_id',                 tipo: 'UUID',          fk: true  },
  { nombre: 'nombre',                  tipo: 'String'                   },
  { nombre: 'variedad',                tipo: 'String',        nullable: true },
  { nombre: 'notas',                   tipo: 'String',        nullable: true },
  { nombre: 'plantado_en',             tipo: 'Timestamp'                },
  { nombre: 'fecha_cosecha_estimada',  tipo: 'Timestamp',     nullable: true },
  { nombre: 'area_m2',                 tipo: 'Decimal',       nullable: true },
  { nombre: 'cantidad_sembrada',       tipo: 'Integer',       nullable: true },
  { nombre: 'rendimiento_esperado_kg', tipo: 'Decimal',       nullable: true },
  { nombre: 'estado',                  tipo: 'EstadoCultivo', nullable: true },
  { nombre: 'creado_en',               tipo: 'Timestamp'                },
];

const LECTURA: Campo[] = [
  { nombre: 'id',            tipo: 'UUID',          pk: true },
  { nombre: 'zona_id',       tipo: 'UUID',          fk: true },
  { nombre: 'tipo',          tipo: 'MetricaTipo'             },
  { nombre: 'valor',         tipo: 'Decimal'                 },
  { nombre: 'fuente',        tipo: 'FuenteLectura'           },
  { nombre: 'notas',         tipo: 'String',        nullable: true },
  { nombre: 'registrado_en', tipo: 'Timestamp'               },
];

const UMBRAL: Campo[] = [
  { nombre: 'id',             tipo: 'UUID',       pk: true },
  { nombre: 'zona_id',        tipo: 'UUID',       fk: true },
  { nombre: 'tipo',           tipo: 'MetricaTipo'          },
  { nombre: 'valor_min',      tipo: 'Decimal'              },
  { nombre: 'valor_max',      tipo: 'Decimal'              },
  { nombre: 'actualizado_en', tipo: 'Timestamp'            },
];

const ENUMS = [
  { nombre: 'TipoZona',      color: '#2d6a4f', valores: ['INVERNADERO', 'CAMPO_ABIERTO', 'HIDROPONICO', 'MACETAS'] },
  { nombre: 'EstadoCultivo', color: '#40916c', valores: ['ACTIVO', 'EN_PREPARACION', 'COSECHADO', 'PERDIDO'] },
  { nombre: 'MetricaTipo',   color: '#1b4332', valores: ['TEMPERATURA_C', 'HUMEDAD_RELATIVA_PCT', 'LUZ_LUX', 'HUMEDAD_SUELO_PCT'] },
  { nombre: 'FuenteLectura', color: '#52b788', valores: ['MANUAL', 'SENSOR_AUTOMATICO'] },
];

function Tabla({ titulo, campos, color }: { titulo: string; campos: Campo[]; color: string }) {
  return (
    <div style={{ background: '#fff', borderRadius: 10, overflow: 'hidden', boxShadow: '0 3px 12px rgba(0,0,0,0.12)', minWidth: 280 }}>
      <div style={{ background: color, color: '#fff', padding: '9px 16px', fontWeight: 700, fontSize: '0.9rem', letterSpacing: '0.05em', textTransform: 'uppercase' as const }}>
        {titulo}
      </div>
      {campos.map((c, i) => (
        <div key={c.nombre} style={{ display: 'flex', alignItems: 'center', gap: 6, padding: '5px 14px', fontSize: '0.82rem', borderBottom: i < campos.length - 1 ? '1px solid #f0f0f0' : 'none', background: c.pk ? '#fffdf0' : c.fk ? '#fff8f5' : '#fff' }}>
          {c.pk && <span style={badge('#e6a817')}>PK</span>}
          {c.fk && <span style={badge('#e06c3a')}>FK</span>}
          {!c.pk && !c.fk && <span style={{ width: 24 }} />}
          <span style={{ flex: 1, fontFamily: 'monospace', color: c.nullable ? '#aaa' : '#222' }}>{c.nombre}</span>
          <span style={{ fontFamily: 'monospace', fontSize: '0.76rem', color: '#2d6a4f', background: '#f0f9f4', padding: '1px 6px', borderRadius: 4 }}>{c.tipo}</span>
          {c.nullable && <span style={{ fontSize: '0.68rem', color: '#bbb', fontStyle: 'italic' }}>null</span>}
        </div>
      ))}
    </div>
  );
}

function badge(bg: string): React.CSSProperties {
  return { background: bg, color: '#fff', fontSize: '0.62rem', fontWeight: 800, padding: '1px 5px', borderRadius: 3, minWidth: 22, textAlign: 'center' as const };
}

export default function DiagramaPage() {
  const { t } = useTranslation();

  return (
    <div>
      <h2 style={{ marginBottom: '0.3rem', color: '#1b4332' }}>{t('diagrama.title')}</h2>
      <p style={{ color: '#666', marginBottom: '1.5rem', fontSize: '0.88rem' }}>{t('diagrama.subtitle')}</p>

      {/* Leyenda */}
      <div style={{ display: 'flex', gap: 16, marginBottom: '1.5rem', fontSize: '0.8rem', color: '#555', flexWrap: 'wrap' }}>
        <span><span style={badge('#e6a817')}>PK</span> &nbsp;Primary Key</span>
        <span><span style={badge('#e06c3a')}>FK</span> &nbsp;Foreign Key</span>
        <span style={{ fontStyle: 'italic', color: '#aaa' }}>null = nullable</span>
        <span style={{ marginLeft: 'auto', color: '#2d6a4f', fontWeight: 600 }}>Cardinalidad: 1 Zona → N registros</span>
      </div>

      {/* Diagrama principal */}
      <div style={{ display: 'flex', gap: 0, alignItems: 'flex-start', flexWrap: 'wrap' }}>

        {/* Zona (izquierda) */}
        <div style={{ flexShrink: 0 }}>
          <Tabla titulo="zona" campos={ZONA} color="#1b4332" />
        </div>

        {/* Conector SVG central */}
        <svg width="80" height="580" style={{ flexShrink: 0, overflow: 'visible', marginTop: 20 }}>
          {/* línea vertical central */}
          <line x1="40" y1="10" x2="40" y2="560" stroke="#2d6a4f" strokeWidth="2" strokeDasharray="6,3" />
          {/* flechas hacia cada tabla hija */}
          {[72, 270, 480].map((y, i) => (
            <g key={i}>
              <line x1="40" y1={y} x2="68" y2={y} stroke="#2d6a4f" strokeWidth="2" />
              <polygon points={`${68},${y-5} ${80},${y} ${68},${y+5}`} fill="#2d6a4f" />
              <rect x="0" y={y - 12} width="38" height="20" rx="4" fill="#d8f3dc" />
              <text x="19" y={y + 4} textAnchor="middle" fontSize="9" fill="#1b4332" fontWeight="700">1 : N</text>
            </g>
          ))}
        </svg>

        {/* Tablas hijas (derecha) */}
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem', flexShrink: 0 }}>
          <Tabla titulo="cultivo" campos={CULTIVO} color="#40916c" />
          <Tabla titulo="lectura_ambiental" campos={LECTURA} color="#1b4332" />
          <Tabla titulo="umbral_ambiental" campos={UMBRAL} color="#52b788" />
        </div>
      </div>

      {/* Enums */}
      <h3 style={{ marginTop: '2.5rem', marginBottom: '1rem', color: '#2d6a4f', fontSize: '1rem' }}>
        {t('diagrama.enums')}
      </h3>
      <div style={{ display: 'flex', gap: '1rem', flexWrap: 'wrap' }}>
        {ENUMS.map(e => (
          <div key={e.nombre} style={{ background: '#fff', borderRadius: 8, overflow: 'hidden', boxShadow: '0 2px 8px rgba(0,0,0,0.09)', minWidth: 170 }}>
            <div style={{ background: e.color, color: '#fff', padding: '6px 14px', fontSize: '0.78rem', fontWeight: 700 }}>
              «enum» {e.nombre}
            </div>
            {e.valores.map(v => (
              <div key={v} style={{ padding: '4px 14px', fontSize: '0.78rem', fontFamily: 'monospace', color: '#333', borderBottom: '1px solid #f5f5f5' }}>
                {v}
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
}

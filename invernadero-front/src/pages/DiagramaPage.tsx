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

interface Entidad {
  nombre: string;
  color: string;
  campos: Campo[];
}

const ENTIDADES: Entidad[] = [
  {
    nombre: 'zona',
    color: '#2d6a4f',
    campos: [
      { nombre: 'id', tipo: 'UUID', pk: true },
      { nombre: 'nombre', tipo: 'String' },
      { nombre: 'descripcion', tipo: 'String', nullable: true },
      { nombre: 'ubicacion', tipo: 'String', nullable: true },
      { nombre: 'tipo', tipo: 'TipoZona', nullable: true },
      { nombre: 'area_m2', tipo: 'Decimal', nullable: true },
      { nombre: 'creado_en', tipo: 'Timestamp' },
    ],
  },
  {
    nombre: 'cultivo',
    color: '#40916c',
    campos: [
      { nombre: 'id', tipo: 'UUID', pk: true },
      { nombre: 'zona_id', tipo: 'UUID', fk: true },
      { nombre: 'nombre', tipo: 'String' },
      { nombre: 'variedad', tipo: 'String', nullable: true },
      { nombre: 'notas', tipo: 'String', nullable: true },
      { nombre: 'plantado_en', tipo: 'Timestamp' },
      { nombre: 'fecha_cosecha_estimada', tipo: 'Timestamp', nullable: true },
      { nombre: 'area_m2', tipo: 'Decimal', nullable: true },
      { nombre: 'cantidad_sembrada', tipo: 'Integer', nullable: true },
      { nombre: 'rendimiento_esperado_kg', tipo: 'Decimal', nullable: true },
      { nombre: 'estado', tipo: 'EstadoCultivo', nullable: true },
      { nombre: 'creado_en', tipo: 'Timestamp' },
    ],
  },
  {
    nombre: 'lectura_ambiental',
    color: '#1b4332',
    campos: [
      { nombre: 'id', tipo: 'UUID', pk: true },
      { nombre: 'zona_id', tipo: 'UUID', fk: true },
      { nombre: 'tipo', tipo: 'MetricaTipo' },
      { nombre: 'valor', tipo: 'Decimal' },
      { nombre: 'fuente', tipo: 'FuenteLectura' },
      { nombre: 'notas', tipo: 'String', nullable: true },
      { nombre: 'registrado_en', tipo: 'Timestamp' },
    ],
  },
  {
    nombre: 'umbral_ambiental',
    color: '#52b788',
    campos: [
      { nombre: 'id', tipo: 'UUID', pk: true },
      { nombre: 'zona_id', tipo: 'UUID', fk: true },
      { nombre: 'tipo', tipo: 'MetricaTipo' },
      { nombre: 'valor_min', tipo: 'Decimal' },
      { nombre: 'valor_max', tipo: 'Decimal' },
      { nombre: 'actualizado_en', tipo: 'Timestamp' },
    ],
  },
];

const ENUMS = [
  { nombre: 'TipoZona', valores: ['INVERNADERO', 'CAMPO_ABIERTO', 'HIDROPONICO', 'MACETAS'] },
  { nombre: 'EstadoCultivo', valores: ['ACTIVO', 'EN_PREPARACION', 'COSECHADO', 'PERDIDO'] },
  { nombre: 'MetricaTipo', valores: ['TEMPERATURA_C', 'HUMEDAD_RELATIVA_PCT', 'LUZ_LUX', 'HUMEDAD_SUELO_PCT'] },
  { nombre: 'FuenteLectura', valores: ['MANUAL', 'SENSOR_AUTOMATICO'] },
];

function TarjetaEntidad({ entidad }: { entidad: Entidad }) {
  return (
    <div style={styles.card}>
      <div style={{ ...styles.cardHeader, background: entidad.color }}>
        {entidad.nombre}
      </div>
      <div style={styles.cardBody}>
        {entidad.campos.map(c => (
          <div key={c.nombre} style={styles.campo}>
            <span style={styles.campoBadge}>
              {c.pk && <span style={{ ...styles.badge, background: '#f4a261' }}>PK</span>}
              {c.fk && <span style={{ ...styles.badge, background: '#e76f51' }}>FK</span>}
            </span>
            <span style={{ ...styles.campoNombre, opacity: c.nullable ? 0.65 : 1 }}>
              {c.nombre}
            </span>
            <span style={styles.campoTipo}>{c.tipo}</span>
            {c.nullable && <span style={styles.nullable}>nullable</span>}
          </div>
        ))}
      </div>
    </div>
  );
}

function TarjetaEnum({ e }: { e: typeof ENUMS[0] }) {
  return (
    <div style={{ ...styles.card, minWidth: 180 }}>
      <div style={{ ...styles.cardHeader, background: '#6c757d', fontSize: '0.8rem' }}>
        «enum» {e.nombre}
      </div>
      <div style={styles.cardBody}>
        {e.valores.map(v => (
          <div key={v} style={{ ...styles.campo, justifyContent: 'flex-start' }}>
            <span style={{ fontSize: '0.8rem', color: '#333' }}>{v}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default function DiagramaPage() {
  const { t } = useTranslation();

  return (
    <div>
      <h2 style={{ marginBottom: '0.5rem', color: '#1b4332' }}>{t('diagrama.title')}</h2>
      <p style={{ color: '#555', marginBottom: '1.5rem', fontSize: '0.9rem' }}>
        {t('diagrama.subtitle')}
      </p>

      {/* Relación central: zona es la tabla principal */}
      <div style={styles.legend}>
        <span style={{ ...styles.badge, background: '#f4a261' }}>PK</span> Primary Key &nbsp;&nbsp;
        <span style={{ ...styles.badge, background: '#e76f51' }}>FK</span> Foreign Key &nbsp;&nbsp;
        <span style={styles.nullable}>campo</span> = nullable
      </div>

      {/* Diagrama */}
      <div style={styles.diagramWrapper}>
        {/* Zona — tabla central */}
        <div style={styles.zonaCenter}>
          <TarjetaEntidad entidad={ENTIDADES[0]} />
        </div>

        {/* Tablas hijas */}
        <div style={styles.hijas}>
          {ENTIDADES.slice(1).map(e => (
            <div key={e.nombre} style={styles.hijaWrapper}>
              <div style={styles.relLine}>
                <span style={styles.relLabel}>N : 1</span>
              </div>
              <TarjetaEntidad entidad={e} />
            </div>
          ))}
        </div>
      </div>

      {/* Enums */}
      <h3 style={{ marginTop: '2rem', marginBottom: '0.75rem', color: '#2d6a4f' }}>
        {t('diagrama.enums')}
      </h3>
      <div style={styles.enumsWrapper}>
        {ENUMS.map(e => <TarjetaEnum key={e.nombre} e={e} />)}
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  legend: {
    display: 'flex',
    alignItems: 'center',
    gap: 4,
    marginBottom: '1.2rem',
    fontSize: '0.82rem',
    color: '#555',
  },
  diagramWrapper: {
    display: 'flex',
    gap: '2.5rem',
    alignItems: 'flex-start',
    flexWrap: 'wrap',
  },
  zonaCenter: {
    flexShrink: 0,
  },
  hijas: {
    display: 'flex',
    flexDirection: 'column',
    gap: '1.2rem',
  },
  hijaWrapper: {
    display: 'flex',
    alignItems: 'center',
    gap: '0.6rem',
  },
  relLine: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    gap: 2,
  },
  relLabel: {
    fontSize: '0.7rem',
    color: '#888',
    background: '#e9ecef',
    padding: '2px 6px',
    borderRadius: 4,
    whiteSpace: 'nowrap',
  },
  card: {
    background: '#fff',
    borderRadius: 8,
    boxShadow: '0 2px 8px rgba(0,0,0,0.10)',
    minWidth: 260,
    overflow: 'hidden',
  },
  cardHeader: {
    color: '#fff',
    fontWeight: 700,
    fontSize: '0.9rem',
    padding: '8px 14px',
    letterSpacing: '0.03em',
    textTransform: 'uppercase' as const,
  },
  cardBody: {
    padding: '6px 0',
  },
  campo: {
    display: 'flex',
    alignItems: 'center',
    gap: 6,
    padding: '4px 14px',
    fontSize: '0.82rem',
    borderBottom: '1px solid #f0f0f0',
  },
  campoBadge: {
    display: 'flex',
    gap: 3,
    minWidth: 36,
  },
  campoNombre: {
    flex: 1,
    fontFamily: 'monospace',
    color: '#222',
  },
  campoTipo: {
    color: '#2d6a4f',
    fontSize: '0.78rem',
    fontFamily: 'monospace',
    background: '#f0f9f4',
    padding: '1px 5px',
    borderRadius: 3,
  },
  nullable: {
    fontSize: '0.7rem',
    color: '#aaa',
    fontStyle: 'italic',
  },
  badge: {
    fontSize: '0.65rem',
    color: '#fff',
    padding: '1px 4px',
    borderRadius: 3,
    fontWeight: 700,
  },
  enumsWrapper: {
    display: 'flex',
    gap: '1rem',
    flexWrap: 'wrap',
  },
};

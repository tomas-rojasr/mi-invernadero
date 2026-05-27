/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/TaigaPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-27
 * Descripción: Página de integración con Taiga.io. Muestra las historias
 *              de usuario del proyecto cargadas automáticamente.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api } from '../api/client';
import type { TaigaHistoria } from '../api/client';
import { s } from '../styles/shared';

const estadoColor: Record<string, React.CSSProperties> = {
  default: { background: '#e9ecef', color: '#495057' },
  new:     { background: '#cfe2ff', color: '#084298' },
  ready:   { background: '#d1e7dd', color: '#0a3622' },
  done:    { background: '#d8f3dc', color: '#1b4332' },
  closed:  { background: '#f8d7da', color: '#842029' },
};

function badgeStyle(nombre: string): React.CSSProperties {
  const key = nombre.toLowerCase();
  if (key.includes('new') || key.includes('nuevo')) return estadoColor.new;
  if (key.includes('ready') || key.includes('listo')) return estadoColor.ready;
  if (key.includes('done') || key.includes('hecho')) return estadoColor.done;
  if (key.includes('closed') || key.includes('cerrado')) return estadoColor.closed;
  return estadoColor.default;
}

/** Lista de historias de usuario del proyecto Taiga cargadas automáticamente. */
export default function TaigaPage() {
  const { t } = useTranslation();
  const [historias, setHistorias] = useState<TaigaHistoria[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    api.taiga.listar()
      .then(r => setHistorias(r.data))
      .catch(() => setError(t('taiga.error')))
      .finally(() => setLoading(false));
  }, [t]);

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('taiga.title')}</h2>
      </div>

      <div style={s.card}>
        {loading && <p style={{ color: '#888', fontStyle: 'italic' }}>...</p>}
        {error && <p style={s.errorMsg}>{error}</p>}

        {!loading && !error && historias.length === 0 && (
          <p style={{ color: '#888', fontStyle: 'italic' }}>{t('taiga.no_result')}</p>
        )}

        {historias.length > 0 && (
          <table style={s.table}>
            <thead>
              <tr>
                <th style={{ ...s.th, width: 60 }}>{t('taiga.ref')}</th>
                <th style={s.th}>{t('taiga.subject')}</th>
                <th style={{ ...s.th, width: 140 }}>{t('taiga.status')}</th>
              </tr>
            </thead>
            <tbody>
              {historias.map(h => (
                <tr key={h.id} style={s.tr}>
                  <td style={{ ...s.td, color: '#888' }}>#{h.ref}</td>
                  <td style={s.td}>{h.subject}</td>
                  <td style={s.td}>
                    <span style={{ ...styles.badge, ...badgeStyle(h.statusNombre) }}>
                      {h.statusNombre}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  badge: { borderRadius: 12, padding: '2px 10px', fontSize: '0.82rem', fontWeight: 600 },
};

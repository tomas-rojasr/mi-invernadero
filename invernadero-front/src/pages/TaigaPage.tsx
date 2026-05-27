/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/TaigaPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de integración con Taiga.io. Permite consultar
 *              historias de usuario del proyecto por su ID.
 */
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api } from '../api/client';
import type { TaigaHistoria } from '../api/client';
import { s } from '../styles/shared';

/** Consulta de historias de usuario desde Taiga.io por ID. */
export default function TaigaPage() {
  const { t } = useTranslation();
  const [idInput, setIdInput] = useState('');
  const [historia, setHistoria] = useState<TaigaHistoria | null>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const buscar = () => {
    const id = parseInt(idInput);
    if (!id) return;
    setLoading(true);
    setError('');
    setHistoria(null);
    api.taiga.buscar(id)
      .then(r => setHistoria(r.data))
      .catch(() => setError(t('taiga.error')))
      .finally(() => setLoading(false));
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('taiga.title')}</h2>
      </div>

      <div style={s.card}>
        <div style={{ display: 'flex', gap: 8, alignItems: 'center', marginBottom: '1.5rem' }}>
          <input
            style={{ ...s.input, maxWidth: 200 }}
            type="number"
            placeholder={t('taiga.id_label')}
            value={idInput}
            onChange={e => setIdInput(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && buscar()}
          />
          <button style={s.btnPrimary} onClick={buscar} disabled={loading}>
            {loading ? '...' : t('taiga.buscar')}
          </button>
        </div>

        {error && <p style={s.errorMsg}>{error}</p>}

        {!historia && !error && (
          <p style={{ color: '#888', fontStyle: 'italic' }}>{t('taiga.no_result')}</p>
        )}

        {historia && (
          <table style={s.table}>
            <thead>
              <tr>
                <th style={s.th}>{t('taiga.ref')}</th>
                <th style={s.th}>{t('taiga.subject')}</th>
                <th style={s.th}>{t('taiga.status')}</th>
              </tr>
            </thead>
            <tbody>
              <tr style={s.tr}>
                <td style={s.td}>#{historia.ref}</td>
                <td style={s.td}>{historia.subject}</td>
                <td style={s.td}>
                  <span style={styles.badge}>{historia.status}</span>
                </td>
              </tr>
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  badge: {
    background: '#d8f3dc',
    color: '#1b4332',
    borderRadius: 12,
    padding: '2px 10px',
    fontSize: '0.82rem',
    fontWeight: 600,
  },
};

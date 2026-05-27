/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/UmbralesPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de umbrales ambientales. Permite definir rangos
 *              mínimo/máximo por métrica para alertas del invernadero.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, METRICAS } from '../api/client';
import type { Zona, Umbral } from '../api/client';
import { s } from '../styles/shared';

/** Definición y consulta de umbrales ambientales por zona. */
export default function UmbralesPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaId, setZonaId] = useState('');
  const [umbrales, setUmbrales] = useState<Umbral[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [tipo, setTipo] = useState(METRICAS[0]);
  const [minimo, setMinimo] = useState('');
  const [maximo, setMaximo] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { api.zonas.listar().then(r => setZonas(r.data)); }, []);

  useEffect(() => {
    if (zonaId) api.umbrales.listar(zonaId).then(r => setUmbrales(r.data));
    else setUmbrales([]);
  }, [zonaId]);

  const definir = () => {
    if (!zonaId || !minimo || !maximo) { setError('Selecciona una zona, mínimo y máximo'); return; }
    setError('');
    api.umbrales.definir(zonaId, { tipo, minimo: parseFloat(minimo), maximo: parseFloat(maximo) })
      .then(() => { setMinimo(''); setMaximo(''); setMostrarForm(false); api.umbrales.listar(zonaId).then(r => setUmbrales(r.data)); })
      .catch(() => setError(t('error.generic')));
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('umbral.title')}</h2>
        <button style={s.btnPrimary} onClick={() => setMostrarForm(!mostrarForm)}>
          + {t('umbral.definir')}
        </button>
      </div>

      {error && <p style={s.errorMsg}>{error}</p>}

      {mostrarForm && (
        <div style={s.card}>
          <h3 style={s.cardTitle}>{t('umbral.definir')}</h3>
          <div style={s.formGrid}>
            <select style={s.input} value={zonaId} onChange={e => setZonaId(e.target.value)}>
              <option value="">{t('umbral.selecciona_zona')}</option>
              {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
            </select>
            <select style={s.input} value={tipo} onChange={e => setTipo(e.target.value as typeof tipo)}>
              {METRICAS.map(m => <option key={m} value={m}>{t(`metricas.${m}`)}</option>)}
            </select>
            <input style={s.input} type="number" placeholder={t('umbral.minimo')} value={minimo} onChange={e => setMinimo(e.target.value)} />
            <input style={s.input} type="number" placeholder={t('umbral.maximo')} value={maximo} onChange={e => setMaximo(e.target.value)} />
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={definir}>{t('umbral.guardar')}</button>
            <button style={s.btnSecondary} onClick={() => setMostrarForm(false)}>{t('umbral.cancelar')}</button>
          </div>
        </div>
      )}

      <div style={s.card}>
        <div style={{ marginBottom: '1rem' }}>
          <select style={{ ...s.input, maxWidth: 280 }} value={zonaId} onChange={e => setZonaId(e.target.value)}>
            <option value="">{t('umbral.selecciona_zona')}</option>
            {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
          </select>
        </div>
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>{t('umbral.tipo')}</th>
              <th style={s.th}>{t('umbral.minimo')}</th>
              <th style={s.th}>{t('umbral.maximo')}</th>
              <th style={s.th}>{t('umbral.actualizado')}</th>
            </tr>
          </thead>
          <tbody>
            {!zonaId ? (
              <tr><td colSpan={4} style={s.empty}>{t('umbral.selecciona_zona')}</td></tr>
            ) : umbrales.length === 0 ? (
              <tr><td colSpan={4} style={s.empty}>{t('umbral.no_umbrales')}</td></tr>
            ) : umbrales.map(u => (
              <tr key={u.id} style={s.tr}>
                <td style={s.td}>{t(`metricas.${u.tipo}`)}</td>
                <td style={s.td}>{u.valorMin}</td>
                <td style={s.td}>{u.valorMax}</td>
                <td style={s.td}>{new Date(u.actualizadoEn).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/LecturasPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de lecturas ambientales. Permite seleccionar una zona,
 *              ver las lecturas existentes y registrar nuevas.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, METRICAS } from '../api/client';
import type { Zona, Lectura } from '../api/client';
import { s } from '../styles/shared';

/** Registro y visualización de lecturas ambientales por zona. */
export default function LecturasPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaId, setZonaId] = useState('');
  const [lecturas, setLecturas] = useState<Lectura[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [tipo, setTipo] = useState(METRICAS[0]);
  const [valor, setValor] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { api.zonas.listar().then(r => setZonas(r.data)); }, []);

  useEffect(() => {
    if (zonaId) api.lecturas.listar(zonaId).then(r => setLecturas(r.data));
    else setLecturas([]);
  }, [zonaId]);

  const registrar = () => {
    if (!zonaId || !valor) { setError('Selecciona una zona e ingresa un valor'); return; }
    setError('');
    api.lecturas.registrar(zonaId, { tipo, valor: parseFloat(valor) })
      .then(() => { setValor(''); setMostrarForm(false); api.lecturas.listar(zonaId).then(r => setLecturas(r.data)); })
      .catch(() => setError(t('error.generic')));
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('lectura.title')}</h2>
        <button style={s.btnPrimary} onClick={() => setMostrarForm(!mostrarForm)}>
          + {t('lectura.registrar')}
        </button>
      </div>

      {error && <p style={s.errorMsg}>{error}</p>}

      {mostrarForm && (
        <div style={s.card}>
          <h3 style={s.cardTitle}>{t('lectura.registrar')}</h3>
          <div style={s.formGrid}>
            <select style={s.input} value={zonaId} onChange={e => setZonaId(e.target.value)}>
              <option value="">{t('lectura.selecciona_zona')}</option>
              {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
            </select>
            <select style={s.input} value={tipo} onChange={e => setTipo(e.target.value as typeof tipo)}>
              {METRICAS.map(m => <option key={m} value={m}>{t(`metricas.${m}`)}</option>)}
            </select>
            <input style={s.input} type="number" placeholder={t('lectura.valor')} value={valor} onChange={e => setValor(e.target.value)} />
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={registrar}>{t('lectura.guardar')}</button>
            <button style={s.btnSecondary} onClick={() => setMostrarForm(false)}>{t('zona.cancelar')}</button>
          </div>
        </div>
      )}

      <div style={s.card}>
        <div style={{ marginBottom: '1rem' }}>
          <select style={{ ...s.input, maxWidth: 280 }} value={zonaId} onChange={e => setZonaId(e.target.value)}>
            <option value="">{t('lectura.selecciona_zona')}</option>
            {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
          </select>
        </div>
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>{t('lectura.tipo')}</th>
              <th style={s.th}>{t('lectura.valor')}</th>
              <th style={s.th}>{t('lectura.registrada')}</th>
            </tr>
          </thead>
          <tbody>
            {!zonaId ? (
              <tr><td colSpan={3} style={s.empty}>{t('lectura.selecciona_zona')}</td></tr>
            ) : lecturas.length === 0 ? (
              <tr><td colSpan={3} style={s.empty}>{t('lectura.no_lecturas')}</td></tr>
            ) : lecturas.map(l => (
              <tr key={l.id} style={s.tr}>
                <td style={s.td}>{t(`metricas.${l.tipo}`)}</td>
                <td style={s.td}><strong>{l.valor}</strong></td>
                <td style={s.td}>{new Date(l.registradoEn).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/ZonasPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de gestión de zonas del invernadero.
 *              Permite crear, listar y eliminar zonas.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, TIPOS_ZONA } from '../api/client';
import type { Zona, TipoZona } from '../api/client';
import { s } from '../styles/shared';

export default function ZonasPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [ubicacion, setUbicacion] = useState('');
  const [tipo, setTipo] = useState<TipoZona | ''>('');
  const [areaM2, setAreaM2] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { cargar(); }, []);

  const cargar = () =>
    api.zonas.listar().then(r => setZonas(r.data)).catch(() => setError(t('error.network')));

  const resetForm = () => {
    setNombre(''); setDescripcion(''); setUbicacion(''); setTipo(''); setAreaM2('');
  };

  const crear = () => {
    if (!nombre.trim()) return;
    api.zonas.crear({
      nombre,
      descripcion,
      ubicacion: ubicacion || undefined,
      tipo: tipo || undefined,
      areaM2: areaM2 ? parseFloat(areaM2) : undefined,
    })
      .then(() => { resetForm(); setMostrarForm(false); cargar(); })
      .catch(() => setError(t('error.generic')));
  };

  const eliminar = (id: string) => {
    if (!confirm(t('zona.confirmar_eliminar'))) return;
    api.zonas.eliminar(id).then(cargar);
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('zona.title')}</h2>
        <button style={s.btnPrimary} onClick={() => setMostrarForm(!mostrarForm)}>
          + {t('zona.nueva')}
        </button>
      </div>

      {error && <p style={s.errorMsg}>{error}</p>}

      {mostrarForm && (
        <div style={s.card}>
          <h3 style={s.cardTitle}>{t('zona.nueva')}</h3>
          <div style={s.formGrid}>
            <input style={s.input} placeholder={t('zona.nombre')} value={nombre} onChange={e => setNombre(e.target.value)} />
            <input style={s.input} placeholder={t('zona.descripcion')} value={descripcion} onChange={e => setDescripcion(e.target.value)} />
            <input style={s.input} placeholder={t('zona.ubicacion')} value={ubicacion} onChange={e => setUbicacion(e.target.value)} />
            <select style={s.input} value={tipo} onChange={e => setTipo(e.target.value as TipoZona | '')}>
              <option value="">{t('zona.tipo_placeholder')}</option>
              {TIPOS_ZONA.map(tz => (
                <option key={tz} value={tz}>{t(`tipoZona.${tz}`)}</option>
              ))}
            </select>
            <input style={s.input} type="number" min="0" step="0.01" placeholder={t('zona.area_m2')} value={areaM2} onChange={e => setAreaM2(e.target.value)} />
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={crear}>{t('zona.guardar')}</button>
            <button style={s.btnSecondary} onClick={() => { setMostrarForm(false); resetForm(); }}>{t('zona.cancelar')}</button>
          </div>
        </div>
      )}

      <div style={s.card}>
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>{t('zona.nombre')}</th>
              <th style={s.th}>{t('zona.descripcion')}</th>
              <th style={s.th}>{t('zona.ubicacion')}</th>
              <th style={s.th}>{t('zona.tipo')}</th>
              <th style={s.th}>{t('zona.area_m2')}</th>
              <th style={s.th}>{t('zona.creada')}</th>
              <th style={s.th}></th>
            </tr>
          </thead>
          <tbody>
            {zonas.length === 0 ? (
              <tr><td colSpan={7} style={s.empty}>{t('zona.no_zonas')}</td></tr>
            ) : zonas.map(z => (
              <tr key={z.id} style={s.tr}>
                <td style={s.td}><strong>{z.nombre}</strong></td>
                <td style={s.td}>{z.descripcion}</td>
                <td style={s.td}>{z.ubicacion ?? '—'}</td>
                <td style={s.td}>{z.tipo ? t(`tipoZona.${z.tipo}`) : '—'}</td>
                <td style={s.td}>{z.areaM2 != null ? `${z.areaM2} m²` : '—'}</td>
                <td style={s.td}>{new Date(z.creadoEn).toLocaleDateString()}</td>
                <td style={s.td}>
                  <button style={s.btnDanger} onClick={() => eliminar(z.id)}>{t('zona.eliminar')}</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

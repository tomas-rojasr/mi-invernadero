/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/CultivosPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de gestión de cultivos por zona. Permite registrar
 *              nuevos cultivos y consultar los existentes.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api } from '../api/client';
import type { Zona, Cultivo } from '../api/client';
import { s } from '../styles/shared';

/** Registro y listado de cultivos asociados a una zona. */
export default function CultivosPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaId, setZonaId] = useState('');
  const [cultivos, setCultivos] = useState<Cultivo[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');

  useEffect(() => { api.zonas.listar().then(r => setZonas(r.data)); }, []);

  useEffect(() => {
    if (zonaId) api.cultivos.listar(zonaId).then(r => setCultivos(r.data));
    else setCultivos([]);
  }, [zonaId]);

  const crear = () => {
    if (!zonaId || !nombre.trim()) return;
    api.cultivos.crear(zonaId, { nombre, descripcion })
      .then(() => { setNombre(''); setDescripcion(''); api.cultivos.listar(zonaId).then(r => setCultivos(r.data)); })
      .catch(() => {});
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('cultivo.title')}</h2>
        <button style={s.btnPrimary} onClick={() => setMostrarForm(!mostrarForm)}>
          + {t('cultivo.nuevo')}
        </button>
      </div>

      {mostrarForm && (
        <div style={s.card}>
          <h3 style={s.cardTitle}>{t('cultivo.nuevo')}</h3>
          <div style={s.formGrid}>
            <select style={s.input} value={zonaId} onChange={e => setZonaId(e.target.value)}>
              <option value="">{t('cultivo.selecciona_zona')}</option>
              {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
            </select>
            <input style={s.input} placeholder={t('cultivo.nombre')} value={nombre} onChange={e => setNombre(e.target.value)} />
            <input style={s.input} placeholder={t('cultivo.descripcion')} value={descripcion} onChange={e => setDescripcion(e.target.value)} />
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={crear}>{t('cultivo.guardar')}</button>
            <button style={s.btnSecondary} onClick={() => setMostrarForm(false)}>{t('cultivo.cancelar')}</button>
          </div>
        </div>
      )}

      <div style={s.card}>
        <div style={{ marginBottom: '1rem' }}>
          <select style={{ ...s.input, maxWidth: 280 }} value={zonaId} onChange={e => setZonaId(e.target.value)}>
            <option value="">{t('cultivo.selecciona_zona')}</option>
            {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
          </select>
        </div>
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>{t('cultivo.nombre')}</th>
              <th style={s.th}>{t('cultivo.descripcion')}</th>
              <th style={s.th}>{t('cultivo.plantado')}</th>
              <th style={s.th}>{t('cultivo.creado')}</th>
            </tr>
          </thead>
          <tbody>
            {!zonaId ? (
              <tr><td colSpan={4} style={s.empty}>{t('cultivo.selecciona_zona')}</td></tr>
            ) : cultivos.length === 0 ? (
              <tr><td colSpan={4} style={s.empty}>{t('cultivo.no_cultivos')}</td></tr>
            ) : cultivos.map(c => (
              <tr key={c.id} style={s.tr}>
                <td style={s.td}><strong>{c.nombre}</strong></td>
                <td style={s.td}>{c.descripcion}</td>
                <td style={s.td}>{new Date(c.plantadoEn).toLocaleDateString()}</td>
                <td style={s.td}>{new Date(c.creadoEn).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

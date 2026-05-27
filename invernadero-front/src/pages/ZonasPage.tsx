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
import { api } from '../api/client';
import type { Zona } from '../api/client';
import { s } from '../styles/shared';

/** Gestión completa de zonas: listar, crear y eliminar. */
export default function ZonasPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { cargar(); }, []);

  const cargar = () =>
    api.zonas.listar().then(r => setZonas(r.data)).catch(() => setError(t('error.network')));

  const crear = () => {
    if (!nombre.trim()) return;
    api.zonas.crear({ nombre, descripcion })
      .then(() => { setNombre(''); setDescripcion(''); setMostrarForm(false); cargar(); })
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
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={crear}>{t('zona.guardar')}</button>
            <button style={s.btnSecondary} onClick={() => setMostrarForm(false)}>{t('zona.cancelar')}</button>
          </div>
        </div>
      )}

      <div style={s.card}>
        <table style={s.table}>
          <thead>
            <tr>
              <th style={s.th}>{t('zona.nombre')}</th>
              <th style={s.th}>{t('zona.descripcion')}</th>
              <th style={s.th}>{t('zona.creada')}</th>
              <th style={s.th}></th>
            </tr>
          </thead>
          <tbody>
            {zonas.length === 0 ? (
              <tr><td colSpan={4} style={s.empty}>{t('zona.no_zonas')}</td></tr>
            ) : zonas.map(z => (
              <tr key={z.id} style={s.tr}>
                <td style={s.td}><strong>{z.nombre}</strong></td>
                <td style={s.td}>{z.descripcion}</td>
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

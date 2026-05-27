/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/CultivosPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de gestión de cultivos por zona. Permite registrar,
 *              editar y eliminar cultivos, y consultar los existentes.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api } from '../api/client';
import type { Zona, Cultivo } from '../api/client';
import { s } from '../styles/shared';

/** Registro, edición y listado de cultivos asociados a una zona. */
export default function CultivosPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaId, setZonaId] = useState('');
  const [cultivos, setCultivos] = useState<Cultivo[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [editandoId, setEditandoId] = useState<string | null>(null);
  const [nombre, setNombre] = useState('');
  const [variedad, setVariedad] = useState('');
  const [notas, setNotas] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { api.zonas.listar().then(r => setZonas(r.data)); }, []);

  useEffect(() => {
    if (zonaId) api.cultivos.listar(zonaId).then(r => setCultivos(r.data));
    else setCultivos([]);
  }, [zonaId]);

  const recargar = (zId: string) => api.cultivos.listar(zId).then(r => setCultivos(r.data));

  const abrirNuevo = () => {
    setEditandoId(null);
    setNombre(''); setVariedad(''); setNotas('');
    setMostrarForm(true);
  };

  const abrirEditar = (c: Cultivo) => {
    setEditandoId(c.id);
    setNombre(c.nombre);
    setVariedad(c.variedad ?? '');
    setNotas(c.notas ?? '');
    setMostrarForm(true);
  };

  const cancelar = () => {
    setMostrarForm(false);
    setEditandoId(null);
    setNombre(''); setVariedad(''); setNotas('');
  };

  const guardar = () => {
    if (!zonaId || !nombre.trim()) { setError('Selecciona una zona e ingresa el nombre'); return; }
    setError('');
    const body = { nombre, variedad: variedad || undefined, notas: notas || undefined };
    const req = editandoId
      ? api.cultivos.actualizar(zonaId, editandoId, body)
      : api.cultivos.crear(zonaId, body);
    req
      .then(() => { cancelar(); recargar(zonaId); })
      .catch(() => setError(t('error.generic')));
  };

  const eliminar = (cultivoId: string) => {
    api.cultivos.eliminar(zonaId, cultivoId)
      .then(() => recargar(zonaId))
      .catch(() => setError(t('error.generic')));
  };

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('cultivo.title')}</h2>
        <button style={s.btnPrimary} onClick={abrirNuevo}>
          + {t('cultivo.nuevo')}
        </button>
      </div>

      {error && <p style={s.errorMsg}>{error}</p>}

      {mostrarForm && (
        <div style={s.card}>
          <h3 style={s.cardTitle}>{editandoId ? t('cultivo.editar') : t('cultivo.nuevo')}</h3>
          <div style={s.formGrid}>
            {!editandoId && (
              <select style={s.input} value={zonaId} onChange={e => setZonaId(e.target.value)}>
                <option value="">{t('cultivo.selecciona_zona')}</option>
                {zonas.map(z => <option key={z.id} value={z.id}>{z.nombre}</option>)}
              </select>
            )}
            <input style={s.input} placeholder={t('cultivo.nombre')} value={nombre} onChange={e => setNombre(e.target.value)} />
            <input style={s.input} placeholder="Variedad (opcional)" value={variedad} onChange={e => setVariedad(e.target.value)} />
            <input style={s.input} placeholder="Notas (opcional)" value={notas} onChange={e => setNotas(e.target.value)} />
          </div>
          <div style={s.btnRow}>
            <button style={s.btnPrimary} onClick={guardar}>{t('cultivo.guardar')}</button>
            <button style={s.btnSecondary} onClick={cancelar}>{t('cultivo.cancelar')}</button>
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
              <th style={s.th}>Variedad</th>
              <th style={s.th}>{t('cultivo.plantado')}</th>
              <th style={s.th}>{t('cultivo.creado')}</th>
              <th style={s.th}></th>
            </tr>
          </thead>
          <tbody>
            {!zonaId ? (
              <tr><td colSpan={5} style={s.empty}>{t('cultivo.selecciona_zona')}</td></tr>
            ) : cultivos.length === 0 ? (
              <tr><td colSpan={5} style={s.empty}>{t('cultivo.no_cultivos')}</td></tr>
            ) : cultivos.map(c => (
              <tr key={c.id} style={s.tr}>
                <td style={s.td}><strong>{c.nombre}</strong></td>
                <td style={s.td}>{c.variedad ?? '—'}</td>
                <td style={s.td}>{new Date(c.plantadoEn).toLocaleDateString()}</td>
                <td style={s.td}>{new Date(c.creadoEn).toLocaleDateString()}</td>
                <td style={s.td}>
                  <div style={{ display: 'flex', gap: 6 }}>
                    <button style={s.btnSecondary} onClick={() => abrirEditar(c)}>{t('cultivo.editar')}</button>
                    <button style={s.btnDanger} onClick={() => eliminar(c.id)}>{t('cultivo.eliminar')}</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

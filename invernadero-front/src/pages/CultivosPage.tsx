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
import { api, ESTADOS_CULTIVO } from '../api/client';
import type { Zona, Cultivo, EstadoCultivo } from '../api/client';
import { s } from '../styles/shared';

export default function CultivosPage() {
  const { t } = useTranslation();
  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaId, setZonaId] = useState('');
  const [cultivos, setCultivos] = useState<Cultivo[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [editandoId, setEditandoId] = useState<string | null>(null);

  // Campos del formulario
  const [nombre, setNombre] = useState('');
  const [variedad, setVariedad] = useState('');
  const [notas, setNotas] = useState('');
  const [plantadoEn, setPlantadoEn] = useState('');
  const [fechaCosecha, setFechaCosecha] = useState('');
  const [areaM2, setAreaM2] = useState('');
  const [cantidad, setCantidad] = useState('');
  const [rendimiento, setRendimiento] = useState('');
  const [estado, setEstado] = useState<EstadoCultivo | ''>('');

  const [error, setError] = useState('');

  useEffect(() => { api.zonas.listar().then(r => setZonas(r.data)); }, []);

  useEffect(() => {
    if (zonaId) api.cultivos.listar(zonaId).then(r => setCultivos(r.data));
    else setCultivos([]);
  }, [zonaId]);

  const recargar = (zId: string) => api.cultivos.listar(zId).then(r => setCultivos(r.data));

  const resetForm = () => {
    setNombre(''); setVariedad(''); setNotas(''); setPlantadoEn('');
    setFechaCosecha(''); setAreaM2(''); setCantidad(''); setRendimiento(''); setEstado('');
  };

  const abrirNuevo = () => {
    setEditandoId(null);
    resetForm();
    setMostrarForm(true);
  };

  const abrirEditar = (c: Cultivo) => {
    setEditandoId(c.id);
    setNombre(c.nombre);
    setVariedad(c.variedad ?? '');
    setNotas(c.notas ?? '');
    setPlantadoEn('');
    setFechaCosecha(c.fechaCosechaEstimada ? c.fechaCosechaEstimada.substring(0, 10) : '');
    setAreaM2(c.areaM2 != null ? String(c.areaM2) : '');
    setCantidad(c.cantidadSembrada != null ? String(c.cantidadSembrada) : '');
    setRendimiento(c.rendimientoEsperadoKg != null ? String(c.rendimientoEsperadoKg) : '');
    setEstado(c.estado ?? '');
    setMostrarForm(true);
  };

  const cancelar = () => { setMostrarForm(false); setEditandoId(null); resetForm(); };

  const guardar = () => {
    if (!zonaId || !nombre.trim()) { setError(t('cultivo.error_campos')); return; }
    setError('');

    if (editandoId) {
      api.cultivos.actualizar(zonaId, editandoId, {
        nombre,
        variedad: variedad || undefined,
        notas: notas || undefined,
        fechaCosechaEstimada: fechaCosecha || undefined,
        areaM2: areaM2 ? parseFloat(areaM2) : undefined,
        cantidadSembrada: cantidad ? parseInt(cantidad) : undefined,
        rendimientoEsperadoKg: rendimiento ? parseFloat(rendimiento) : undefined,
        estado: estado || undefined,
      })
        .then(() => { cancelar(); recargar(zonaId); })
        .catch(() => setError(t('error.generic')));
    } else {
      api.cultivos.crear(zonaId, {
        nombre,
        variedad: variedad || undefined,
        notas: notas || undefined,
        plantadoEn: plantadoEn || undefined,
        fechaCosechaEstimada: fechaCosecha || undefined,
        areaM2: areaM2 ? parseFloat(areaM2) : undefined,
        cantidadSembrada: cantidad ? parseInt(cantidad) : undefined,
        rendimientoEsperadoKg: rendimiento ? parseFloat(rendimiento) : undefined,
        estado: estado || undefined,
      })
        .then(() => { cancelar(); recargar(zonaId); })
        .catch(() => setError(t('error.generic')));
    }
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
            <input style={s.input} placeholder={t('cultivo.variedad')} value={variedad} onChange={e => setVariedad(e.target.value)} />
            <input style={s.input} placeholder={t('cultivo.notas')} value={notas} onChange={e => setNotas(e.target.value)} />
            {!editandoId && (
              <div style={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                <label style={{ fontSize: 12, color: '#555' }}>{t('cultivo.plantado')}</label>
                <input style={s.input} type="date" value={plantadoEn} onChange={e => setPlantadoEn(e.target.value)} />
              </div>
            )}
            <div style={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
              <label style={{ fontSize: 12, color: '#555' }}>{t('cultivo.fecha_cosecha')}</label>
              <input style={s.input} type="date" value={fechaCosecha} onChange={e => setFechaCosecha(e.target.value)} />
            </div>
            <input style={s.input} type="number" min="0" step="0.01" placeholder={t('cultivo.area_m2')} value={areaM2} onChange={e => setAreaM2(e.target.value)} />
            <input style={s.input} type="number" min="0" placeholder={t('cultivo.cantidad_sembrada')} value={cantidad} onChange={e => setCantidad(e.target.value)} />
            <input style={s.input} type="number" min="0" step="0.01" placeholder={t('cultivo.rendimiento_kg')} value={rendimiento} onChange={e => setRendimiento(e.target.value)} />
            <select style={s.input} value={estado} onChange={e => setEstado(e.target.value as EstadoCultivo | '')}>
              <option value="">{t('cultivo.estado_placeholder')}</option>
              {ESTADOS_CULTIVO.map(e => <option key={e} value={e}>{t(`estadoCultivo.${e}`)}</option>)}
            </select>
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
              <th style={s.th}>{t('cultivo.variedad')}</th>
              <th style={s.th}>{t('cultivo.estado')}</th>
              <th style={s.th}>{t('cultivo.area_m2')}</th>
              <th style={s.th}>{t('cultivo.cantidad_sembrada')}</th>
              <th style={s.th}>{t('cultivo.rendimiento_kg')}</th>
              <th style={s.th}>{t('cultivo.plantado')}</th>
              <th style={s.th}>{t('cultivo.fecha_cosecha')}</th>
              <th style={s.th}></th>
            </tr>
          </thead>
          <tbody>
            {!zonaId ? (
              <tr><td colSpan={9} style={s.empty}>{t('cultivo.selecciona_zona')}</td></tr>
            ) : cultivos.length === 0 ? (
              <tr><td colSpan={9} style={s.empty}>{t('cultivo.no_cultivos')}</td></tr>
            ) : cultivos.map(c => (
              <tr key={c.id} style={s.tr}>
                <td style={s.td}><strong>{c.nombre}</strong></td>
                <td style={s.td}>{c.variedad ?? '—'}</td>
                <td style={s.td}>{c.estado ? t(`estadoCultivo.${c.estado}`) : '—'}</td>
                <td style={s.td}>{c.areaM2 != null ? `${c.areaM2} m²` : '—'}</td>
                <td style={s.td}>{c.cantidadSembrada ?? '—'}</td>
                <td style={s.td}>{c.rendimientoEsperadoKg != null ? `${c.rendimientoEsperadoKg} kg` : '—'}</td>
                <td style={s.td}>{new Date(c.plantadoEn).toLocaleDateString()}</td>
                <td style={s.td}>{c.fechaCosechaEstimada ? new Date(c.fechaCosechaEstimada).toLocaleDateString() : '—'}</td>
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

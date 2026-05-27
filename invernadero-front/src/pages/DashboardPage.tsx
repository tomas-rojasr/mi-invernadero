/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/DashboardPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Panel principal. Muestra la lista de zonas, permite crear
 *              y eliminar zonas, y ver las últimas lecturas de cada una.
 */
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { api, Zona, Lectura } from '../api/client';
import LanguageToggle from '../components/LanguageToggle';

/**
 * Dashboard principal del invernadero.
 * Gestiona el estado de zonas y lecturas consumiendo la API REST del backend.
 */
export default function DashboardPage() {
  const { t } = useTranslation();

  const [zonas, setZonas] = useState<Zona[]>([]);
  const [zonaSeleccionada, setZonaSeleccionada] = useState<Zona | null>(null);
  const [lecturas, setLecturas] = useState<Lectura[]>([]);
  const [mostrarForm, setMostrarForm] = useState(false);
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { cargarZonas(); }, []);

  const cargarZonas = () => {
    api.zonas.listar().then((r) => setZonas(r.data)).catch(() => setError(t('error.network')));
  };

  const cargarLecturas = (zona: Zona) => {
    setZonaSeleccionada(zona);
    api.lecturas.listar(zona.id).then((r) => setLecturas(r.data)).catch(() => setLecturas([]));
  };

  const crearZona = () => {
    if (!nombre.trim()) return;
    api.zonas
      .crear({ nombre, descripcion })
      .then(() => { setNombre(''); setDescripcion(''); setMostrarForm(false); cargarZonas(); })
      .catch(() => setError(t('error.generic')));
  };

  const eliminarZona = (id: string) => {
    api.zonas.eliminar(id).then(() => {
      if (zonaSeleccionada?.id === id) { setZonaSeleccionada(null); setLecturas([]); }
      cargarZonas();
    });
  };

  return (
    <div style={styles.page}>
      <header style={styles.header}>
        <h1 style={styles.headerTitle}>{t('app.title')}</h1>
        <LanguageToggle />
      </header>

      <main style={styles.main}>
        {error && <p style={styles.error}>{error}</p>}

        <section style={styles.section}>
          <div style={styles.sectionHeader}>
            <h2>{t('dashboard.zonas_title')}</h2>
            <button style={styles.btnPrimary} onClick={() => setMostrarForm(!mostrarForm)}>
              {t('dashboard.add_zona')}
            </button>
          </div>

          {mostrarForm && (
            <div style={styles.form}>
              <input
                style={styles.input}
                placeholder={t('zona.nombre')}
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
              />
              <input
                style={styles.input}
                placeholder={t('zona.descripcion')}
                value={descripcion}
                onChange={(e) => setDescripcion(e.target.value)}
              />
              <div style={{ display: 'flex', gap: 8 }}>
                <button style={styles.btnPrimary} onClick={crearZona}>{t('zona.guardar')}</button>
                <button style={styles.btnSecondary} onClick={() => setMostrarForm(false)}>{t('zona.cancelar')}</button>
              </div>
            </div>
          )}

          {zonas.length === 0 ? (
            <p style={styles.empty}>{t('dashboard.no_zonas')}</p>
          ) : (
            <ul style={styles.list}>
              {zonas.map((z) => (
                <li key={z.id} style={styles.listItem}>
                  <button style={styles.zonaBtn} onClick={() => cargarLecturas(z)}>
                    <strong>{z.nombre}</strong>
                    <span style={styles.desc}>{z.descripcion}</span>
                  </button>
                  <button style={styles.btnDanger} onClick={() => eliminarZona(z.id)}>
                    {t('dashboard.delete')}
                  </button>
                </li>
              ))}
            </ul>
          )}
        </section>

        {zonaSeleccionada && (
          <section style={styles.section}>
            <h2>{t('dashboard.lecturas_title')} — {zonaSeleccionada.nombre}</h2>
            {lecturas.length === 0 ? (
              <p style={styles.empty}>{t('dashboard.no_lecturas')}</p>
            ) : (
              <table style={styles.table}>
                <thead>
                  <tr>
                    <th>{t('lectura.tipo')}</th>
                    <th>{t('lectura.valor')}</th>
                    <th>{t('lectura.registrada')}</th>
                  </tr>
                </thead>
                <tbody>
                  {lecturas.map((l) => (
                    <tr key={l.id}>
                      <td>{l.tipo}</td>
                      <td>{l.valor}</td>
                      <td>{new Date(l.registradoEn).toLocaleString()}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </section>
        )}
      </main>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  page: { minHeight: '100vh', background: '#f0f4f0', fontFamily: 'sans-serif' },
  header: {
    background: '#2d6a4f', color: '#fff', padding: '1rem 2rem',
    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
  },
  headerTitle: { margin: 0, fontSize: '1.4rem' },
  main: { maxWidth: 900, margin: '0 auto', padding: '2rem 1rem' },
  section: { background: '#fff', borderRadius: 10, padding: '1.5rem', marginBottom: '1.5rem', boxShadow: '0 2px 8px rgba(0,0,0,0.07)' },
  sectionHeader: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' },
  form: { display: 'flex', flexDirection: 'column', gap: 8, marginBottom: '1rem', maxWidth: 400 },
  input: { padding: '0.5rem 0.75rem', borderRadius: 6, border: '1px solid #ccc', fontSize: '1rem' },
  list: { listStyle: 'none', padding: 0, margin: 0 },
  listItem: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '0.6rem 0', borderBottom: '1px solid #eee' },
  zonaBtn: { background: 'none', border: 'none', cursor: 'pointer', textAlign: 'left', display: 'flex', flexDirection: 'column', gap: 2 },
  desc: { color: '#777', fontSize: '0.85rem' },
  btnPrimary: { background: '#2d6a4f', color: '#fff', border: 'none', borderRadius: 6, padding: '0.45rem 1rem', cursor: 'pointer' },
  btnSecondary: { background: '#eee', color: '#333', border: 'none', borderRadius: 6, padding: '0.45rem 1rem', cursor: 'pointer' },
  btnDanger: { background: '#c0392b', color: '#fff', border: 'none', borderRadius: 6, padding: '0.35rem 0.8rem', cursor: 'pointer', fontSize: '0.85rem' },
  empty: { color: '#888', fontStyle: 'italic' },
  error: { color: '#c0392b', background: '#fdecea', borderRadius: 6, padding: '0.75rem 1rem', marginBottom: '1rem' },
  table: { width: '100%', borderCollapse: 'collapse', marginTop: '0.5rem' },
};

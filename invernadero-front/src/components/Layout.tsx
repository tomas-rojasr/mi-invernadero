/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  components/Layout.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Layout principal del panel. Combina la barra lateral de
 *              navegación con el área de contenido de cada página.
 */
import { Outlet } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { NavLink } from 'react-router-dom';
import LanguageToggle from './LanguageToggle';

const NAV_ITEMS = [
  { to: '/panel/zonas',    key: 'nav.zonas' },
  { to: '/panel/lecturas', key: 'nav.lecturas' },
  { to: '/panel/cultivos', key: 'nav.cultivos' },
  { to: '/panel/umbrales', key: 'nav.umbrales' },
  { to: '/panel/taiga',    key: 'nav.taiga' },
  { to: '/panel/perfil',   key: 'nav.perfil' },
];

/** Layout compartido por todas las rutas del panel (/panel/*). */
export default function Layout() {
  const { t } = useTranslation();

  return (
    <div style={styles.root}>
      <aside style={styles.sidebar}>
        <div style={styles.brand}>{t('app.title')}</div>
        <nav>
          {NAV_ITEMS.map(({ to, key }) => (
            <NavLink
              key={to}
              to={to}
              style={({ isActive }) => ({
                ...styles.navLink,
                ...(isActive ? styles.navActive : {}),
              })}
            >
              {t(key)}
            </NavLink>
          ))}
        </nav>
      </aside>

      <div style={styles.main}>
        <header style={styles.header}>
          <span />
          <LanguageToggle />
        </header>
        <div style={styles.content}>
          <Outlet />
        </div>
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  root: { display: 'flex', minHeight: '100vh', fontFamily: 'system-ui, sans-serif' },
  sidebar: {
    width: 210,
    minWidth: 210,
    background: '#2d6a4f',
    display: 'flex',
    flexDirection: 'column',
  },
  brand: {
    color: '#fff',
    fontWeight: 700,
    fontSize: '1rem',
    padding: '1.4rem 1.2rem 1rem',
    borderBottom: '1px solid rgba(255,255,255,0.15)',
    marginBottom: '0.5rem',
  },
  navLink: {
    display: 'block',
    color: 'rgba(255,255,255,0.80)',
    textDecoration: 'none',
    padding: '0.65rem 1.2rem',
    fontSize: '0.95rem',
    borderLeft: '3px solid transparent',
    transition: 'background 0.15s',
  },
  navActive: {
    background: 'rgba(255,255,255,0.15)',
    color: '#fff',
    borderLeft: '3px solid #b7e4c7',
    fontWeight: 600,
  },
  main: { flex: 1, display: 'flex', flexDirection: 'column', background: '#f0f4f0' },
  header: {
    background: '#fff',
    borderBottom: '1px solid #e0e0e0',
    padding: '0.6rem 1.5rem',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  content: { padding: '1.8rem', flex: 1 },
};

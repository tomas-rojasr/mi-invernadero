/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/LoginPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de inicio de sesión. Con OAuth2 activo muestra el
 *              botón de Google; en modo dev redirige directamente al panel.
 */
import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import LanguageToggle from '../components/LanguageToggle';

const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

/**
 * Página de login. Si el backend indica que el usuario ya está autenticado
 * (modo dev con oauth2-enabled=false), redirige automáticamente al panel.
 */
export default function LoginPage() {
  const { auth, loading } = useAuth();
  const navigate = useNavigate();
  const { t } = useTranslation();

  useEffect(() => {
    if (!loading && auth?.authenticated) {
      navigate('/panel', { replace: true });
    }
  }, [auth, loading, navigate]);

  return (
    <div style={styles.container}>
      <div style={styles.card}>
        <div style={styles.langRow}>
          <LanguageToggle />
        </div>

        <div style={styles.iconWrap}>🌿</div>
        <h1 style={styles.title}>{t('login.title')}</h1>
        <p style={styles.subtitle}>{t('login.subtitle')}</p>

        <div style={styles.divider} />

        {auth?.oauth2Enabled ? (
          <a href={`${BASE_URL}/oauth2/authorization/google`} style={styles.googleBtn}>
            <svg style={styles.googleIcon} viewBox="0 0 48 48">
              <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"/>
              <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"/>
              <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"/>
              <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"/>
              <path fill="none" d="M0 0h48v48H0z"/>
            </svg>
            {t('login.button')}
          </a>
        ) : (
          <button style={styles.button} onClick={() => navigate('/panel')}>
            {t('login.dev_note')}
          </button>
        )}

        <p style={styles.footer}>Electiva 1 · Ingeniería de Software</p>
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  container: {
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    background: 'linear-gradient(135deg, #d8f3dc 0%, #b7e4c7 50%, #95d5b2 100%)',
  },
  card: {
    background: '#fff',
    borderRadius: 20,
    padding: '2.5rem 2.5rem 2rem',
    boxShadow: '0 8px 40px rgba(45,106,79,0.15)',
    textAlign: 'center',
    minWidth: 340,
    maxWidth: 400,
  },
  langRow: { display: 'flex', justifyContent: 'flex-end', marginBottom: '0.5rem' },
  iconWrap: { fontSize: '3rem', marginBottom: '0.5rem' },
  title: { fontSize: '1.7rem', color: '#1b4332', fontWeight: 700, marginBottom: '0.4rem' },
  subtitle: { color: '#666', fontSize: '0.95rem', marginBottom: '1.5rem' },
  divider: {
    height: 1,
    background: '#e9ecef',
    margin: '0 0 1.5rem',
  },
  googleBtn: {
    display: 'inline-flex',
    alignItems: 'center',
    gap: '0.75rem',
    background: '#fff',
    color: '#3c4043',
    border: '1px solid #dadce0',
    borderRadius: 6,
    padding: '0.7rem 1.8rem',
    fontSize: '0.95rem',
    fontWeight: 500,
    cursor: 'pointer',
    textDecoration: 'none',
    boxShadow: '0 2px 6px rgba(0,0,0,0.10)',
    transition: 'box-shadow 0.2s',
    fontFamily: 'Roboto, sans-serif',
  },
  googleIcon: { width: 20, height: 20, flexShrink: 0 },
  button: {
    display: 'inline-block',
    background: '#2d6a4f',
    color: '#fff',
    border: 'none',
    borderRadius: 8,
    padding: '0.8rem 2rem',
    fontSize: '1rem',
    cursor: 'pointer',
    textDecoration: 'none',
  },
  footer: {
    marginTop: '1.8rem',
    fontSize: '0.75rem',
    color: '#aaa',
  },
};

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
        <h1 style={styles.title}>{t('login.title')}</h1>
        <p style={styles.subtitle}>{t('login.subtitle')}</p>

        {auth?.oauth2Enabled ? (
          <a href={`${BASE_URL}/oauth2/authorization/google`} style={styles.button}>
            {t('login.button')}
          </a>
        ) : (
          <button style={styles.button} onClick={() => navigate('/panel')}>
            {t('login.dev_note')}
          </button>
        )}
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
    background: '#f0f4f0',
  },
  card: {
    background: '#fff',
    borderRadius: 12,
    padding: '3rem 2.5rem',
    boxShadow: '0 4px 24px rgba(0,0,0,0.10)',
    textAlign: 'center',
    minWidth: 320,
  },
  langRow: { display: 'flex', justifyContent: 'flex-end', marginBottom: '1rem' },
  title: { fontSize: '1.8rem', color: '#2d6a4f', marginBottom: '0.5rem' },
  subtitle: { color: '#555', marginBottom: '2rem' },
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
};

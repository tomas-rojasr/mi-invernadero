/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  pages/PerfilPage.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Página de perfil del usuario. Muestra el estado de sesión
 *              y permite cerrar sesión cuando OAuth2 está activo.
 */
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';
import { s } from '../styles/shared';

const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8081';

/** Información de sesión del usuario y acción de cierre de sesión. */
export default function PerfilPage() {
  const { t } = useTranslation();
  const { auth } = useAuth();

  return (
    <div>
      <div style={s.pageHeader}>
        <h2 style={s.pageTitle}>{t('perfil.title')}</h2>
      </div>

      <div style={{ ...s.card, maxWidth: 480 }}>
        <table style={{ width: '100%', borderCollapse: 'collapse' }}>
          <tbody>
            {auth?.email && (
              <tr style={s.tr}>
                <td style={{ ...s.td, fontWeight: 600, width: 140 }}>{t('perfil.email')}</td>
                <td style={s.td}>{auth.email}</td>
              </tr>
            )}
            <tr style={s.tr}>
              <td style={{ ...s.td, fontWeight: 600 }}>{t('perfil.estado')}</td>
              <td style={s.td}>
                <span style={styles.badge}>✓ {t('perfil.estado')}</span>
              </td>
            </tr>
            <tr style={s.tr}>
              <td style={{ ...s.td, fontWeight: 600 }}>{t('perfil.oauth2')}</td>
              <td style={s.td}>
                {auth?.oauth2Enabled
                  ? <span style={styles.badgeGreen}>{t('perfil.oauth2')}</span>
                  : <span style={styles.badgeGray}>{t('perfil.dev_mode')}</span>
                }
              </td>
            </tr>
          </tbody>
        </table>

        {auth?.oauth2Enabled && (
          <div style={{ marginTop: '1.5rem' }}>
            <a href={`${BASE_URL}/logout`} style={s.btnDanger as React.CSSProperties}>
              {t('perfil.cerrar_sesion')}
            </a>
          </div>
        )}
      </div>
    </div>
  );
}

const styles: Record<string, React.CSSProperties> = {
  badge:      { background: '#d8f3dc', color: '#1b4332', borderRadius: 12, padding: '2px 10px', fontSize: '0.85rem', fontWeight: 600 },
  badgeGreen: { background: '#d8f3dc', color: '#1b4332', borderRadius: 12, padding: '2px 10px', fontSize: '0.85rem', fontWeight: 600 },
  badgeGray:  { background: '#f0f0f0', color: '#555',    borderRadius: 12, padding: '2px 10px', fontSize: '0.85rem' },
};

/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  components/ProtectedRoute.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Componente que protege rutas privadas. Redirige al login
 *              si el usuario no está autenticado.
 */
import { Navigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import { useAuth } from '../context/AuthContext';

interface Props {
  children: React.ReactNode;
}

/**
 * Envuelve rutas que requieren sesión activa.
 * Muestra un spinner mientras verifica el estado con el backend.
 */
export default function ProtectedRoute({ children }: Props) {
  const { auth, loading } = useAuth();
  const { t } = useTranslation();

  if (loading) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', padding: '4rem' }}>
        <p>{t('app.loading')}</p>
      </div>
    );
  }

  if (!auth?.authenticated) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}

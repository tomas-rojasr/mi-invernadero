/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  context/AuthContext.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Contexto de autenticación global. Consulta el estado de
 *              sesión al backend y lo expone a toda la aplicación.
 */
import { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import { api, AuthStatus } from '../api/client';

interface AuthContextValue {
  auth: AuthStatus | null;
  loading: boolean;
  refresh: () => void;
}

const AuthContext = createContext<AuthContextValue>({
  auth: null,
  loading: true,
  refresh: () => {},
});

/**
 * Proveedor de autenticación — envuelve la aplicación y resuelve el estado
 * de sesión consultando GET /api/v1/auth/status al montar.
 */
export function AuthProvider({ children }: { children: ReactNode }) {
  const [auth, setAuth] = useState<AuthStatus | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchStatus = () => {
    setLoading(true);
    api.auth
      .status()
      .then((res) => setAuth(res.data))
      .catch(() => setAuth({ authenticated: false, oauth2Enabled: false }))
      .finally(() => setLoading(false));
  };

  useEffect(() => { fetchStatus(); }, []);

  return (
    <AuthContext.Provider value={{ auth, loading, refresh: fetchStatus }}>
      {children}
    </AuthContext.Provider>
  );
}

/** Hook para acceder al contexto de autenticación desde cualquier componente. */
export const useAuth = () => useContext(AuthContext);

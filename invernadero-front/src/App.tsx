/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  App.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Componente raíz. Define el enrutamiento principal y envuelve
 *              la aplicación con los proveedores de contexto necesarios.
 */
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import './i18n';

/**
 * Raíz de la aplicación. Rutas:
 * - /login       → página de autenticación (pública)
 * - /panel       → dashboard principal (protegida)
 * - /            → redirige a /panel
 */
export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/panel"
            element={
              <ProtectedRoute>
                <DashboardPage />
              </ProtectedRoute>
            }
          />
          <Route path="*" element={<Navigate to="/panel" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

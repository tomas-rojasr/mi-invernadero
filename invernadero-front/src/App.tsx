/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  App.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Componente raíz. Define el enrutamiento principal con sidebar
 *              y envuelve la app con los proveedores de contexto.
 */
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/Layout';
import LoginPage from './pages/LoginPage';
import ZonasPage from './pages/ZonasPage';
import LecturasPage from './pages/LecturasPage';
import CultivosPage from './pages/CultivosPage';
import UmbralesPage from './pages/UmbralesPage';
import TaigaPage from './pages/TaigaPage';
import PerfilPage from './pages/PerfilPage';
import './i18n';

/**
 * Raíz de la aplicación. Rutas:
 * - /login         → página pública de autenticación
 * - /panel/*       → panel protegido con sidebar (zonas, lecturas, cultivos, umbrales, taiga, perfil)
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
                <Layout />
              </ProtectedRoute>
            }
          >
            <Route index element={<Navigate to="zonas" replace />} />
            <Route path="zonas"    element={<ZonasPage />} />
            <Route path="lecturas" element={<LecturasPage />} />
            <Route path="cultivos" element={<CultivosPage />} />
            <Route path="umbrales" element={<UmbralesPage />} />
            <Route path="taiga"    element={<TaigaPage />} />
            <Route path="perfil"   element={<PerfilPage />} />
          </Route>
          <Route path="*" element={<Navigate to="/panel" replace />} />
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

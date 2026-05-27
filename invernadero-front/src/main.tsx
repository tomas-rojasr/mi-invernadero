/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  main.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Punto de entrada de la aplicación React.
 *              Monta el componente raíz en el DOM.
 */
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);

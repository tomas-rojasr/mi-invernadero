/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  styles/shared.ts
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Estilos en línea reutilizables entre todas las páginas.
 *              Centraliza la paleta y los componentes visuales comunes.
 */
import type { CSSProperties } from 'react';

/** Objeto de estilos compartidos para tablas, formularios, botones y cards. */
export const s: Record<string, CSSProperties> = {
  pageHeader: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.2rem' },
  pageTitle:  { fontSize: '1.4rem', fontWeight: 700, color: '#1a1a1a', margin: 0 },
  card:       { background: '#fff', borderRadius: 10, padding: '1.4rem', boxShadow: '0 2px 8px rgba(0,0,0,0.07)', marginBottom: '1.2rem' },
  cardTitle:  { fontSize: '1rem', fontWeight: 600, marginBottom: '1rem', color: '#2d6a4f' },
  formGrid:   { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(220px, 1fr))', gap: 10, marginBottom: '1rem' },
  input:      { padding: '0.5rem 0.75rem', borderRadius: 6, border: '1px solid #ccc', fontSize: '0.95rem', width: '100%', boxSizing: 'border-box' as const },
  btnRow:     { display: 'flex', gap: 8 },
  btnPrimary: { background: '#2d6a4f', color: '#fff', border: 'none', borderRadius: 6, padding: '0.5rem 1.2rem', cursor: 'pointer', fontSize: '0.9rem', fontWeight: 600, textDecoration: 'none', display: 'inline-block' },
  btnSecondary:{ background: '#e9ecef', color: '#333', border: 'none', borderRadius: 6, padding: '0.5rem 1rem', cursor: 'pointer', fontSize: '0.9rem' },
  btnDanger:  { background: '#c0392b', color: '#fff', border: 'none', borderRadius: 6, padding: '0.4rem 0.9rem', cursor: 'pointer', fontSize: '0.85rem', fontWeight: 600 },
  table:      { width: '100%', borderCollapse: 'collapse' },
  th:         { textAlign: 'left', padding: '0.6rem 0.8rem', borderBottom: '2px solid #e0e0e0', fontSize: '0.85rem', color: '#555', fontWeight: 600, textTransform: 'uppercase' as const, letterSpacing: '0.04em' },
  td:         { padding: '0.65rem 0.8rem', borderBottom: '1px solid #f0f0f0', fontSize: '0.92rem', verticalAlign: 'middle' },
  tr:         { transition: 'background 0.1s' },
  empty:      { padding: '1.5rem', textAlign: 'center' as const, color: '#999', fontStyle: 'italic' },
  errorMsg:   { color: '#c0392b', background: '#fdecea', borderRadius: 6, padding: '0.7rem 1rem', marginBottom: '1rem', fontSize: '0.9rem' },
};

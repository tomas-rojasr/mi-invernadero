/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  components/LanguageToggle.tsx
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Botón para alternar entre español e inglés.
 *              Persiste la preferencia en localStorage.
 */
import { useTranslation } from 'react-i18next';

/** Alterna el idioma de la interfaz entre ES y EN. */
export default function LanguageToggle() {
  const { i18n } = useTranslation();
  const esActivo = i18n.language.startsWith('es');

  const toggle = () => {
    const next = esActivo ? 'en' : 'es';
    i18n.changeLanguage(next);
    localStorage.setItem('lang', next);
  };

  return (
    <button
      onClick={toggle}
      style={{
        cursor: 'pointer',
        padding: '4px 12px',
        borderRadius: 6,
        border: '1px solid #ccc',
        background: '#fff',
        fontWeight: 600,
        fontSize: '0.85rem',
        color: '#2d6a4f',
      }}
    >
      {esActivo ? 'EN' : 'ES'}
    </button>
  );
}

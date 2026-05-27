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

  const toggle = () => {
    const next = i18n.language === 'es' ? 'en' : 'es';
    i18n.changeLanguage(next);
    localStorage.setItem('lang', next);
  };

  return (
    <button onClick={toggle} style={{ cursor: 'pointer' }}>
      {i18n.language === 'es' ? 'EN' : 'ES'}
    </button>
  );
}

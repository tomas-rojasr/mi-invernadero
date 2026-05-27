/*
 * Proyecto: Invernadero Inteligente
 * Archivo:  i18n/index.ts
 * Autor:    Tomas Rojas
 * Fecha:    2026-05-26
 * Descripción: Configuración de internacionalización con i18next.
 *              Soporta español (predeterminado) e inglés.
 */
import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import es from './locales/es.json';
import en from './locales/en.json';

i18n
  .use(initReactI18next)
  .init({
    resources: {
      es: { translation: es },
      en: { translation: en },
    },
    lng: localStorage.getItem('lang') ?? 'es',
    fallbackLng: 'es',
    interpolation: { escapeValue: false },
  });

export default i18n;

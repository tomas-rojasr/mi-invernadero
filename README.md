# Invernadero Inteligente

**Proyecto académico — Electiva 1 de Ingeniería de Software**
**Autor:** Tomas Rojas | **Fecha:** Mayo 2026

---

## 1. Introducción

Invernadero Inteligente es una aplicación web full-stack para la gestión y monitoreo de invernaderos agrícolas. Permite registrar zonas de cultivo, tomar lecturas ambientales de sensores, administrar cultivos y definir umbrales de alerta por métrica. El sistema cuenta con autenticación OAuth2 con Google, internacionalización (español/inglés), integración con Taiga.io para gestión de historias de usuario, y pipeline de CI/CD automatizado con GitHub Actions.

---

## 2. Problema

Los pequeños y medianos productores agrícolas carecen de herramientas digitales centralizadas para:
- Monitorear las condiciones ambientales de sus invernaderos (temperatura, humedad, luz).
- Llevar un registro histórico de lecturas por zona y tipo de cultivo.
- Recibir alertas cuando los valores superan rangos seguros predefinidos.
- Gestionar la información desde cualquier dispositivo con acceso a internet.

---

## 3. Objetivos

**General:** Desarrollar una aplicación web que permita la gestión integral de un invernadero inteligente, aplicando buenas prácticas de ingeniería de software.

**Específicos:**
- Implementar una API REST con arquitectura hexagonal (puertos y adaptadores).
- Construir una interfaz de usuario reactiva con soporte multiidioma.
- Asegurar el acceso mediante OAuth2 con Google.
- Garantizar la calidad del software mediante pruebas unitarias, de integración, de API y de interfaz (Selenium).
- Automatizar el despliegue mediante CI/CD con GitHub Actions.
- Integrar el proyecto con Taiga.io para la gestión ágil.

---

## 4. Estado del Arte

| Herramienta / Concepto | Uso en el proyecto |
|---|---|
| Arquitectura hexagonal | Separación de dominio, aplicación e infraestructura |
| Spring Boot 3 + Spring Security | Backend REST con OAuth2 |
| React 19 + Vite | Frontend SPA con renderizado eficiente |
| PostgreSQL | Persistencia relacional en producción |
| Docker | Contenerización del backend para despliegue en Render |
| GitHub Actions | Pipeline CI/CD automatizado |
| Taiga.io | Gestión ágil de historias de usuario |
| i18next | Internacionalización español/inglés |

---

## 5. Arquitectura del Sistema

### Visión general

```
┌─────────────────────────────────────────────────────┐
│                   CLIENTE (Browser)                  │
│         React 19 + TypeScript + Vite                 │
│    Zonas · Lecturas · Cultivos · Umbrales · Taiga    │
└──────────────────────┬──────────────────────────────┘
                       │ HTTPS (axios + cookies)
┌──────────────────────▼──────────────────────────────┐
│              BACKEND (Spring Boot 3.5)               │
│                                                      │
│  ┌─────────────┐  ┌──────────────┐  ┌────────────┐  │
│  │  API Layer  │  │ App Services │  │  Domain    │  │
│  │ Controllers │→ │  (Use Cases) │→ │  Models    │  │
│  │    DTOs     │  │              │  │  Entities  │  │
│  └─────────────┘  └──────────────┘  └────────────┘  │
│                                                      │
│  ┌──────────────────────────────────────────────┐   │
│  │        Repository (Ports & Adapters)         │   │
│  │   JPA Repositories · PostgreSQL Driver       │   │
│  └──────────────────────────────────────────────┘   │
└──────────────────────┬──────────────────────────────┘
                       │
        ┌──────────────┴──────────────┐
        │                             │
┌───────▼───────┐            ┌────────▼────────┐
│  PostgreSQL   │            │   Taiga.io API  │
│  (Render DB)  │            │  (User Stories) │
└───────────────┘            └─────────────────┘
```

### Arquitectura hexagonal (backend)

El backend implementa el patrón de **puertos y adaptadores**:

- **Dominio:** modelos de negocio puros (`Zona`, `Cultivo`, `LecturaAmbiental`, `UmbralAmbiental`, enum `MetricaTipo`) sin dependencias de frameworks.
- **Aplicación:** servicios de casos de uso (`ZonaApplicationService`, `CultivoApplicationService`, etc.) que orquestan la lógica de negocio.
- **Infraestructura:** adaptadores de entrada (Controllers REST) y salida (JPA Repositories, Taiga REST Client).

---

## 6. Tecnologías

### Backend
| Tecnología | Versión | Propósito |
|---|---|---|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.5.14 | Framework principal |
| Spring Security + OAuth2 | 3.5.14 | Autenticación con Google |
| Spring Data JPA / Hibernate | 3.5.14 | ORM y persistencia |
| PostgreSQL | — | Base de datos relacional |
| Springdoc OpenAPI | 2.8.8 | Documentación Swagger UI |
| Docker | — | Contenerización |
| Maven | 3.9.6 | Gestión de dependencias |

### Frontend
| Tecnología | Versión | Propósito |
|---|---|---|
| React | 19.2.6 | Framework UI |
| TypeScript | 6.0.2 | Tipado estático |
| Vite | 8.0.12 | Bundler y dev server |
| React Router | 6.30.3 | Navegación SPA |
| Axios | 1.16.1 | Cliente HTTP |
| i18next | 26.3.0 | Internacionalización |

### Infraestructura y DevOps
| Herramienta | Uso |
|---|---|
| GitHub Actions | Pipeline CI/CD |
| Render | Hosting del backend (Docker) |
| Vercel | Hosting del frontend |
| Taiga.io | Gestión ágil del proyecto |

---

## 7. Modelo de Datos

### Diagrama entidad-relación

```
ZONAS
├── id          UUID (PK)
├── nombre      VARCHAR(120)  NOT NULL
├── descripcion VARCHAR(2000)
└── creado_en   TIMESTAMP

CULTIVOS
├── id          UUID (PK)
├── zona_id     UUID (FK → zonas)
├── nombre      VARCHAR(200)  NOT NULL
├── variedad    VARCHAR(120)
├── notas       VARCHAR(2000)
├── plantado_en TIMESTAMP
└── creado_en   TIMESTAMP

LECTURAS_AMBIENTALES
├── id             UUID (PK)
├── zona_id        UUID (FK → zonas)
├── tipo           ENUM (TEMPERATURA_C, HUMEDAD_RELATIVA_PCT, LUZ_LUX, HUMEDAD_SUELO_PCT)
├── valor          DECIMAL(10,2)  NOT NULL
└── registrado_en  TIMESTAMP

UMBRALES_AMBIENTALES
├── id            UUID (PK)
├── zona_id       UUID (FK → zonas)
├── tipo          ENUM (mismo que lecturas)
├── valor_min     DECIMAL(10,2)
├── valor_max     DECIMAL(10,2)
└── actualizado_en TIMESTAMP
```

**Regla de negocio:** por cada zona solo puede existir un umbral por tipo de métrica (patrón upsert).

---

## 8. Catálogo de API REST

Base URL: `https://invernadero-back.onrender.com/api/v1`
Documentación interactiva: `/swagger-ui.html`

### Autenticación
| Método | Ruta | Descripción |
|---|---|---|
| GET | `/auth/status` | Estado de sesión del usuario |

### Zonas
| Método | Ruta | Descripción | Código |
|---|---|---|---|
| GET | `/zonas` | Listar todas las zonas | 200 |
| POST | `/zonas` | Crear zona | 201 |
| DELETE | `/zonas/{id}` | Eliminar zona | 204 |

### Lecturas ambientales
| Método | Ruta | Descripción | Código |
|---|---|---|---|
| GET | `/zonas/{zonaId}/lecturas` | Listar lecturas de una zona | 200 |
| POST | `/zonas/{zonaId}/lecturas` | Registrar lectura | 201 |
| DELETE | `/zonas/{zonaId}/lecturas/{id}` | Eliminar lectura | 204 |

### Cultivos
| Método | Ruta | Descripción | Código |
|---|---|---|---|
| GET | `/zonas/{zonaId}/cultivos` | Listar cultivos | 200 |
| POST | `/zonas/{zonaId}/cultivos` | Crear cultivo | 201 |
| PUT | `/zonas/{zonaId}/cultivos/{id}` | Actualizar cultivo | 200 |
| DELETE | `/zonas/{zonaId}/cultivos/{id}` | Eliminar cultivo | 204 |

### Umbrales ambientales
| Método | Ruta | Descripción | Código |
|---|---|---|---|
| GET | `/zonas/{zonaId}/umbrales` | Listar umbrales | 200 |
| PUT | `/zonas/{zonaId}/umbrales` | Definir/actualizar umbral (upsert) | 200 |
| DELETE | `/zonas/{zonaId}/umbrales/{id}` | Eliminar umbral | 204 |

### Integración Taiga
| Método | Ruta | Descripción | Código |
|---|---|---|---|
| GET | `/taiga/historias` | Listar todas las historias del proyecto | 200 |
| GET | `/taiga/historias/{id}` | Consultar historia por ID | 200 |

---

## 9. Historias de Usuario

Gestionadas en [Taiga.io](https://tree.taiga.io/project/tomas-rojasr-invernadero-inteligente):

| # | Historia | Estado |
|---|---|---|
| 1 | Como usuario quiero gestionar zonas del invernadero para organizar el espacio de cultivo | New |
| 2 | Como usuario quiero registrar lecturas ambientales para monitorear las condiciones por zona | New |
| 3 | Como usuario quiero administrar cultivos por zona para hacer seguimiento de lo sembrado | New |
| 4 | Como usuario quiero definir umbrales ambientales para saber cuándo las condiciones son críticas | New |
| 5 | Como usuario quiero autenticarme con Google OAuth2 para acceder de forma segura | New |

---

## 10. Pruebas

### Pruebas unitarias — JUnit 5 + Mockito

**`ZonaApplicationServiceTest`**
- `crear_debePersistirYRetornarZona`
- `buscarPorId_debeRetornarZonaSiExiste`
- `buscarPorId_debeLanzarExcepcionSiNoExiste`
- `listarTodas_debeRetornarListaCompleta`
- `eliminar_debeBorrarZonaYDatosDependientes`
- `eliminar_debeLanzarExcepcionSiZonaNoExiste`

### Pruebas de integración — Spring MockMvc

**`ZonaControllerTest`**
- `listar_debeRetornarListaConStatus200`
- `crear_debeRetornarZonaCreada`
- `crear_conNombreVacio_debeRetornar400`
- `eliminar_debeRetornar204`
- `eliminar_zonaInexistente_debeRetornar404`

**`AuthControllerTest`**
- `status_debeRetornarOk`
- `status_sinOauth2_debeRetornarAutenticado`

### Pruebas de API — Python + pytest + requests (23 pruebas)

| Módulo | Pruebas |
|---|---|
| `test_zonas.py` | Listar, crear, validar, eliminar zonas |
| `test_cultivos.py` | CRUD completo de cultivos |
| `test_lecturas.py` | Registrar, listar, eliminar lecturas; validar zona inexistente |
| `test_umbrales.py` | Definir, upsert, listar, eliminar umbrales |

### Pruebas de interfaz — Selenium (9 pruebas)

- `test_titulo_zonas_visible` — navegación a sección Zonas
- `test_crear_zona` — flujo completo de creación de zona
- `test_titulo_lecturas_visible` — navegación a Lecturas
- `test_registrar_lectura` — registro de lectura ambiental
- `test_titulo_cultivos_visible` — navegación a Cultivos
- `test_crear_cultivo` — creación de cultivo
- `test_titulo_umbrales_visible` — navegación a Umbrales
- `test_definir_umbral` — definición de umbral por métrica
- `test_navegacion_sidebar` — validación del menú lateral

---

## 11. Pipeline CI/CD

```
Push a main
    │
    ├── [Pruebas Backend]     JUnit 5 en Java 17 (Maven)
    │
    ├── [Build Frontend]      npm ci + npm run build (Node 20)
    │
    └── [Desplegar en Render] curl al deploy hook → redespliegue automático
```

El pipeline está definido en [.github/workflows/ci.yml](.github/workflows/ci.yml). Pruebas y build corren en paralelo; el despliegue solo ocurre si ambos pasan y el push es sobre `main`.

---

## 12. Despliegue

| Componente | Plataforma | URL |
|---|---|---|
| Backend | Render (Docker) | https://invernadero-back.onrender.com |
| Frontend | Vercel | https://mi-invernadero-one.vercel.app |
| Base de datos | Render PostgreSQL | — |

### Variables de entorno del backend (Render)

| Variable | Descripción |
|---|---|
| `DATABASE_URL` | URL de conexión PostgreSQL |
| `APP_OAUTH2_ENABLED` | Activa OAuth2 con Google (`true`) |
| `GOOGLE_CLIENT_ID` | ID de cliente de Google Cloud |
| `GOOGLE_CLIENT_SECRET` | Secreto de cliente de Google Cloud |
| `APP_CORS_ORIGINS` | Origen del frontend permitido |
| `TAIGA_TOKEN` | Token de acceso a la API de Taiga |
| `TAIGA_PROJECT_SLUG` | Slug del proyecto en Taiga |

---

## 13. Estructura del Repositorio

```
mi-invernadero/
├── .github/
│   └── workflows/
│       └── ci.yml              # Pipeline CI/CD
├── invernadero-back/           # Backend Spring Boot
│   ├── src/main/java/com/tomasrojas/invernadero/
│   │   ├── api/                # Controllers, DTOs, mappers
│   │   ├── config/             # Seguridad, OAuth2, CORS, Taiga
│   │   ├── model/              # Dominio, entidades JPA, enums
│   │   ├── repository/         # Puertos, adaptadores JPA
│   │   └── service/            # Casos de uso
│   ├── src/test/               # Pruebas unitarias e integración
│   ├── Dockerfile
│   └── pom.xml
├── invernadero-front/          # Frontend React
│   ├── src/
│   │   ├── api/                # Cliente HTTP (axios)
│   │   ├── components/         # Layout, ProtectedRoute, i18n toggle
│   │   ├── context/            # AuthContext
│   │   ├── i18n/               # Traducciones ES/EN
│   │   ├── pages/              # Zonas, Lecturas, Cultivos, Umbrales, Taiga, Perfil
│   │   └── styles/             # Estilos compartidos
│   ├── vercel.json
│   └── package.json
└── invernadero-tests/          # Pruebas externas
    ├── api/                    # pytest + requests (23 pruebas)
    ├── ui_tests/               # Selenium (9 pruebas)
    └── conftest.py
```

---

## 14. Seguridad

- **OAuth2 con Google:** autenticación delegada, sin contraseñas almacenadas.
- **Modo desarrollo:** cuando `APP_OAUTH2_ENABLED=false`, todas las rutas son públicas para facilitar el desarrollo local.
- **Cookies de sesión:** `SameSite=None; Secure; HttpOnly` para permitir sesiones cross-domain entre Render y Vercel.
- **CORS:** lista de orígenes permitidos configurable por variable de entorno.
- **Secretos:** nunca en el repositorio; gestionados como variables de entorno y GitHub Secrets.

---

## 15. Internacionalización (i18n)

La aplicación soporta **español** e **inglés**. El idioma se detecta automáticamente desde el navegador y se puede cambiar con el botón de la interfaz. Todas las cadenas de texto están externalizadas en:

- `invernadero-front/src/i18n/locales/es.json`
- `invernadero-front/src/i18n/locales/en.json`

---

## 16. Resultados

- **32 pruebas automatizadas:** 13 JUnit/MockMvc + 10 Python API + 9 Selenium — todas pasan.
- **Aplicación en producción:** accesible públicamente con autenticación Google.
- **Pipeline funcional:** cada push a `main` ejecuta pruebas y redespliegue automático.
- **Integración Taiga:** las 5 historias de usuario se visualizan en tiempo real dentro de la aplicación.
- **Documentación Swagger:** disponible en `/swagger-ui.html`.

---

## 17. Conclusiones y Recomendaciones

**Conclusiones:**
- La arquitectura hexagonal facilitó la escritura de pruebas unitarias al aislar el dominio de la infraestructura.
- El uso de variables de entorno y perfiles de Spring permitió un flujo de desarrollo local limpio sin exponer credenciales.
- OAuth2 con Google elimina la necesidad de gestionar contraseñas y mejora la seguridad del sistema.

**Recomendaciones:**
- Implementar alertas en tiempo real (WebSockets o SSE) cuando una lectura supere los umbrales definidos.
- Agregar gráficas históricas de lecturas por zona y métrica.
- Implementar roles de usuario (administrador, operario de invernadero).
- Agregar caché para reducir latencia en el tier gratuito de Render.

---

## 18. Referencias

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [React Documentation](https://react.dev/)
- [Taiga API Documentation](https://docs.taiga.io/api.html)
- [Render Deployment Guide](https://render.com/docs)
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)

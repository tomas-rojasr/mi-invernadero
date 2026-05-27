"""
Proyecto: Invernadero Inteligente
Archivo:  conftest.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Fixtures compartidas entre tests de API y Selenium.
"""
import pytest
import requests

API_BASE = "http://localhost:8081/api/v1"
FRONT_BASE = "http://localhost:5173"


@pytest.fixture(scope="session")
def api():
    """Sesión HTTP reutilizable para todos los tests de API."""
    session = requests.Session()
    session.headers.update({"Content-Type": "application/json"})
    return session


@pytest.fixture
def zona_nueva(api):
    """Crea una zona de prueba y la elimina al terminar el test."""
    r = api.post(f"{API_BASE}/zonas", json={"nombre": "Zona Test", "descripcion": "Prueba automatizada"})
    assert r.status_code == 201, f"No se pudo crear zona: {r.text}"
    zona = r.json()
    yield zona
    api.delete(f"{API_BASE}/zonas/{zona['id']}")

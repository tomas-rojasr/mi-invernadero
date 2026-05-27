"""
Proyecto: Invernadero Inteligente
Archivo:  api/test_zonas.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Tests de API REST para el recurso Zona.
"""
import pytest
import requests

API_BASE = "http://localhost:8081/api/v1"


def test_listar_zonas_retorna_200(api):
    r = api.get(f"{API_BASE}/zonas")
    assert r.status_code == 200
    assert isinstance(r.json(), list)


def test_crear_zona_retorna_201(api):
    payload = {"nombre": "Invernadero Norte", "descripcion": "Zona de prueba"}
    r = api.post(f"{API_BASE}/zonas", json=payload)
    assert r.status_code == 201
    body = r.json()
    assert body["nombre"] == "Invernadero Norte"
    assert "id" in body
    # Limpieza
    api.delete(f"{API_BASE}/zonas/{body['id']}")


def test_crear_zona_sin_nombre_retorna_400(api):
    r = api.post(f"{API_BASE}/zonas", json={"nombre": "", "descripcion": "Sin nombre"})
    assert r.status_code == 400


def test_zona_aparece_en_listado(api, zona_nueva):
    r = api.get(f"{API_BASE}/zonas")
    ids = [z["id"] for z in r.json()]
    assert zona_nueva["id"] in ids


def test_eliminar_zona_retorna_204(api):
    r = api.post(f"{API_BASE}/zonas", json={"nombre": "A eliminar", "descripcion": ""})
    zona_id = r.json()["id"]
    del_r = api.delete(f"{API_BASE}/zonas/{zona_id}")
    assert del_r.status_code == 204


def test_zona_eliminada_no_aparece(api):
    r = api.post(f"{API_BASE}/zonas", json={"nombre": "Temporal", "descripcion": ""})
    zona_id = r.json()["id"]
    api.delete(f"{API_BASE}/zonas/{zona_id}")
    ids = [z["id"] for z in api.get(f"{API_BASE}/zonas").json()]
    assert zona_id not in ids

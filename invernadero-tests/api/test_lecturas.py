"""
Proyecto: Invernadero Inteligente
Archivo:  api/test_lecturas.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Tests de API REST para el recurso Lectura.
"""

API_BASE = "http://localhost:8081/api/v1"


def test_listar_lecturas_retorna_200(api, zona_nueva):
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas")
    assert r.status_code == 200
    assert isinstance(r.json(), list)


def test_registrar_lectura_retorna_201(api, zona_nueva):
    payload = {"tipo": "TEMPERATURA_C", "valor": 24.5}
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas", json=payload)
    assert r.status_code == 201
    body = r.json()
    assert body["tipo"] == "TEMPERATURA_C"
    assert body["valor"] == 24.5
    assert "id" in body


def test_lectura_sin_tipo_valido_retorna_400(api, zona_nueva):
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas",
                 json={"tipo": "TIPO_INVALIDO", "valor": 10.0})
    assert r.status_code == 400


def test_lectura_aparece_en_listado(api, zona_nueva):
    api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas",
             json={"tipo": "HUMEDAD_RELATIVA_PCT", "valor": 65.0})
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas")
    tipos = [l["tipo"] for l in r.json()]
    assert "HUMEDAD_RELATIVA_PCT" in tipos


def test_eliminar_lectura_retorna_204(api, zona_nueva):
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas",
                 json={"tipo": "LUZ_LUX", "valor": 1500.0})
    lectura_id = r.json()["id"]
    del_r = api.delete(f"{API_BASE}/zonas/{zona_nueva['id']}/lecturas/{lectura_id}")
    assert del_r.status_code == 204


def test_zona_inexistente_retorna_404(api):
    r = api.get(f"{API_BASE}/zonas/00000000-0000-0000-0000-000000000000/lecturas")
    assert r.status_code == 404

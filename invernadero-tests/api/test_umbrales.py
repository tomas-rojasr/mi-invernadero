"""
Proyecto: Invernadero Inteligente
Archivo:  api/test_umbrales.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Tests de API REST para el recurso UmbralAmbiental.
"""

API_BASE = "http://localhost:8081/api/v1"


def test_listar_umbrales_retorna_200(api, zona_nueva):
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales")
    assert r.status_code == 200
    assert isinstance(r.json(), list)


def test_definir_umbral_retorna_200(api, zona_nueva):
    payload = {"tipo": "TEMPERATURA_C", "valorMin": 15.0, "valorMax": 35.0}
    r = api.put(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales", json=payload)
    assert r.status_code == 200
    body = r.json()
    assert body["tipo"] == "TEMPERATURA_C"
    assert body["valorMin"] == 15.0
    assert body["valorMax"] == 35.0


def test_upsert_umbral_actualiza_existente(api, zona_nueva):
    zona_id = zona_nueva["id"]
    api.put(f"{API_BASE}/zonas/{zona_id}/umbrales",
            json={"tipo": "HUMEDAD_RELATIVA_PCT", "valorMin": 40.0, "valorMax": 80.0})
    r = api.put(f"{API_BASE}/zonas/{zona_id}/umbrales",
                json={"tipo": "HUMEDAD_RELATIVA_PCT", "valorMin": 50.0, "valorMax": 90.0})
    assert r.status_code == 200
    umbrales = api.get(f"{API_BASE}/zonas/{zona_id}/umbrales").json()
    humedad = [u for u in umbrales if u["tipo"] == "HUMEDAD_RELATIVA_PCT"]
    assert len(humedad) == 1
    assert humedad[0]["valorMin"] == 50.0


def test_umbral_aparece_en_listado(api, zona_nueva):
    api.put(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales",
            json={"tipo": "LUZ_LUX", "valorMin": 500.0, "valorMax": 5000.0})
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales")
    tipos = [u["tipo"] for u in r.json()]
    assert "LUZ_LUX" in tipos


def test_eliminar_umbral_retorna_204(api, zona_nueva):
    r = api.put(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales",
                json={"tipo": "HUMEDAD_SUELO_PCT", "valorMin": 20.0, "valorMax": 80.0})
    umbral_id = r.json()["id"]
    del_r = api.delete(f"{API_BASE}/zonas/{zona_nueva['id']}/umbrales/{umbral_id}")
    assert del_r.status_code == 204

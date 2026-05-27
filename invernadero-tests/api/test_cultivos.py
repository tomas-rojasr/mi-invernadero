"""
Proyecto: Invernadero Inteligente
Archivo:  api/test_cultivos.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Tests de API REST para el recurso Cultivo.
"""

API_BASE = "http://localhost:8081/api/v1"


def test_listar_cultivos_retorna_200(api, zona_nueva):
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos")
    assert r.status_code == 200
    assert isinstance(r.json(), list)


def test_crear_cultivo_retorna_201(api, zona_nueva):
    payload = {"nombre": "Tomate Cherry", "variedad": "Grape", "notas": "Siembra de prueba"}
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos", json=payload)
    assert r.status_code == 201
    body = r.json()
    assert body["nombre"] == "Tomate Cherry"
    assert body["variedad"] == "Grape"
    assert "id" in body


def test_crear_cultivo_sin_nombre_retorna_400(api, zona_nueva):
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos",
                 json={"nombre": "", "variedad": "Roja"})
    assert r.status_code == 400


def test_cultivo_aparece_en_listado(api, zona_nueva):
    api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos",
             json={"nombre": "Lechuga", "variedad": "Romana"})
    r = api.get(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos")
    nombres = [c["nombre"] for c in r.json()]
    assert "Lechuga" in nombres


def test_actualizar_cultivo_retorna_200(api, zona_nueva):
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos",
                 json={"nombre": "Pepino", "variedad": "Largo"})
    cultivo_id = r.json()["id"]
    upd = api.put(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos/{cultivo_id}",
                  json={"nombre": "Pepino Actualizado", "variedad": "Corto", "notas": "Editado"})
    assert upd.status_code == 200
    assert upd.json()["nombre"] == "Pepino Actualizado"


def test_eliminar_cultivo_retorna_204(api, zona_nueva):
    r = api.post(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos",
                 json={"nombre": "A eliminar"})
    cultivo_id = r.json()["id"]
    del_r = api.delete(f"{API_BASE}/zonas/{zona_nueva['id']}/cultivos/{cultivo_id}")
    assert del_r.status_code == 204

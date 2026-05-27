"""
Proyecto: Invernadero Inteligente
Archivo:  selenium/test_ui.py
Autor:    Tomas Rojas
Fecha:    2026-05-27
Descripcion: Tests end-to-end con Selenium. Verifica los flujos principales
             de la interfaz: login dev, zonas, lecturas, cultivos y umbrales.
"""
import pytest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait, Select
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager

FRONT_URL = "http://localhost:5173"
WAIT = 8  # segundos de espera máxima


@pytest.fixture(scope="module")
def driver():
    opts = Options()
    opts.add_argument("--headless=new")
    opts.add_argument("--no-sandbox")
    opts.add_argument("--disable-dev-shm-usage")
    opts.add_argument("--window-size=1280,900")
    drv = webdriver.Chrome(
        service=Service(ChromeDriverManager().install()),
        options=opts,
    )
    yield drv
    drv.quit()


@pytest.fixture(autouse=True)
def login(driver):
    """En modo dev (oauth2-enabled=false) el panel es accesible directamente."""
    driver.get(f"{FRONT_URL}/panel")
    WebDriverWait(driver, WAIT).until(EC.url_contains("/panel"))


# ── Zonas ─────────────────────────────────────────────────────────────────────

def test_titulo_zonas_visible(driver):
    driver.get(f"{FRONT_URL}/panel/zonas")
    header = WebDriverWait(driver, WAIT).until(
        EC.visibility_of_element_located((By.TAG_NAME, "h2"))
    )
    assert "Zona" in header.text or "Zone" in header.text


def test_crear_zona(driver):
    driver.get(f"{FRONT_URL}/panel/zonas")
    WebDriverWait(driver, WAIT).until(
        EC.element_to_be_clickable((By.XPATH, "//button[contains(text(),'+')]"))
    ).click()
    driver.find_element(By.XPATH, "//input[@placeholder]").send_keys("Zona Selenium")
    inputs = driver.find_elements(By.XPATH, "//input[@placeholder]")
    if len(inputs) > 1:
        inputs[1].send_keys("Descripción de prueba")
    driver.find_element(By.XPATH, "//button[contains(text(),'Guardar') or contains(text(),'Save')]").click()
    WebDriverWait(driver, WAIT).until(
        EC.text_to_be_present_in_element((By.TAG_NAME, "body"), "Zona Selenium")
    )


# ── Lecturas ──────────────────────────────────────────────────────────────────

def test_titulo_lecturas_visible(driver):
    driver.get(f"{FRONT_URL}/panel/lecturas")
    header = WebDriverWait(driver, WAIT).until(
        EC.visibility_of_element_located((By.TAG_NAME, "h2"))
    )
    assert header.text != ""


def test_registrar_lectura(driver):
    driver.get(f"{FRONT_URL}/panel/lecturas")
    WebDriverWait(driver, WAIT).until(
        EC.element_to_be_clickable((By.XPATH, "//button[contains(text(),'+')]"))
    ).click()

    # Selecciona zona en el select del formulario
    selects = WebDriverWait(driver, WAIT).until(
        lambda d: d.find_elements(By.TAG_NAME, "select")
    )
    zona_select = Select(selects[0])
    opciones = zona_select.options
    assert len(opciones) > 1, "No hay zonas disponibles para registrar lectura"
    zona_select.select_by_index(1)

    # Ingresa el valor
    valor_input = driver.find_element(By.XPATH, "//input[@type='number']")
    valor_input.clear()
    valor_input.send_keys("22.5")

    driver.find_element(By.XPATH, "//button[contains(text(),'Registrar') or contains(text(),'Record')]").click()
    WebDriverWait(driver, WAIT).until(
        EC.text_to_be_present_in_element((By.TAG_NAME, "body"), "22.5")
    )


# ── Cultivos ──────────────────────────────────────────────────────────────────

def test_titulo_cultivos_visible(driver):
    driver.get(f"{FRONT_URL}/panel/cultivos")
    header = WebDriverWait(driver, WAIT).until(
        EC.visibility_of_element_located((By.TAG_NAME, "h2"))
    )
    assert header.text != ""


def test_crear_cultivo(driver):
    driver.get(f"{FRONT_URL}/panel/cultivos")
    WebDriverWait(driver, WAIT).until(
        EC.element_to_be_clickable((By.XPATH, "//button[contains(text(),'+')]"))
    ).click()

    selects = WebDriverWait(driver, WAIT).until(
        lambda d: d.find_elements(By.TAG_NAME, "select")
    )
    zona_select = Select(selects[0])
    assert len(zona_select.options) > 1, "No hay zonas para crear cultivo"
    zona_select.select_by_index(1)

    inputs = driver.find_elements(By.XPATH, "//input[@placeholder]")
    inputs[0].send_keys("Tomate Selenium")

    driver.find_element(By.XPATH, "//button[contains(text(),'Guardar') or contains(text(),'Save')]").click()
    WebDriverWait(driver, WAIT).until(
        EC.text_to_be_present_in_element((By.TAG_NAME, "body"), "Tomate Selenium")
    )


# ── Umbrales ──────────────────────────────────────────────────────────────────

def test_titulo_umbrales_visible(driver):
    driver.get(f"{FRONT_URL}/panel/umbrales")
    header = WebDriverWait(driver, WAIT).until(
        EC.visibility_of_element_located((By.TAG_NAME, "h2"))
    )
    assert header.text != ""


def test_definir_umbral(driver):
    driver.get(f"{FRONT_URL}/panel/umbrales")
    WebDriverWait(driver, WAIT).until(
        EC.element_to_be_clickable((By.XPATH, "//button[contains(text(),'+')]"))
    ).click()

    selects = WebDriverWait(driver, WAIT).until(
        lambda d: d.find_elements(By.TAG_NAME, "select")
    )
    zona_select = Select(selects[0])
    assert len(zona_select.options) > 1, "No hay zonas para definir umbral"
    zona_select.select_by_index(1)

    inputs = driver.find_elements(By.XPATH, "//input[@type='number']")
    inputs[0].send_keys("10")
    inputs[1].send_keys("40")

    driver.find_element(By.XPATH, "//button[contains(text(),'Guardar') or contains(text(),'Save')]").click()
    WebDriverWait(driver, WAIT).until(
        EC.text_to_be_present_in_element((By.TAG_NAME, "body"), "10")
    )


# ── Navegación ────────────────────────────────────────────────────────────────

def test_navegacion_sidebar(driver):
    driver.get(f"{FRONT_URL}/panel/zonas")
    secciones = ["lecturas", "cultivos", "umbrales"]
    for seccion in secciones:
        link = WebDriverWait(driver, WAIT).until(
            EC.element_to_be_clickable((By.XPATH, f"//a[contains(@href,'{seccion}')]"))
        )
        link.click()
        WebDriverWait(driver, WAIT).until(EC.url_contains(seccion))
        assert seccion in driver.current_url

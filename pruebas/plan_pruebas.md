# Plan de pruebas — PréstamoLab CTMA build 1.0
**Objetivo:** verificar historias HU-01 a HU-08, reglas de disponibilidad, validación, navegación, consistencia y cancelación.
**Ambiente:** Android Studio, JDK compatible con AGP, emulador/dispositivo API 24+.
**Base:** historias y criterios en `scrum/actividad2_historias_usuario.md`.
**Datos:** exclusivamente sintéticos incluidos en `InMemoryPrestamoRepository`.
**Entrada:** proyecto compila y catálogo carga. **Salida:** casos ejecutados y defectos reales documentados.
**Estados:** PASS (esperado coincide), FAIL (difiere), BLOCKED (no pudo ejecutarse).

## Técnicas
Partición de equivalencia, valores límite, transición de estados, decisión y caso de uso.

## Suite trazable
| ID | Escenario | Esperado | Técnica |
|---|---|---|---|
| TC-01 | Catálogo con datos | Equipos visibles | Caso de uso |
| TC-02 | equipoId válido | Detalle correcto | Caso de uso |
| TC-03 | equipoId inexistente | Estado recuperable | Negativa |
| TC-04 | Propósito 9 | Rechaza | Límite |
| TC-05 | Propósito 10 | Acepta | Límite |
| TC-06 | Propósito 180 | Acepta | Límite |
| TC-07 | Propósito 181 | Rechaza | Límite |
| TC-08 | Duración 0 | Rechaza | Límite |
| TC-09 | Duración 1 | Acepta | Límite |
| TC-10 | Duración 8 | Acepta | Límite |
| TC-11 | Duración 9 | Rechaza | Límite |
| TC-12 | Equipo no disponible | Solicitud rechazada | Decisión |
| TC-13 | Doble guardado | Una sola solicitud | Riesgo |
| TC-14 | Solicitud válida | SOLICITADA + RESERVADO | Caso de uso |
| TC-15 | Cancelar SOLICITADA | CANCELADA + DISPONIBLE | Transición |
| TC-16 | Cancelar CANCELADA | No permitido | Transición |
| TC-17 | Catálogo → detalle | Navegación correcta | Navegación |
| TC-18 | Formulario vacío | Mensajes específicos | Accesibilidad/negativa |
| TC-19 | Ambiente vacío | Rechaza | Equivalencia |
| TC-20 | solicitudId inexistente | Estado recuperable | Negativa |

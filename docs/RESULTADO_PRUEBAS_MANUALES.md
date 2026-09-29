# Resultados de pruebas manuales — PréstamoLab CTMA

## Objetivo

Registrar la ejecución de las principales pruebas funcionales del incremento de PréstamoLab CTMA.

## Estados utilizados

- PASS: el comportamiento observado coincide con el resultado esperado.
- FAIL: se encontró un comportamiento incorrecto.
- BLOCKED: la prueba no pudo ejecutarse por una dependencia o bloqueo.

## Bitácora

| ID | Caso | Resultado | Observación |
|---|---|---|---|
| TC-01 | Consultar catálogo | PASS | Se muestran los equipos registrados. |
| TC-02 | Consultar detalle existente | PASS | Se muestran nombre, categoría y estado. |
| TC-03 | Consultar equipo inexistente | PASS | Se muestra un estado recuperable sin cierre inesperado. |
| TC-04 | Solicitar equipo disponible | PASS | La solicitud puede registrarse correctamente. |
| TC-05 | Solicitar equipo no disponible | PASS | Se informa que el equipo no está disponible. |
| TC-06 | Propósito inválido | PASS | La validación impide registrar datos inválidos. |
| TC-07 | Duración inválida | PASS | La validación impide una duración fuera del rango permitido. |
| TC-08 | Doble solicitud del mismo equipo | PASS | Se evita una solicitud activa duplicada. |
| TC-09 | Consultar mis solicitudes | PASS | Se muestran las solicitudes registradas. |
| TC-10 | Cancelar solicitud solicitada | PASS | La solicitud pasa a CANCELADA y el equipo queda disponible. |
| TC-11 | Buscar equipo | PASS | Se muestran las coincidencias de búsqueda. |
| TC-12 | Filtrar por categoría | PASS | Se muestran los equipos de la categoría seleccionada. |
| TC-13 | Confirmar datos | PASS | El resumen muestra los datos ingresados antes de guardar. |

## Evidencias relacionadas

- `EV-01_catalogo.png`
- `EV-02-detalle-equipo.png`
- `EV-03-solicitud.png`
- `EV-04-validacion.png`
- `EV-05-solicitud_creada.png`
- `EV-06-equipo-reservado.png`
- `EV-07-mis-solicitudes.png`
- `EV-08-detalle-solicitud.png`
- `EV-09-cancelacion.png`
- `EV-10-equipo-disponible.png`
- `EV-11-equipo-no-disponible.png`
- `EV-12-equipo-reservado.png`

## Pruebas automatizadas

También se ejecutaron correctamente:

- 18 pruebas de `InMemoryPrestamoRepository`.
- 8 pruebas de `PrestamoViewModel`.
- 26 pruebas unitarias en total.
- Pruebas instrumentadas en dispositivo físico.
- Android Lint.
- GitHub Actions.

## Estado

Pruebas funcionales del incremento registradas.
# Bitácora de ejecución — PréstamoLab CTMA

Este archivo registra únicamente resultados obtenidos mediante ejecuciones reales del proyecto.

| Ejecución | Caso / validación | Entorno | Resultado | Evidencia / observación |
|---|---|---|---|---|
| 1 | Suite de pruebas unitarias | Gradle `testDebugUnitTest` | PASS | 19 pruebas ejecutadas, 19 aprobadas y 0 fallidas. Evidencia: `evidencia_19_tests_unitarios_pass.png` |
| 2 | Catálogo de equipos | Pixel 3a API 34 | PASS | El catálogo muestra nombre, categoría y disponibilidad. Evidencia: `evidencia_catalogo_aplicacion.png` |
| 3 | Navegación al detalle | Pixel 3a API 34 | PASS | Se visualizó correctamente el detalle del equipo mediante su ID. Evidencia: `evidencia_detalle_equipo.png` |
| 4 | Registro de solicitud válida | Pixel 3a API 34 | PASS | Se creó correctamente una solicitud en estado `SOLICITADA`. Evidencia: `evidencia_solicitud_creada.png` |
| 5 | Cambio de disponibilidad | Pixel 3a API 34 | PASS | Después de solicitar el equipo pasó de `DISPONIBLE` a `RESERVADO`. Evidencia: `evidencia_equipo_reservado.png` |
| 6 | Validaciones del formulario | Pixel 3a API 34 | PASS | Se rechazaron destino vacío, propósito de 9 caracteres y duración de 9 horas. Se aceptaron propósito de 10 caracteres y duración de 8 horas. |
| 7 | Prevención de doble guardado | Pixel 3a API 34 | PASS | Una doble pulsación sobre Guardar produjo una sola solicitud; no se generó una solicitud duplicada. |
| 8 | Cancelación de solicitud | Pixel 3a API 34 | PASS | Una solicitud en estado `SOLICITADA` fue cancelada correctamente. Evidencia: `evidencia_solicitud_cancelada.png` |
| 9 | Liberación del equipo | Pixel 3a API 34 | PASS | Después de cancelar, el equipo regresó al estado `DISPONIBLE`. Evidencia: `evidencia_equipo_disponible_tras_cancelacion.png` |
| 10 | Suite de pruebas instrumentadas | Pixel 3a API 34 | PASS | 3 pruebas ejecutadas, 3 aprobadas y 0 fallidas. Evidencia: `evidencia_tests_instrumentados_pass.png` |
| 11 | Android Lint | Gradle `lintDebug` | PASS | Ejecución finalizada con `BUILD SUCCESSFUL`. Reporte generado en `app/build/reports/lint-results-debug.html`. |
| 12 | Integración continua Android CI | GitHub Actions | PASS | Workflow ejecutado correctamente sobre `feat/fernando_zapa`, commit `38d9621`. Evidencia: `evidencia_github_actions_ci_pass.png` |

## Resumen de ejecución

- Pruebas unitarias: **19/19 PASS**
- Pruebas instrumentadas: **3/3 PASS**
- Total de pruebas automatizadas ejecutadas: **22**
- Pruebas manuales principales del incremento: **PASS**
- Android Lint: **PASS**
- GitHub Actions / Android CI: **PASS**

## Observaciones

Las pruebas manuales comprobaron el flujo principal de solicitud y cancelación, las reglas de valores límite ejecutadas durante la validación y la prevención de duplicados.

Los datos utilizados son sintéticos y el repositorio de la aplicación funciona en memoria.

> Integridad: solamente se registran como PASS las pruebas y verificaciones realmente ejecutadas. No se registran pruebas no ejecutadas ni defectos ficticios como evidencia de calidad.
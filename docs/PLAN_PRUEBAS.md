# Plan de pruebas · PréstamoLab CTMA v0.6.0

## Objetivo
Verificar reglas de negocio, persistencia, navegación, integración remota, UI y regresión del flujo crítico.

## Ambiente
Android API 24+, JDK 17, Gradle 8.11.1. Datos sintéticos. Convención de ejecución: PASS / FAIL / BLOCKED.

## Técnicas de caja negra
Partición de equivalencia, valores límite y transición de estados.

## Suite mínima (16 casos)
| ID | HU | Caso / datos | Esperado | Estado de evidencia |
|---|---|---|---|---|
| TC-01 | HU-01 | Abrir catálogo | Lista con estado textual | Automatizable UI |
| TC-02 | HU-02 | Abrir ID válido | Detalle correcto | Manual/UI |
| TC-03 | HU-03 | Destino vacío | Error específico | Unit/manual |
| TC-04 | HU-03 | Propósito 9 chars | Rechazo | Unit |
| TC-05 | HU-03 | Propósito 10/180 chars | Acepta | Unit |
| TC-06 | HU-03 | Propósito 181 chars | Rechazo | Unit |
| TC-07 | HU-03 | Doble Guardar | Una sola solicitud | Manual/integración |
| TC-08 | HU-03 | Equipo RESERVADO | Rechazo | Repository |
| TC-09 | HU-03 | Duración 0/1/8/9 | Rechaza/acepta/acepta/rechaza | Unit |
| TC-10 | HU-02 | ID inexistente | Mensaje recuperable, sin crash | Manual |
| TC-11 | HU-04 | Abrir Mis préstamos | Lista y detalle | UI/manual |
| TC-12 | HU-06 | Crear préstamo y reiniciar app | Datos conservados | Instrumentada/manual |
| TC-13 | HU-04 | Cancelar SOLICITADA | CANCELADA + equipo DISPONIBLE | Repository/manual |
| TC-14 | HU-07 | API JSON válida | DTO parseado | MockWebServer unit |
| TC-15 | HU-08 | Elegir imagen | URI asociada a solicitud | Dispositivo/emulador |
| TC-16 | HU-05 | Registrar devolución | DEVUELTA + equipo DISPONIBLE | Manual/regresión |
| TC-17 | HU-09 | Crear préstamo | Recordatorio programado | Dispositivo |
| TC-18 | HU-10 | Abrir Dispositivo | Batería y almacenamiento visibles | UI instrumentada |

## Bitácora
Los casos con código automatizado quedan definidos en `src/test`/`src/androidTest`. El estado PASS/FAIL final debe registrarse después de ejecutar el build concreto en Android Studio/GitHub Actions; no se marca un PASS ficticio dentro del repositorio.

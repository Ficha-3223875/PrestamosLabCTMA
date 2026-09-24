# Plan de pruebas

## Alcance

Se validan catálogo, detalle, solicitud, persistencia, entrega, devolución, evidencia, sensor, preferencias, sincronización y errores recuperables.

| TC | Escenario | Tipo | Resultado esperado | Automatización |
|---|---|---|---|---|
| TC-01 | Catálogo inicial | Unidad/UI | Muestra equipos | Unidad/UI |
| TC-02 | Equipo válido | Unidad | Recupera detalle | Unidad |
| TC-03 | ID inexistente | Unidad/UI | Mensaje recuperable | Unidad |
| TC-04 a TC-07 | Propósito 9 10 180 181 | Valores límite | Rechaza/acepta según rango | Unidad |
| TC-08 a TC-11 | Duración 0 1 8 9 | Valores límite | Rechaza/acepta según rango | Unidad |
| TC-12 | Equipo no disponible | Unidad | Rechaza solicitud | Unidad |
| TC-13 | Doble guardado | Unidad | Crea una solicitud | Unidad |
| TC-14 | Solicitud válida | Unidad | Reserva equipo | Unidad |
| TC-15 | Cancelación válida | Unidad | Cancela y libera | Unidad |
| TC-16 | Segunda cancelación | Unidad | Rechaza operación | Unidad |
| TC-17 | Navegación catálogo detalle | UI | Abre detalle correcto | Instrumentada |
| TC-18 | Formulario vacío | UI | Muestra validaciones | Instrumentada |
| TC-19 | Ambiente vacío | Unidad | Rechaza | Unidad |
| TC-20 | Solicitud inexistente | Unidad | Devuelve nulo | Unidad |
| TC-21 | Registrar entrega | Unidad | ENTREGADA y PRESTADO | Unidad |
| TC-22 | Devolver sin entrega | Unidad | Rechaza | Unidad |
| TC-23 | Devolución válida | Unidad | DEVUELTA y DISPONIBLE | Unidad |
| TC-24 | Devolución sin foto | Unidad | Rechaza | Unidad |
| TC-25 | API HTTP 200 | Integración | Mapea JSON | MockWebServer |
| TC-26 | API HTTP 500 | Integración | Error recuperable | MockWebServer |
| TC-27 | Reiniciar aplicación | Manual | Conserva solicitudes y estados | Dispositivo |
| TC-28 | Seleccionar foto | Manual/UI | Conserva URI y metadatos | Dispositivo |
| TC-29 | Mover dispositivo | Manual | Verifica acelerómetro | Dispositivo/sensor virtual |
| TC-30 | Permiso notificación denegado | Manual | App sigue operativa | Android 13+ |
| TC-31 | API sin conectividad | Manual/integración | Estado FALLIDA y mensaje | Staging/mock |
| TC-32 | Accesibilidad básica | Manual | Texto legible y acciones etiquetadas | Accessibility Scanner |

## Técnicas

Partición de equivalencia, valores límite, transición de estados, casos negativos, integración HTTP y recorrido de regresión. PASS, FAIL o BLOCKED solo se completa tras ejecución real.

## Regresión crítica

`Catálogo -> Detalle -> Solicitud -> Mis préstamos -> Entrega -> Evidencia -> Devolución -> Equipo disponible`.

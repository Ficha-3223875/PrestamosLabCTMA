# Matriz de Trazabilidad

La matriz relaciona las historias de usuario, criterios verificables, riesgos y casos de prueba definidos para el proyecto PréstamoLab CTMA.

La columna de ejecución únicamente registra ejecuciones que cuentan con evidencia real en la Bitácora de Ejecución. Los casos de prueba que aún no han sido ejecutados permanecen identificados como **No ejecutado en esta entrega**, sin asignarles un resultado PASS, FAIL o BLOCKED.

| Historia | Criterio verificable | Riesgo | Caso(s) | Ejecución | Resultado | Defecto |
|---|---|---|---|---|---|---|
| HU-01 | El catálogo muestra los equipos con nombre, categoría y estado de disponibilidad | R-04 | TC-01 | EX-001 | PASS | - |
| HU-02 | El detalle corresponde al `equipoId` seleccionado y un ID inexistente se maneja sin cierre abrupto | R-03 | TC-02, TC-03 | EX-002, EX-003 | PASS* | - |
| HU-03 | Los datos válidos permiten crear una solicitud en estado `SOLICITADA` y reservar el equipo | R-04 | TC-14 | No ejecutado en esta entrega | — | - |
| HU-04 | El propósito debe contener entre 10 y 180 caracteres | R-02 | TC-04, TC-05, TC-06, TC-07 | EX-004, EX-005; TC-06/TC-07 no ejecutados | PASS* | - |
| HU-04 | La duración debe estar entre 1 y 8 horas | R-02 | TC-08, TC-09, TC-10, TC-11 | No ejecutado en esta entrega | — | - |
| HU-05 | No se permite solicitar equipos que se encuentren `RESERVADO` o `PRESTADO` | R-04 | TC-12 | No ejecutado en esta entrega | — | - |
| HU-06 | Una doble pulsación o intento repetido de guardar no genera solicitudes duplicadas | R-01 | TC-13 | No ejecutado en esta entrega | — | - |
| HU-07 | El usuario puede consultar las solicitudes creadas desde la sección de solicitudes | R-04 | TC-14, TC-15 | No ejecutado en esta entrega | — | - |
| HU-08 | Solo una solicitud en estado `SOLICITADA` puede cancelarse y al hacerlo el equipo vuelve a estar `DISPONIBLE` | R-04 | TC-15, TC-16 | No ejecutado en esta entrega | — | - |
| HU-09 | La navegación entre catálogo, detalle y formulario mantiene un flujo comprensible y permite regresar al destino anterior | R-03 | TC-17 | No ejecutado en esta entrega | — | - |
| HU-10 | La interfaz continúa siendo comprensible y utilizable con fuente aumentada y textos largos | R-05 | TC-18 | No ejecutado en esta entrega | — | - |

\* El resultado PASS se refiere únicamente a la ejecución documentada en la Bitácora y a la evidencia disponible para ese caso.

## Trazabilidad de las ejecuciones documentadas

Las ejecuciones con evidencia disponibles en esta entrega son:

| Ejecución | Caso | Resultado | Evidencia |
|---|---|---|---|
| EX-001 | TC-01 | PASS | `evidencias/TC-01/` |
| EX-002 | TC-02 | PASS | `evidencias/TC-02/` |
| EX-003 | TC-03 | PASS | `evidencias/TC-03/` |
| EX-004 | TC-04 | PASS | `evidencias/TC-04/` |
| EX-005 | TC-05 | PASS | `evidencias/TC-05/` |

## Estado de la trazabilidad

La trazabilidad documental cubre las historias, criterios, riesgos y los 18 casos definidos en la suite de pruebas.

En esta entrega existen evidencias de ejecución para TC-01 a TC-05. Los demás casos permanecen definidos y relacionados con sus respectivas historias y riesgos, pero no se presentan como ejecutados.

No se registran defectos en la matriz porque las ejecuciones documentadas no han producido defectos reproducibles.

## Convención

- **PASS:** el resultado observado coincide con el resultado esperado.
- **FAIL:** existe una discrepancia reproducible entre el resultado esperado y el observado.
- **BLOCKED:** una condición externa o precondición impide completar la ejecución.
- **No ejecutado en esta entrega:** el caso está diseñado y trazado, pero no cuenta con una ejecución documentada en esta entrega.
- **—:** no existe resultado de ejecución que registrar.
- **Defecto:** se registra únicamente cuando existe un defecto real, reproducible y respaldado por evidencia.
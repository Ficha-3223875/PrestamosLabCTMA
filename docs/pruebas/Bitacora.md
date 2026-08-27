# Bitácora de Ejecución

La presente bitácora registra únicamente las ejecuciones que cuentan con evidencia disponible para esta entrega. La suite de pruebas contiene 18 casos de prueba definidos; sin embargo, no todos requieren una evidencia individual en esta fase, de acuerdo con el alcance acordado para la actividad.

| ID ejecución | Caso | Build | Fecha | Resultado | Evidencia | Observación |
|---|---|---|---|---|---|---|
| EX-001 | TC-01 | 0.1.0 | [fecha de ejecución] | PASS | `evidencias/TC-01/` | El catálogo de equipos se visualiza correctamente y muestra la información de disponibilidad. |
| EX-002 | TC-02 | 0.1.0 | [fecha de ejecución] | PASS | `evidencias/TC-02/` | El detalle mostrado corresponde al equipo seleccionado. |
| EX-003 | TC-03 | 0.1.0 | [fecha de ejecución] | PASS | `evidencias/TC-03/` | El identificador inexistente es manejado mediante un estado recuperable sin cierre abrupto de la aplicación. |
| EX-004 | TC-04 | 0.1.0 | [fecha de ejecución] | PASS | `evidencias/TC-04/` | Un propósito con 9 caracteres es rechazado de acuerdo con la regla RN-03. |
| EX-005 | TC-05 | 0.1.0 | [fecha de ejecución] | PASS | `evidencias/TC-05/` | Un propósito con 10 caracteres es aceptado cuando los demás datos son válidos. |

## Resumen de ejecución

- Casos definidos en la suite: **18**
- Casos ejecutados con evidencia en esta entrega: **5**
- Casos con evidencia: **TC-01, TC-02, TC-03, TC-04 y TC-05**
- PASS: **5**
- FAIL: **0**
- BLOCKED: **0**

Los casos TC-06 a TC-18 permanecen definidos en la suite de pruebas, pero no se registran como PASS, FAIL o BLOCKED en esta bitácora debido a que no cuentan con una ejecución documentada mediante evidencia en esta entrega.

## Convención de resultados

- **PASS:** el resultado observado coincide con el resultado esperado del caso de prueba.
- **FAIL:** existe una discrepancia reproducible entre el resultado esperado y el resultado observado.
- **BLOCKED:** una precondición o condición externa impide completar la ejecución del caso.
- **Pendiente de ejecución:** el caso está definido en la suite, pero todavía no cuenta con una ejecución registrada en esta bitácora.

## Integridad de la evidencia

Los resultados registrados en esta bitácora corresponden únicamente a ejecuciones que cuentan con evidencia disponible. Los casos que no han sido ejecutados no se reportan como PASS ni se presentan como resultados de ejecución.

La evidencia se organiza utilizando el identificador correspondiente al caso de prueba para facilitar la trazabilidad entre el caso, la ejecución y el resultado observado.
# Confirmación y Regresión

## 1. Objetivo

Definir el procedimiento que se utilizará para confirmar la corrección de un defecto y comprobar que un cambio realizado no introduzca efectos colaterales en funcionalidades relacionadas.

En esta entrega no se registran actividades de confirmación o regresión como ejecutadas, debido a que no se han identificado defectos reproducibles en las ejecuciones documentadas.

## 2. Procedimiento de confirmación

La confirmación se realizará cuando una ejecución produzca un `FAIL` reproducible y posteriormente se realice una corrección.

El procedimiento será:

1. Registrar el `FAIL` en el Registro de Defectos.
2. Documentar el comportamiento esperado y el comportamiento obtenido.
3. Identificar y corregir la causa del defecto.
4. Generar una nueva versión o build de la aplicación.
5. Repetir el mismo caso de prueba que produjo el `FAIL`.
6. Comparar el resultado obtenido con el resultado esperado.
7. Registrar la nueva ejecución, build, fecha, resultado y evidencia.
8. Si el resultado coincide con lo esperado, registrar la confirmación de la corrección.

## 3. Procedimiento de regresión

Después de corregir un defecto, se ejecutarán nuevamente los casos relacionados con la funcionalidad modificada para verificar que la corrección no afecte otros comportamientos.

El procedimiento será:

1. Identificar los casos relacionados con el cambio realizado.
2. Ejecutar nuevamente dichos casos.
3. Comparar los resultados con los resultados esperados.
4. Registrar cualquier nueva discrepancia como defecto.
5. Documentar el resultado y la evidencia correspondiente.

## 4. Matriz preparada

| Cambio / funcionalidad | Caso de confirmación | Regresión sugerida | Resultado |
|---|---|---|---|
| Prevención de doble guardado | TC-13 | TC-12, TC-14, TC-15 | No ejecutado |
| Cancelación de solicitud | TC-15 | TC-01, TC-02, TC-14, TC-16 | No ejecutado |
| Manejo de navegación mediante ID | TC-03 | TC-02, TC-17 | No ejecutado |
| Validaciones de propósito y duración | TC afectado | TC-04, TC-05, TC-06, TC-07, TC-08, TC-09, TC-10, TC-11 | No ejecutado |

## 5. Estado en esta entrega

No se han registrado defectos reproducibles en las ejecuciones documentadas de TC-01 a TC-05.

Por lo tanto:

- No existe actualmente un defecto que requiera confirmación.
- No se ha realizado una corrección que requiera una regresión formal.
- La matriz anterior queda preparada para utilizarse si durante una ejecución posterior se identifica y corrige un defecto.
- No se asigna `PASS` a actividades de confirmación o regresión que no hayan sido ejecutadas.

## 6. Evidencia

Cuando se realice una confirmación o regresión, la evidencia deberá registrar como mínimo:

- Caso de prueba.
- Build utilizada.
- Fecha de ejecución.
- Resultado.
- Evidencia disponible.
- Referencia al defecto, cuando aplique.

Las evidencias se almacenarán en:

`docs/evidencias/`

## 7. Criterio de resultado

- **PASS:** la corrección fue confirmada mediante una ejecución real y el resultado coincide con el esperado.
- **FAIL:** el defecto continúa reproduciéndose o aparece una nueva discrepancia.
- **BLOCKED:** una condición externa impide completar la confirmación o regresión.
- **No ejecutado:** la actividad está definida, pero no se ha realizado en esta entrega.
# Registro de Defectos

Este registro contiene únicamente defectos identificados a partir de ejecuciones reales de los casos de prueba.

No se inventan defectos ni se registran como FAIL situaciones que no hayan sido observadas y reproducidas durante la ejecución.

## Defectos registrados

| ID | Caso | Build | Severidad | Prioridad | Esperado | Obtenido | Evidencia | Estado |
|---|---|---|---|---|---|---|---|---|
| — | — | — | — | — | — | — | — | Sin defectos reproducibles registrados |

## Resultado de las ejecuciones documentadas

En las cinco ejecuciones que cuentan con evidencia en esta entrega (TC-01, TC-02, TC-03, TC-04 y TC-05), no se registraron discrepancias reproducibles entre el resultado esperado y el resultado observado.

Por lo tanto:

**Conclusión: no se encontraron defectos reproducibles en las ejecuciones documentadas.**

Los casos TC-06 a TC-18 forman parte de la suite de pruebas, pero no se consideran para esta conclusión de defectos debido a que no cuentan con una ejecución documentada en la bitácora de esta entrega.

## Criterio para registrar un defecto

Si durante una ejecución posterior se identifica una discrepancia reproducible, se agregará un registro con:

- **ID:** identificador único del defecto.
- **Caso:** caso de prueba en el que fue encontrado.
- **Build:** versión en la que se reprodujo.
- **Severidad:** impacto del defecto sobre el funcionamiento.
- **Prioridad:** urgencia para su corrección.
- **Esperado:** comportamiento definido por el caso de prueba.
- **Obtenido:** comportamiento observado.
- **Evidencia:** captura, video u otra evidencia disponible.
- **Estado:** abierto, corregido, confirmado, cerrado u otro estado utilizado por el equipo.

Un defecto solo se registrará cuando exista evidencia suficiente para reproducir y comprobar la discrepancia.
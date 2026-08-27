# Matriz de Riesgos

La matriz identifica los principales riesgos funcionales y de calidad del proyecto PréstamoLab CTMA y los relaciona con los casos de prueba diseñados para su cobertura.

| ID | Riesgo | Prob. | Impacto | Nivel | Cobertura |
|---|---|---|---|---|---|
| R-01 | Una doble pulsación o intento repetido de guardar puede generar dos solicitudes activas para el mismo equipo. | Alta | Alta | Crítico | TC-13 |
| R-02 | La aplicación puede aceptar valores de propósito o duración fuera de los límites establecidos. | Alta | Media | Alto | TC-04, TC-05, TC-06, TC-07, TC-08, TC-09, TC-10, TC-11 |
| R-03 | Un identificador de equipo inexistente puede provocar un comportamiento incorrecto o cierre abrupto de la aplicación. | Media | Alta | Alto | TC-03 |
| R-04 | La disponibilidad del equipo puede quedar inconsistente después de crear o cancelar una solicitud, o permitir solicitudes sobre equipos no disponibles. | Media | Alta | Alto | TC-12, TC-14, TC-15, TC-16 |
| R-05 | La interfaz puede perder legibilidad o dificultar el uso de las acciones principales cuando aumenta el tamaño de fuente o se utilizan textos largos. | Media | Media | Medio | TC-18 |

## Priorización

Los riesgos se priorizan según la combinación de probabilidad e impacto:

- **R-01 — Crítico:** afecta directamente la regla central de disponibilidad y puede generar solicitudes duplicadas para un mismo equipo.
- **R-02 — Alto:** una validación incorrecta permitiría registrar solicitudes con datos que incumplen las reglas definidas.
- **R-03 — Alto:** un identificador inexistente no debe provocar el cierre abrupto de la aplicación.
- **R-04 — Alto:** una inconsistencia entre solicitudes y disponibilidad puede permitir reservas incorrectas o mostrar información desactualizada.
- **R-05 — Medio:** afecta principalmente la accesibilidad y facilidad de uso de la aplicación.

## Relación entre riesgos y pruebas

| Riesgo | Casos principales | Objetivo de cobertura |
|---|---|---|
| R-01 | TC-13 | Comprobar que no se creen solicitudes duplicadas para un mismo equipo. |
| R-02 | TC-04 a TC-11 | Comprobar los valores límite de propósito y duración. |
| R-03 | TC-03 | Comprobar el manejo de un `equipoId` inexistente. |
| R-04 | TC-12, TC-14, TC-15, TC-16 | Comprobar disponibilidad, creación, cancelación y consistencia del estado del equipo. |
| R-05 | TC-18 | Comprobar que la interfaz continúe siendo utilizable con fuente aumentada y textos largos. |

## Estado de cobertura

Los riesgos están relacionados con casos de prueba definidos en la suite de 18 casos.

La cobertura indica qué casos están diseñados para tratar cada riesgo. La ejecución efectiva de cada caso se registra por separado en la **Bitácora de Ejecución** y no se considera PASS únicamente por estar incluido en esta matriz.

En esta entrega se cuenta con evidencia de ejecución para TC-01 a TC-05. Por lo tanto, la existencia de cobertura documental de un riesgo no significa que todas sus pruebas hayan sido ejecutadas.
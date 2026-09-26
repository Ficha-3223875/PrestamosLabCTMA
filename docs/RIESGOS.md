# Matriz de riesgos — PréstamoLab CTMA

## Propósito

Identificar los riesgos funcionales y de calidad del incremento de PréstamoLab CTMA y relacionarlos con las pruebas realizadas.

| ID | Riesgo | Probabilidad | Impacto | Nivel | Cobertura |
|---|---|---|---|---|---|
| R-01 | Una doble pulsación de Guardar genera solicitudes duplicadas para el mismo equipo. | Alta | Alta | Crítico | Pruebas de Repository y ViewModel sobre duplicación y guardado. |
| R-02 | Se permite registrar una solicitud con datos inválidos de propósito o duración. | Alta | Alta | Alto | Pruebas de validación y valores límite. |
| R-03 | Un `equipoId` inexistente provoca un cierre inesperado de la aplicación. | Media | Alta | Alto | Pruebas de consulta de equipo inexistente y evidencia de UI. |
| R-04 | El catálogo no refleja correctamente el cambio de disponibilidad después de crear o cancelar una solicitud. | Media | Alta | Alto | Pruebas de creación/cancelación y actualización del estado del equipo. |
| R-05 | Se intenta solicitar un equipo que no está disponible. | Media | Alta | Alto | Pruebas de equipos RESERVADOS/PRESTADOS y validación del Repository. |

## Trazabilidad de riesgos

- R-01 → prevención de solicitudes duplicadas.
- R-02 → validación de propósito y duración.
- R-03 → manejo de identificadores inexistentes.
- R-04 → consistencia entre solicitudes y disponibilidad.
- R-05 → validación de disponibilidad antes de crear una solicitud.

## Estado

Los riesgos fueron considerados durante el diseño, implementación y ejecución de pruebas del incremento.
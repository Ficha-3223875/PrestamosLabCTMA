# Gestión Scrum e incrementos

## Product Backlog refinado

| ID | Historia | Prioridad | Puntos | Estado |
|---|---|---:|---:|---|
| HU-01 | Consultar equipos disponibles | Alta | 3 | Hecha |
| HU-02 | Consultar detalle de equipo | Alta | 2 | Hecha |
| HU-03 | Solicitar préstamo | Alta | 5 | Hecha |
| HU-04 | Consultar mis préstamos | Alta | 3 | Hecha |
| HU-05 | Registrar devolución | Alta | 5 | Hecha |
| HU-06 | Conservar datos sin conexión | Alta | 8 | Hecha |
| HU-07 | Sincronizar con servicio remoto | Media alta | 8 | Endpoint configurable |
| HU-08 | Adjuntar evidencia fotográfica | Media | 5 | Hecha |
| HU-09 | Recibir recordatorio | Media | 3 | Hecha |
| HU-10 | Verificar acelerómetro | Media | 3 | Hecha |

## Priorización MoSCoW

- Must: HU-01 a HU-06 y HU-08.
- Should: HU-07, HU-09 y HU-10.
- Could: autenticación institucional.
- Won't: backend productivo y datos reales del CTMA en este incremento.

## Sprint Goal

Evolucionar el prototipo a un incremento persistente, reactivo y seguro que complete solicitud, entrega y devolución con evidencia, manteniendo trazabilidad y controles automáticos.

## Definition of Done

- Criterios verificables y trazados.
- UI separada de Room y Retrofit.
- Reglas cubiertas por pruebas.
- Build, tests y lint configurados como gates.
- Errores recuperables y sin cierres abruptos.
- Sin secretos ni datos sensibles.
- Documentación y limitaciones actualizadas.

## Métricas

Para no inventar resultados, velocidad, lead time y cycle time deben completarse con fechas reales de Issues y PR: `HU | Inicio | PR abierto | Merge | Lead time | Cycle time`.

## Review y Retrospective

En Review se demuestra catálogo, persistencia, entrega, evidencia, acelerómetro, devolución y sincronización con mock. Mantener la separación por capas; mejorar la división de pantallas y disponer de backend staging; exigir evidencia del pipeline antes del merge.

# Sprint Review — PréstamoLab CTMA

## Objetivo

Revisar el incremento desarrollado y verificar que las funcionalidades implementadas respondan a las necesidades definidas para el Sprint.

## Incremento presentado

Durante la revisión se presentó el flujo principal de PréstamoLab CTMA:

1. Consulta del catálogo de equipos.
2. Búsqueda y filtro por categoría.
3. Consulta del detalle de un equipo.
4. Verificación de disponibilidad.
5. Registro de una solicitud de préstamo.
6. Confirmación de los datos antes de registrar.
7. Consulta de las solicitudes del usuario.
8. Consulta del detalle de una solicitud.
9. Cancelación de una solicitud en estado `SOLICITADA`.
10. Manejo de equipos o solicitudes inexistentes.

## Validaciones realizadas

- Los criterios de aceptación de las funcionalidades implementadas fueron revisados.
- Se ejecutaron 26 pruebas unitarias.
- Se ejecutaron pruebas instrumentadas en dispositivo físico.
- Android Lint finalizó correctamente.
- GitHub Actions ejecutó los controles automatizados configurados.
- Se revisaron las evidencias funcionales almacenadas en `docs`.

## Evidencias

Entre las evidencias utilizadas se encuentran:

- `EV-01_catalogo.png`
- `EV-02-detalle-equipo.png`
- `EV-03-solicitud.png`
- `EV-04-validacion.png`
- `EV-05-solicitud_creada.png`
- `EV-07-mis-solicitudes.png`
- `EV-08-detalle-solicitud.png`
- `EV-09-cancelacion.png`

## Resultado

El incremento actual queda documentado y validado para las funcionalidades implementadas.

Las funcionalidades de persistencia con Room, sincronización remota, devolución, evidencia fotográfica y recordatorios quedan previstas para incrementos posteriores.

## Estado

Review documentada.
# Sprint Backlog — PréstamoLab CTMA

## Sprint

Incremento de Semana 5.

## Objetivo

Consolidar las funcionalidades principales de consulta y solicitud de préstamos, validar su comportamiento y dejar evidencia técnica y funcional.

## Historias de Usuario

| HU | Historia de Usuario | Estado |
|---|---|---|
| HU-01 | Consultar catálogo | Done |
| HU-02 | Consultar detalle | Done |
| HU-03 | Registrar solicitud | Done |
| HU-04 | Consultar mis solicitudes | Done |
| HU-05 | Registrar devolución | Pendiente de incremento futuro |
| HU-06 | Conservar datos localmente | Pendiente de incremento futuro |
| HU-07 | Sincronizar datos remoto | Pendiente de incremento futuro |
| HU-08 | Adjuntar evidencia fotográfica | Pendiente de incremento futuro |
| HU-09 | Recordatorio de devolución | Pendiente de incremento futuro |
| HU-10 | Información del usuario | Done |
| HU-11 | Buscar equipos | Done |
| HU-12 | Filtrar por categoría | Done |
| HU-13 | Confirmar datos antes de registrar | Done |

## Tareas técnicas

- [x] Consolidar modelos de dominio.
- [x] Implementar Repository en memoria.
- [x] Implementar ViewModel.
- [x] Implementar `UiState` con `StateFlow`.
- [x] Implementar navegación entre pantallas.
- [x] Implementar catálogo.
- [x] Implementar detalle de equipo.
- [x] Implementar solicitud de préstamo.
- [x] Implementar mis solicitudes.
- [x] Implementar cancelación.
- [x] Implementar búsqueda.
- [x] Implementar filtro por categoría.
- [x] Implementar validaciones.
- [x] Implementar manejo de IDs inexistentes.
- [x] Crear pruebas unitarias.
- [x] Ejecutar Android Lint.
- [x] Configurar GitHub Actions.
- [x] Documentar riesgos.
- [x] Documentar plan de pruebas.
- [x] Crear matriz de trazabilidad.
- [x] Registrar resultados de pruebas manuales.

## Tareas pendientes para incrementos posteriores

- [ ] Migrar persistencia a Room.
- [ ] Incorporar DataStore.
- [ ] Incorporar Coroutines y Flow donde corresponda.
- [ ] Integrar API REST mediante Retrofit y OkHttp.
- [ ] Implementar sincronización local/remota.
- [ ] Incorporar devolución del préstamo.
- [ ] Incorporar evidencia fotográfica.
- [ ] Incorporar recordatorios de devolución.
- [ ] Incorporar una capacidad física adicional del dispositivo.

## Estado

Sprint documentado y funcionalidades actuales estabilizadas.
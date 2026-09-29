# Matriz de trazabilidad — PréstamoLab CTMA

## Objetivo

Relacionar las Historias de Usuario con sus criterios de aceptación, riesgos, casos de prueba, implementación y evidencias.

| HU | Criterio / funcionalidad | Riesgo | Caso de prueba | Implementación | Evidencia |
|---|---|---|---|---|---|
| HU-01 | Consultar catálogo de equipos | R-04 | TC-01 | Catálogo + Repository | EV-01_catalogo.png |
| HU-02 | Consultar detalle mediante `equipoId` | R-03 | TC-02, TC-03 | Navegación + EquipoDetalleScreen | EV-02-detalle-equipo.png |
| HU-03 | Registrar solicitud para equipo disponible | R-05 | TC-04, TC-05 | SolicitudScreen + Repository | EV-03-solicitud.png |
| HU-04 | Consultar solicitudes propias | R-04 | TC-09 | MisSolicitudesScreen | EV-07-mis-solicitudes.png |
| HU-05 | Registrar devolución | R-04 | TC-10 | Gestión de estado de solicitud | Evidencia funcional correspondiente |
| HU-06 | Conservar datos localmente | R-04 | TC-09 | Persistencia local | Evidencia de persistencia |
| HU-07 | Sincronizar datos con servicio remoto | R-04 | TC-04 | Integración remota | Evidencia de API |
| HU-08 | Adjuntar evidencia fotográfica | R-04 | TC-10 | Evidencia del préstamo | Evidencia fotográfica |
| HU-09 | Recibir recordatorio de devolución | R-04 | TC-10 | Notificaciones | Evidencia de notificación |
| HU-10 | Mostrar información del usuario | R-02 | TC-01 | Usuario + UI | Evidencia de información |
| HU-11 | Buscar equipos | R-04 | TC-11 | Búsqueda en catálogo | Evidencia de búsqueda |
| HU-12 | Filtrar por categoría | R-04 | TC-12 | Filtro de categoría | Evidencia de filtro |
| HU-13 | Confirmar datos antes de registrar | R-02 | TC-13 | Confirmación de solicitud | Evidencia de confirmación |

## Relación con implementación

Las funcionalidades se implementan mediante:

- `InMemoryPrestamoRepository`
- `PrestamoViewModel`
- `PrestamoUiState`
- `CatalogoScreen`
- `EquipoDetalleScreen`
- `SolicitudScreen`
- `MisSolicitudesScreen`
- `SolicitudDetalleScreen`
- Navegación mediante argumentos `equipoId` y `solicitudId`.

## Pruebas automatizadas

La implementación cuenta con:

- 18 pruebas del `InMemoryPrestamoRepository`.
- 8 pruebas del `PrestamoViewModel`.
- 26 pruebas unitarias en total.
- Pruebas instrumentadas de Android.
- Android Lint.
- GitHub Actions.

## Trazabilidad del cambio

El flujo de trabajo utilizado es:

HU → Criterios de aceptación → Riesgo → Caso de prueba → Código → PR → GitHub Actions → Evidencia

## Estado

Matriz correspondiente al incremento de Semana 5.
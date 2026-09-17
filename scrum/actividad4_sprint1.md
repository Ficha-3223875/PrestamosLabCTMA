# Actividad 4 — Sprint Planning · Sprint 1
## Sprint Goal
Permitir consultar un equipo disponible y registrar una solicitud de préstamo válida, manteniendo la disponibilidad coherente y demostrando su calidad mediante pruebas reproducibles.

## Sprint Backlog
| PBI | Trabajo del incremento | Estado esperado |
|---|---|---|
| PB-01 | Catálogo y disponibilidad | Done al cumplir DoD |
| PB-02 | Detalle por equipoId | Done al cumplir DoD |
| PB-03 | Registrar solicitud | Done al cumplir DoD |
| PB-04 | Validaciones | Done al cumplir DoD |
| PB-05 | Bloquear equipo no disponible | Done al cumplir DoD |
| PB-06 | Evitar duplicación | Done al cumplir DoD |
| PB-07/08 | Lista y detalle de solicitudes | Done al cumplir DoD |
| PB-09 | Cancelación de SOLICITADA | Done al cumplir DoD |
| PB-10/11/12 | Errores, accesibilidad y documentación | Done al cumplir DoD |

## Plan
1. Modelar Equipo, SolicitudPrestamo y estados.
2. Implementar Repository/InMemoryRepository como fuente única.
3. Implementar ViewModel/StateFlow y validaciones desacopladas.
4. Construir pantallas Compose y navegación por IDs.
5. Implementar reglas de reserva/cancelación y anti-duplicación.
6. Diseñar y ejecutar pruebas; registrar resultados reales.
7. Corregir defectos, confirmar y ejecutar regresión.
8. Actualizar README, trazabilidad y evidencia.

## Definition of Done
- [ ] Compila y ejecuta en el ambiente definido.
- [ ] Criterios seleccionados implementados.
- [x] UI no modifica directamente el Repository.
- [x] ViewModel expone StateFlow de solo lectura.
- [x] Navegación transporta IDs y la UI maneja IDs inexistentes.
- [ ] Casos acordados ejecutados con resultados reales.
- [ ] Defectos críticos/altos tienen decisión explícita.
- [ ] Correcciones relevantes tienen confirmación/regresión cuando aplique.
- [x] README, Scrum, plan y trazabilidad están actualizados en el proyecto.
- [ ] Incremento demostrado y explicado por cada integrante.

Los ítems dependientes de ejecución/evidencia se marcan solo después de comprobarlos; no se declara Done por anticipado.

# Matriz de trazabilidad

| HU | Criterio principal | Riesgo | Pruebas | Código |
|---|---|---|---|---|
| HU-01 | Ver catálogo y disponibilidad | R-02 | TC-01 TC-27 | `CatalogoScreen`, `PrestamoDao` |
| HU-02 | Detalle y error de ID | R-05 | TC-02 TC-03 TC-17 | `EquipoDetalleScreen` |
| HU-03 | Validar y evitar duplicado | R-01 R-03 | TC-04 a TC-14 TC-18 TC-19 | `PrestamoViewModel`, `RoomPrestamoRepository` |
| HU-04 | Listar y abrir préstamos | R-05 | TC-20 | `MisSolicitudesScreen` |
| HU-05 | Entregar y devolver préstamo activo | R-03 | TC-21 TC-22 TC-23 | `registrarEntrega`, `registrarDevolucion` |
| HU-06 | Persistencia offline | R-02 | TC-27 | `PrestamoDatabase`, `PrestamoDao` |
| HU-07 | Sincronizar y manejar error | R-04 R-08 R-10 | TC-25 TC-26 TC-31 | `PrestamoApi`, `sincronizar` |
| HU-08 | Foto por URI | R-06 R-07 | TC-24 TC-28 | `OpenDocument`, `SolicitudEntity` |
| HU-09 | Recordatorio bajo permiso | R-07 | TC-30 | `PreferenciasScreen`, canal `devoluciones` |
| HU-10 | Usar acelerómetro sin permiso | R-07 | TC-29 | `VerificacionAcelerometro` |

Los Issues y PR deben conservar estos mismos identificadores para completar la ruta HU -> criterio -> riesgo -> prueba -> código -> resultado -> defecto.

# Matriz de riesgos

| ID | Riesgo | Probabilidad | Impacto | Control | HU |
|---|---|---|---|---|---|
| R-01 | Doble reserva | Media | Alta | Validación transaccional | HU-03 |
| R-02 | Pérdida al cerrar | Alta | Alta | Room canónico | HU-06 |
| R-03 | Inconsistencia equipo solicitud | Media | Alta | Transacciones y FK | HU-03 HU-05 |
| R-04 | API no disponible | Alta | Media | Timeouts, local-first y FALLIDA | HU-07 |
| R-05 | ID inexistente cierra app | Media | Media | Estado recuperable | HU-02 HU-04 |
| R-06 | Imagen pesada en BD | Media | Alta | URI y metadatos | HU-08 |
| R-07 | Permisos excesivos | Media | Alta | Picker, sensor sin permiso y notificación bajo demanda | HU-08 HU-09 HU-10 |
| R-08 | Exposición de datos | Baja | Alta | HTTPS y logs redactados | HU-07 |
| R-09 | Test UI inestable | Media | Media | Tags y datos controlados | Todas |
| R-10 | Backend demo ausente | Alta | Media | MockWebServer y URL configurable | HU-07 |

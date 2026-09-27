# Product Goal y Product Backlog

## Product Goal
Facilitar la consulta, solicitud, seguimiento y devolución trazable de equipos y herramientas de formación del CTMA mediante una aplicación Android segura, verificable y preparada para datos locales y servicios remotos.

| ID | Historia de Usuario | Prioridad | Estado código |
|---|---|---:|---|
| HU-01 | Como solicitante quiero consultar equipos disponibles para elegir un recurso. | Alta | Implementada |
| HU-02 | Quiero consultar el detalle de un equipo por ID para conocer su información y estado. | Alta | Implementada |
| HU-03 | Quiero solicitar un préstamo indicando destino, propósito y duración. | Alta | Implementada |
| HU-04 | Quiero consultar mis préstamos para conocer su estado. | Alta | Implementada |
| HU-05 | Quiero registrar una devolución para liberar el equipo. | Alta | Implementada |
| HU-06 | Quiero conservar los datos localmente sin conexión. | Alta | Implementada con Room |
| HU-07 | Quiero sincronizar el catálogo con un servicio remoto. | Media/Alta | Retrofit implementado + mock activo |
| HU-08 | Quiero adjuntar evidencia fotográfica a una solicitud. | Media | Implementada con Photo Picker/URI |
| HU-09 | Quiero recibir un recordatorio de devolución. | Media | Implementada con AlarmManager/notificación |
| HU-10 | Quiero conocer batería/almacenamiento para validar capacidad del dispositivo. | Media | Implementada |

## Criterios de aceptación clave
- CA-01 catálogo muestra nombre, categoría y estado textual.
- CA-02 navegación transporta `equipmentId` y un ID inexistente no cierra la app.
- CA-03 solo `DISPONIBLE` permite solicitud.
- CA-04 destino no puede estar vacío.
- CA-05 propósito: 10–180 caracteres.
- CA-06 duración: 1–8 horas.
- CA-07 doble pulsación no crea dos solicitudes.
- CA-08 una solicitud activa reserva el equipo.
- CA-09 solo `SOLICITADA` puede cancelarse.
- CA-10 devolución libera el equipo.
- CA-11 reiniciar la app conserva Room.
- CA-12 evidencia se guarda como URI/metadatos.

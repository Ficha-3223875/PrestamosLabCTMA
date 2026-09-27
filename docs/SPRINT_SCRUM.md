# Scrum del proyecto

## Sprint Goal
Permitir que un usuario consulte un equipo, registre y gestione una solicitud válida, conserve el estado localmente y disponga de evidencia de calidad automatizable.

## Sprint Backlog
1. Consolidar catálogo/detalle/navegación.
2. Implementar formulario y validaciones desacopladas.
3. Implementar Mis préstamos, cancelación y devolución.
4. Reemplazar memoria por Room y agregar DataStore.
5. Convertir consultas a Flow/StateFlow.
6. Preparar Retrofit/OkHttp y prueba con MockWebServer.
7. Agregar Photo Picker, URI y recordatorio.
8. Agregar capacidad física de energía/almacenamiento.
9. Construir pruebas, matrices, CI y documentación.

## Definition of Done
- El proyecto sincroniza y compila en el ambiente Android definido.
- Criterios seleccionados implementados.
- UI no accede directamente a Room/Retrofit.
- ViewModel expone StateFlow de solo lectura.
- Navegación usa identificadores y maneja IDs inexistentes.
- Validaciones son reutilizables y probables.
- Pruebas unitarias/Lint/build se ejecutan en CI.
- Correcciones relevantes se acompañan con regresión.
- README y documentación actualizados.
- Sin secretos ni datos personales reales.

## Review
Demostrar: catálogo → detalle → solicitud válida; caso negativo; Mis préstamos; cancelación/devolución; persistencia; evidencia fotográfica; sincronización; capacidad de dispositivo.

## Retrospective
Acción concreta: preparar los datos y estados de prueba desde Sprint Planning y ejecutar los casos de límites antes de integrar cada PR para reducir fallos tardíos.

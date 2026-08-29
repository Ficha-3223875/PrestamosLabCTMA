# Product Backlog — PréstamoLab CTMA

> Las siguientes historias son una propuesta inicial para convertir los requisitos funcionales de la app en Issues. La guía no fija el texto exacto de las historias; por eso deben validarse con el Product Owner antes de crearlas en GitHub.

## HU-01 — Consultar equipos
**Como** aprendiz, **quiero** consultar el listado de equipos y herramientas, **para** conocer qué recursos existen.

**Criterios de aceptación**
- Se muestra el listado de equipos.
- Cada elemento muestra nombre, descripción y disponibilidad.

## HU-02 — Consultar detalle
**Como** aprendiz, **quiero** ver el detalle de un equipo, **para** conocer su información antes de solicitarlo.

**Criterios de aceptación**
- Al seleccionar un equipo se abre su detalle.
- Se muestra el estado de disponibilidad.

## HU-03 — Solicitar préstamo
**Como** aprendiz, **quiero** solicitar el préstamo de un equipo disponible, **para** utilizarlo en mi formación.

**Criterios de aceptación**
- El botón de solicitud está habilitado cuando el equipo está disponible.
- Al solicitarlo se confirma la operación.
- El equipo pasa a estado no disponible.

## HU-04 — Impedir préstamo no disponible
**Como** sistema, **quiero** impedir solicitudes sobre equipos no disponibles, **para** evitar préstamos inválidos.

**Criterios de aceptación**
- Un equipo no disponible no puede solicitarse.
- El repositorio rechaza una segunda solicitud del mismo equipo.

## HU-05 — Mantener estado durante la navegación
**Como** usuario, **quiero** conservar el equipo seleccionado y su estado al navegar entre pantallas, **para** tener una experiencia coherente.

**Criterios de aceptación**
- La navegación lleva del listado al detalle.
- El detalle corresponde al equipo seleccionado.
- Después de solicitar, el estado actualizado se refleja en pantalla.

## HU-06 — Verificar calidad automáticamente
**Como** equipo de desarrollo, **quiero** ejecutar compilación, pruebas y análisis de calidad automáticamente, **para** detectar errores antes de integrar cambios.

**Criterios de aceptación**
- GitHub Actions ejecuta `assembleDebug`.
- GitHub Actions ejecuta `testDebugUnitTest`.
- GitHub Actions ejecuta `lintDebug`.
- Se conserva el APK como artifact.

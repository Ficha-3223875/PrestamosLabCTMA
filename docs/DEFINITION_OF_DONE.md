# Definition of Done — PréstamoLab CTMA

## Propósito

Definir las condiciones que debe cumplir una Historia de Usuario o incremento para considerarse terminado.

## Criterios técnicos

- [x] El código compila correctamente.
- [x] La funcionalidad está integrada en la aplicación.
- [x] Se respeta la arquitectura definida.
- [x] La interfaz utiliza Compose.
- [x] La lógica de negocio se encuentra en el ViewModel y Repository.
- [x] La UI no accede directamente al Repository.
- [x] Se utilizan estados mediante `UiState` y `StateFlow`.
- [x] La navegación funciona con los parámetros requeridos.
- [x] Se manejan identificadores inexistentes sin cierre inesperado.

## Criterios funcionales

- [x] Los criterios de aceptación de la HU están implementados.
- [x] Las validaciones impiden datos inválidos.
- [x] Los equipos no disponibles no pueden ser solicitados.
- [x] Se evita el registro duplicado por doble acción.
- [x] Las solicitudes pueden consultarse.
- [x] Las solicitudes en estado `SOLICITADA` pueden cancelarse.
- [x] La disponibilidad del equipo se actualiza después de una solicitud o cancelación.

## Criterios de pruebas

- [x] Existen pruebas unitarias para las reglas principales.
- [x] Las 26 pruebas unitarias ejecutadas son exitosas.
- [x] Se ejecutaron pruebas instrumentadas.
- [x] Android Lint finaliza correctamente.
- [x] Se registraron pruebas funcionales.
- [x] Existen evidencias de las funcionalidades implementadas.
- [x] GitHub Actions ejecuta automáticamente los controles configurados.

## Criterios de documentación

- [x] Las Historias de Usuario están registradas.
- [x] Los criterios de aceptación están documentados.
- [x] Los riesgos están documentados.
- [x] Existe un plan de pruebas.
- [x] Existe una matriz de trazabilidad.
- [x] Existe una bitácora de pruebas manuales.
- [x] Existe Sprint Goal.
- [x] Existe Sprint Backlog.
- [x] Existe documentación del DoD.

## Control de cambios

Los cambios importantes se realizan mediante commits y Pull Requests, manteniendo trazabilidad entre la funcionalidad, el código, las pruebas y las evidencias.

## Criterio final

Una HU se considera Done cuando cumple sus criterios de aceptación, pasa las pruebas correspondientes, no presenta defectos conocidos que impidan su uso y cuenta con la documentación y evidencia requerida.

## Estado

Definition of Done establecida para el incremento.
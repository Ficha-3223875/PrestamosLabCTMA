# Informe ejecutivo de calidad — PréstamoLab CTMA

## Alcance construido
El incremento desarrollado permite consultar el catálogo de equipos, visualizar el detalle mediante `equipoId`, registrar solicitudes de préstamo, aplicar validaciones de negocio, controlar la disponibilidad de los equipos, consultar las solicitudes realizadas y cancelar solicitudes en estado `SOLICITADA`.

La solución utiliza Kotlin y Jetpack Compose con una arquitectura basada en UI → ViewModel/StateFlow → Repository → InMemoryRepository.

## Resultados de las pruebas

La aplicación fue sometida a pruebas automatizadas y manuales sobre el incremento desarrollado.

### Pruebas unitarias
Se ejecutó la tarea:

`./gradlew testDebugUnitTest`

Resultado obtenido:

- 19 pruebas ejecutadas.
- 19 pruebas aprobadas.
- 0 pruebas fallidas.
- Estado: PASS.

Las pruebas incluyen validaciones de valores límite para propósito y duración, consulta de equipos, IDs inexistentes, disponibilidad, creación de solicitudes, prevención de duplicados y cancelación.

### Pruebas instrumentadas
Se ejecutaron las pruebas instrumentadas utilizando un emulador Android Pixel 3a API 34.

Resultado obtenido:

- 3 pruebas ejecutadas.
- 3 pruebas aprobadas.
- 0 pruebas fallidas.
- Estado: PASS.

Se verificaron aspectos relacionados con el contexto de la aplicación, navegación entre catálogo y detalle y presentación de validaciones en la interfaz.

### Pruebas manuales
Durante la ejecución manual se verificó:

- Visualización correcta del catálogo.
- Navegación al detalle de un equipo.
- Registro de una solicitud válida.
- Cambio del equipo de `DISPONIBLE` a `RESERVADO`.
- Rechazo de formularios con datos inválidos.
- Validación del propósito entre 10 y 180 caracteres.
- Validación de duración entre 1 y 8 horas.
- Prevención de solicitudes duplicadas mediante doble pulsación del botón Guardar.
- Consulta de solicitudes registradas.
- Cancelación de una solicitud en estado `SOLICITADA`.
- Retorno del equipo a estado `DISPONIBLE` después de la cancelación.

## Validación técnica adicional

Se ejecutó:

`./gradlew lintDebug`

La tarea finalizó correctamente con `BUILD SUCCESSFUL`.

También se ejecutó el flujo de integración continua Android CI mediante GitHub Actions sobre la rama `feat/fernando_zapa`, correspondiente al commit `38d9621`.

El workflow finalizó correctamente.

## Evidencias
Las capturas obtenidas durante las ejecuciones se encuentran almacenadas en `evidencias/pruebas/`.

Entre ellas se incluyen evidencias de:

- 19 pruebas unitarias aprobadas.
- Ejecución de la aplicación y catálogo.
- Detalle del equipo.
- Creación de solicitud.
- Equipo en estado `RESERVADO`.
- Cancelación de solicitud.
- Retorno del equipo a `DISPONIBLE`.
- 3 pruebas instrumentadas aprobadas.
- Ejecución satisfactoria de GitHub Actions.

## Defectos
No se registran defectos abiertos derivados de las validaciones realizadas para este cierre. No se documentan defectos ficticios ni pruebas no ejecutadas como aprobadas.

## Riesgos residuales
La persistencia permanente no forma parte del incremento actual, debido a que se utiliza un repositorio en memoria. Los datos se reinician al finalizar el proceso de la aplicación.

Las pruebas instrumentadas requieren un emulador o dispositivo Android disponible.

## Limitaciones
- Datos sintéticos.
- Persistencia únicamente en memoria.
- Sin autenticación.
- Sin integración con un inventario institucional real.

## Definition of Done
El incremento cuenta con implementación funcional, pruebas unitarias, pruebas instrumentadas, validaciones manuales, análisis mediante Android Lint, evidencias de ejecución y validación mediante integración continua.

Los resultados obtenidos corresponden a ejecuciones reales realizadas sobre el proyecto.

## Decisión de calidad
Con base en las pruebas ejecutadas y las evidencias obtenidas, el incremento cumple las verificaciones documentadas para el alcance implementado. Los riesgos y limitaciones residuales se mantienen identificados en la documentación del proyecto.
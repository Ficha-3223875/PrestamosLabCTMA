# PréstamoLab CTMA — Android

Prototipo educativo Android para consultar equipos/herramientas de formación, solicitar préstamos y seguir su estado durante la ejecución. Implementado en Kotlin + Jetpack Compose con arquitectura UI → ViewModel/StateFlow → Repository → InMemoryRepository.

## Alcance del incremento
- Catálogo con nombre, categoría y disponibilidad.
- Detalle por `equipoId` y manejo recuperable de IDs inexistentes.
- Solicitud con destino, propósito (10–180 caracteres) y duración (1–8 horas).
- Solo equipos `DISPONIBLE` pueden reservarse; una solicitud válida cambia el equipo a `RESERVADO`.
- Prevención de duplicados por doble guardado.
- “Mis solicitudes”, detalle por `solicitudId` y cancelación de estado `SOLICITADA`.
- Cancelar libera el equipo y lo devuelve a `DISPONIBLE`.
- Mensajes textuales de estado/error para no depender únicamente del color.

## Arquitectura
`ui/` representa estado y emite eventos. `PrestamoViewModel` contiene validaciones/coordinación. `PrestamoRepository` define el contrato. `InMemoryPrestamoRepository` es la fuente de verdad simulada y compartida. `navigation/` transporta únicamente IDs.

## Ejecutar
1. Abrir la carpeta raíz en Android Studio.
2. Sincronizar Gradle (wrapper 9.3.1).
3. Seleccionar emulador/dispositivo con Android 7.0 (API 24) o superior.
4. Ejecutar `app`.

## Pruebas
- Unitarias: `./gradlew testDebugUnitTest`
- Lint: `./gradlew lintDebug`
- APK: `./gradlew assembleDebug`
- Instrumentadas (requieren emulador/dispositivo): `./gradlew connectedDebugAndroidTest`

La suite unitaria cubre catálogo, IDs, valores límite 9/10/180/181, duración 0/1/8/9, equipo no disponible, duplicación, creación/cambio de estado y cancelación. Las instrumentadas cubren navegación y validaciones visibles.

## Datos y limitaciones
Todos los datos son sintéticos. El repositorio es en memoria: los cambios se reinician al cerrar el proceso. No hay autenticación ni integración con inventarios institucionales; es un prototipo educativo del primer incremento.

## Evidencias y Scrum
Consultar `scrum/`, `pruebas/` y `evidencias/`. Los resultados PASS/FAIL deben corresponder a ejecuciones reales; no se documentan pruebas no ejecutadas como PASS.

# PréstamoLab CTMA · v0.6.0

Proyecto integrador Android del SENA CTMA para consulta, solicitud, seguimiento y devolución de equipos/herramientas de formación. Esta versión evoluciona el incremento inicial y reúne las actividades de las guías integradoras: Scrum + Android + pruebas + Git/GitHub.

## Funcionalidades
- Catálogo de equipos con nombre, categoría, descripción y estado.
- Detalle por `equipmentId`; un ID inexistente se maneja sin cierre abrupto.
- Formulario de solicitud con ambiente/destino, propósito y duración.
- Reglas: destino obligatorio, propósito 10–180 caracteres y duración 1–8 horas.
- Prevención de doble guardado y de solicitudes activas duplicadas.
- Pantalla **Mis préstamos** y detalle de solicitud.
- Cancelación solo en estado `SOLICITADA`.
- Registro de devolución y liberación del equipo.
- Evidencia fotográfica mediante Photo Picker; se persiste la URI, no Bitmap/Base64.
- Persistencia local con Room y preferencias con DataStore.
- Estado reactivo con Flow/StateFlow y `collectAsStateWithLifecycle`.
- Capa remota Retrofit/OkHttp y origen remoto simulado por defecto para que el prototipo funcione sin Internet.
- Recordatorio de devolución con notificación/AlarmManager.
- Capacidad física adicional: consulta de batería y almacenamiento libre del dispositivo.
- GitHub Actions: build, pruebas unitarias, Lint y APK como artifact.

## Arquitectura
```text
Compose UI
   ↓ eventos / ↑ UiState
ViewModel + StateFlow
   ↓
Repository
   ├─ Room (fuente local canónica)
   ├─ DataStore (preferencias)
   └─ RemoteDataSource
        ├─ FakeRemoteDataSource (activo en el prototipo)
        └─ Retrofit/OkHttp (implementado para integración real)
```

## Rutas principales
`home` → `detail/{equipmentId}` → `request/{equipmentId}`

`home` → `loans` → `loan/{loanId}`

`home` → `device`

## Ejecutar
1. Abrir esta carpeta en Android Studio.
2. Sincronizar Gradle.
3. Ejecutar en emulador/dispositivo con API 24 o superior.
4. Para pruebas: `./gradlew testDebugUnitTest`, `./gradlew connectedDebugAndroidTest` y `./gradlew lintDebug`.

> El proyecto usa Gradle Wrapper 8.11.1, JDK 17, compile/target SDK 35.

## Datos y privacidad
Todos los datos del catálogo son sintéticos. No se deben registrar nombres, documentos, credenciales o datos institucionales reales. La app usa Photo Picker y guarda URI/metadatos; no persiste imágenes como Base64. El manifiesto deshabilita tráfico HTTP en texto claro.

## Evidencias y documentación
- `docs/PRODUCT_BACKLOG.md`
- `docs/SPRINT_SCRUM.md`
- `docs/RIESGOS.md`
- `docs/PLAN_PRUEBAS.md`
- `docs/MATRIZ_TRAZABILIDAD.md`
- `docs/ARQUITECTURA.md`
- `docs/TDD.md`
- `docs/SEGURIDAD_PRIVACIDAD.md`
- `docs/CAPACIDAD_DISPOSITIVO.md`
- `docs/REGRESION.md`
- `docs/INFORME_CALIDAD.md`
- `docs/GITHUB_SETUP.md`

## Limitación importante
Issues, GitHub Project, Pull Requests, reviews, Actions ejecutadas, tags y evidencias reales de dispositivo viven en GitHub o se generan al ejecutar el proyecto; no se pueden fabricar dentro de un ZIP. El repositorio contiene plantillas, workflows y matrices para completar esa evidencia de forma trazable.

# PréstamoLab CTMA Android

Aplicación educativa para consultar equipos del CTMA, solicitar préstamos, conservarlos localmente, registrar entregas y devoluciones con evidencia, y sincronizar operaciones con un servicio REST. Este repositorio continúa el incremento original y conserva su historial Git.

## Product Goal

Facilitar la consulta, solicitud, préstamo, seguimiento y devolución trazable de equipos y herramientas de formación del CTMA mediante una aplicación móvil Android, con una experiencia segura, verificable y preparada para operar con datos locales y servicios remotos.

## Funcionalidad terminada

- Catálogo persistente con filtro de categoría guardado en DataStore.
- Detalle por `equipoId` con recuperación ante identificadores inexistentes.
- Solicitud validada: destino obligatorio, propósito de 10 a 180 caracteres y duración de 1 a 8 horas.
- Prevención transaccional de doble reserva y disponibilidad coherente.
- Consulta de préstamos y estado de sincronización.
- Cancelación de solicitudes y liberación del equipo.
- Registro de entrega y cambio del equipo a `PRESTADO`.
- Devolución con fotografía seleccionada mediante Photo Picker/OpenDocument.
- Persistencia de URI y metadatos; no se guardan Bitmap ni Base64.
- Verificación de acelerómetro como capacidad física adicional sin permisos innecesarios.
- Recordatorio contextual con permiso de notificaciones en Android 13 o superior.
- Sincronización REST con Retrofit/OkHttp, HTTPS, timeouts y errores recuperables.

## Arquitectura

`Compose UI -> ViewModel / UiState / StateFlow -> Repository -> Room / DataStore / Retrofit`

Room es la fuente visible y canónica. La interfaz no accede al DAO ni al cliente HTTP.

## Ejecución

1. Abrir la carpeta raíz en Android Studio.
2. Usar JDK 17 y sincronizar Gradle.
3. Verificar el wrapper Gradle 9.3.1.
4. Seleccionar un emulador o dispositivo con Android 7.0 API 24 o superior.
5. Ejecutar `app`.

El endpoint de demostración está centralizado en `BuildConfig.API_BASE_URL`. Debe sustituirse por la URL HTTPS del ambiente de pruebas antes de validar sincronización real. No se incluye ningún token o secreto.

## Pruebas y controles

```bash
./gradlew testDebugUnitTest
./gradlew lintDebug
./gradlew assembleDebug
./gradlew connectedDebugAndroidTest
```

La suite cubre validaciones, duplicados, cambios de estado, cancelación, entrega, devolución, evidencia obligatoria y respuestas HTTP 200/500 con MockWebServer. GitHub Actions ejecuta unit tests, lint y ensamblado, y publica el APK.

## Versionado sugerido

| Semana | Versión | Incremento |
|---|---|---|
| 5 | v0.2.0 | Línea base consolidada |
| 6 | v0.3.0 | Room, DataStore y Scrum formal |
| 7 | v0.4.0 | Corrutinas, Flow, UiState y métricas |
| 8 | v0.5.0 | Retrofit, local-first, MockWebServer y TDD |
| 9 | v0.6.0 | Evidencia, acelerómetro, notificaciones y seguridad |

## Documentación

Consultar `docs/` para arquitectura, backlog, riesgos, plan de pruebas, trazabilidad, seguridad, contrato API e informe de calidad.

## Limitaciones conocidas

La URL incluida es demostrativa y no representa un backend institucional. La sincronización requiere una API compatible. La fotografía y el acelerómetro deben validarse en emulador con sensor virtual o dispositivo físico. Solo se registra PASS después de una ejecución real.

## Uso responsable de IA

Se utilizó asistencia de IA para revisar requisitos, proponer estructura, ampliar escenarios y apoyar la implementación. El equipo debe ejecutar los controles, revisar cada cambio, comprender el código y poder modificarlo durante la sustentación. No se compartieron credenciales, tokens ni datos personales.

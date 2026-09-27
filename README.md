# PréstamoLab CTMA — integración limpia
Proyecto Android integrado con Compose, ViewModel/StateFlow, Room, DataStore, Repository, Retrofit/OkHttp, pruebas unitarias/instrumentadas, evidencia fotográfica, GPS y notificaciones.

## Abrir
Abre esta carpeta raíz en Android Studio y espera Gradle Sync.

## Pruebas
Windows: `./gradlew.bat test` y con emulador `./gradlew.bat connectedAndroidTest`.

## API
La URL por defecto es `https://example.invalid/` para no inventar un backend. Configura `prestamolabApiUrl=https://TU-API/` en `~/.gradle/gradle.properties` cuando tengas la API.

No contiene carpetas `build`, `.gradle`, `.idea`, archivos `.class`, `local.properties` ni el proyecto anidado `PrestamosLabCTMA`.

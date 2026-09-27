# Matriz de cumplimiento de las guías

## Primera guía integradora
- ✅ Catálogo con nombre, categoría y disponibilidad.
- ✅ Detalle por `equipmentId`.
- ✅ Formulario: ambiente/destino, propósito y duración.
- ✅ Validaciones RN-01 a RN-08 principales.
- ✅ Mis solicitudes/préstamos y detalle.
- ✅ Prevención de duplicados.
- ✅ Cambio coherente de disponibilidad.
- ✅ ID inexistente recuperable.
- ✅ Cancelación de `SOLICITADA`.
- ✅ Repository + ViewModel + StateFlow + Navigation Compose.
- ✅ Suite diseñada ≥16 casos, riesgos, trazabilidad, DoD, Review y Retro documentados.

## Segunda guía / evolución semanas 5–9
- ✅ Semana 5: arquitectura y corte de calidad.
- ✅ Semana 6: Room, DAO, CRUD, DataStore y fuente local canónica.
- ✅ Semana 7: Flow/StateFlow, corrutinas y estados recuperables.
- ✅ Semana 8: Retrofit/OkHttp, DTO, MockWebServer y TDD acotado.
- ✅ Semana 9: Photo Picker + URI, notificaciones, seguridad y capacidad física adicional (energía/almacenamiento).
- ✅ GitHub Actions para build, unit tests, lint y APK.

## Herramientas GitHub
- ✅ Plantillas Issues/PR, Actions, Dependabot, CodeQL, CODEOWNERS.
- ✅ Documentación de Product Backlog, riesgos, pruebas y trazabilidad.
- ⚠️ GitHub Project, Issues reales, Pull Requests, reviews, ejecuciones de Actions y tags deben existir en GitHub: un ZIP no puede generarlos como historial verificable.
- ⚠️ PASS/FAIL/BLOCKED definitivo y screenshots deben provenir de una ejecución real del build en Android Studio/emulador/dispositivo.

## Verificación realizada al generar este paquete
- ✅ Modelo y validador Kotlin compilados con `kotlinc`.
- ✅ Estructura Android, manifest, Gradle, tests y workflows revisados estáticamente.
- ⚠️ No se pudo ejecutar Gradle completo en el entorno de preparación porque no había distribución/dependencias Android descargadas y el entorno no dispone de acceso de red. Android Studio deberá sincronizar las dependencias la primera vez.

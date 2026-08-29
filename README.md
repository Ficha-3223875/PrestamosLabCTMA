# PréstamoLab CTMA

Aplicación Android para consultar equipos y herramientas de formación, solicitar préstamos y gestionar su estado.

## Arquitectura implementada
- Kotlin + Jetpack Compose
- Navigation Compose
- ViewModel
- StateFlow para el estado de UI
- Repository en memoria
- Reglas de negocio para disponibilidad
- Pruebas unitarias
- Prueba UI instrumentada
- Android Lint

## Herramientas GitHub preparadas
La guía del producto propone verificar la cadena Historia de Usuario → criterios → código → pruebas → Pull Request → CI → defecto/corrección/regresión. Este proyecto incorpora:

- Issues mediante plantillas de HU, bugs y tareas.
- Pull Request template.
- Product Backlog con 6 historias iniciales.
- GitHub Actions para build, unit tests, Lint y artifacts/APK.
- Workflow de CodeQL para Kotlin.
- Dependabot para Gradle y GitHub Actions.
- CODEOWNERS como plantilla.
- Documentación paso a paso para configurar GitHub Projects, Issues, PRs y regresión.

## Estructura relevante
```text
.github/
  CODEOWNERS
  dependabot.yml
  pull_request_template.md
  ISSUE_TEMPLATE/
  workflows/
    android.yml
    codeql.yml
docs/
  PRODUCT_BACKLOG.md
  GITHUB_SETUP.md
app/
  src/main/java/com/prestamolab/ctma/
    MainActivity.kt
    model/Equipment.kt
    data/EquipmentRepository.kt
    viewmodel/EquipmentViewModel.kt
    ui/HomeScreen.kt
    ui/DetailScreen.kt
    ui/PrestamoLabApp.kt
  src/test/...
  src/androidTest/...
```

## Ejecutar localmente
Abre `PrestamoLabCTMA` en Android Studio y sincroniza Gradle.

Desde una terminal con Gradle 8.11.1:
```bash
gradle assembleDebug
gradle testDebugUnitTest
gradle lintDebug
```

## GitHub
Consulta `docs/GITHUB_SETUP.md` y `docs/GITHUB_CHECKLIST.md` para crear las Issues, el Project, los PRs y completar la configuración del repositorio.

> Nota: GitHub Projects, Issues, PRs, colaboradores y reglas del repositorio son configuraciones del servicio GitHub y no pueden quedar creadas dentro de un archivo ZIP. El proyecto incluye los archivos de configuración y plantillas necesarios para prepararlas.

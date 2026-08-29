# Configuración de herramientas GitHub

La guía de PréstamoLab propone relacionar Historia de Usuario → criterios de aceptación → código → pruebas → Pull Request → CI → defecto/corrección/regresión.

## 1. Repositorio
1. Crea un repositorio GitHub llamado `PrestamoLabCTMA`.
2. Sube el contenido de esta carpeta.
3. Trabaja con ramas para cada historia, por ejemplo `feature/HU-01-consultar-equipos`.

## 2. Issues
Crea una Issue por cada HU de `docs/PRODUCT_BACKLOG.md`.
Usa la plantilla **Historia de usuario**.
Para defectos, usa **Defecto / Bug**.
Vincula el PR con la Issue usando, por ejemplo, `Closes #12`.

## 3. GitHub Projects
Crea un Project asociado al repositorio.
Puedes usar vista Board/Kanban con estados:
- Backlog
- Ready
- In progress
- In review
- Done

Añade campos como Prioridad, Sprint y Tipo. Agrega las Issues de las seis HU.

## 4. Pull Requests
Cada HU debe implementarse mediante un PR.
En la descripción del PR:
- indica `Closes #N`;
- marca los criterios de aceptación;
- adjunta evidencia;
- indica las pruebas ejecutadas.

## 5. GitHub Actions
`.github/workflows/android.yml` ejecuta:
- compilación `assembleDebug`;
- pruebas unitarias `testDebugUnitTest`;
- Android Lint `lintDebug`;
- conservación del APK y reportes como artifacts.

`.github/workflows/codeql.yml` agrega análisis CodeQL para Kotlin.

## 6. Dependabot
`.github/dependabot.yml` revisa semanalmente dependencias Gradle y mensualmente GitHub Actions.

## 7. CODEOWNERS
Edita `.github/CODEOWNERS` y reemplaza `@TU_USUARIO_GITHUB` por el usuario o equipo real que deba revisar el código.

## 8. Branch protection
La guía señala una limitación de GitHub Free para repositorios privados respecto a reglas obligatorias de protección y checks requeridos. Configura las reglas disponibles según el tipo de repositorio y plan que use el equipo.

## 9. Revisión y regresión
Cuando se detecte un defecto:
1. crea una Issue `[BUG]`;
2. corrige en una rama;
3. agrega/actualiza una prueba que reproduzca el problema;
4. abre PR;
5. espera a que Actions compile, pruebe y ejecute Lint;
6. integra la corrección y verifica la regresión.

## 10. Insights
Usa la sección Insights del repositorio para revisar actividad, commits, contribuciones y evolución del trabajo.

### Importante
GitHub Projects, Issues, Pull Requests, colaboradores y configuración de protección de ramas son elementos del repositorio en GitHub; no se pueden crear realmente dentro de un ZIP. Este proyecto deja las plantillas y documentación preparadas para configurarlos.

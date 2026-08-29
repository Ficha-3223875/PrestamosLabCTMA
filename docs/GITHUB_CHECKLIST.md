# Checklist de conexión con GitHub — PréstamoLab CTMA

Este archivo separa lo que queda preparado dentro del proyecto de lo que debe hacerse en GitHub.

## A. Ya viene preparado en el ZIP

- [x] 6 historias de usuario en `docs/PRODUCT_BACKLOG.md`.
- [x] Plantilla de Issue para historias de usuario.
- [x] Plantilla de Issue para bugs.
- [x] Plantilla de Issue para tareas.
- [x] Plantilla de Pull Request.
- [x] GitHub Actions para build, pruebas unitarias, Lint y artifacts.
- [x] Workflow de CodeQL para Kotlin.
- [x] Dependabot para Gradle y GitHub Actions.
- [x] CODEOWNERS como plantilla.
- [x] Pruebas unitarias.
- [x] Prueba UI instrumentada.
- [x] Documentación para Projects, Issues, PRs, regresión e Insights.
- [x] Arquitectura separada en model / data / viewmodel / ui.

## B. Debes hacerlo en GitHub

### 1. Crear el repositorio

Crea un repositorio llamado `PrestamoLabCTMA` en tu cuenta de GitHub y copia allí el contenido de este proyecto.

### 2. Subir el proyecto

Desde Git Bash, ubicado en la carpeta raíz del proyecto:

```bash
git init
git add .
git commit -m "feat: preparar PréstamoLab CTMA para Scrum y CI"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/PrestamoLabCTMA.git
git push -u origin main
```

Reemplaza `TU_USUARIO` por tu usuario real.

### 3. Crear las 6 Issues

Crea una Issue para cada HU del Product Backlog y copia sus criterios de aceptación.

Usa títulos como:

- `HU-01 Consultar equipos`
- `HU-02 Consultar detalle`
- `HU-03 Solicitar préstamo`
- `HU-04 Impedir préstamo no disponible`
- `HU-05 Mantener estado durante la navegación`
- `HU-06 Verificar calidad automáticamente`

Asigna prioridad, responsable y etiquetas según el trabajo del equipo.

### 4. Crear el GitHub Project

Crea un Project asociado al repositorio.

Estados sugeridos:

- Backlog
- Ready
- In progress
- In review
- Done

Campos sugeridos:

- Prioridad
- Sprint
- Tipo

Agrega las seis Issues al Project.

### 5. Crear ramas y Pull Requests

Para cada HU crea una rama, por ejemplo:

```text
feature/HU-01-consultar-equipos
feature/HU-02-consultar-detalle
feature/HU-03-solicitar-prestamo
```

Al abrir el PR, usa la plantilla incluida y relaciona la Issue con:

```text
Closes #NUMERO
```

### 6. Comprobar GitHub Actions

Después del primer `push` o PR, entra a **Actions** y verifica:

- `assembleDebug`
- `testDebugUnitTest`
- `lintDebug`
- artifact del APK
- reportes de pruebas/Lint

### 7. Completar CODEOWNERS

Edita `.github/CODEOWNERS` y cambia:

```text
@TU_USUARIO_GITHUB
```

por el usuario o equipo real responsable de revisar el código.

### 8. Verificar Dependabot

En GitHub revisa la sección de seguridad/dependencias y comprueba que aparezcan las actualizaciones o alertas cuando corresponda.

### 9. Revisar CodeQL

En **Security** revisa que el análisis de CodeQL se ejecute y no presente problemas bloqueantes.

### 10. Registrar un defecto y regresión

Para demostrar el flujo de la guía:

1. Crear Issue de tipo Bug.
2. Crear rama de corrección.
3. Reproducir el problema con una prueba.
4. Corregirlo.
5. Abrir PR.
6. Verificar Actions.
7. Integrar el PR.
8. Confirmar que las pruebas anteriores siguen pasando.

## C. Lo que no se puede crear desde un ZIP

El ZIP no puede crear por sí solo el repositorio de GitHub, Issues, Project, PRs, commits, contributors ni las reglas de protección de ramas. Esos elementos se generan al conectar el proyecto con una cuenta/repositorio de GitHub.

# Defecto, corrección y regresión

## Defecto identificado

Android Lint detectó un problema en las pantallas `CatalogoScreen` y
`MisSolicitudesScreen`.

Se estaba utilizando `StateFlow.value` directamente dentro de la
composición de Jetpack Compose.

## Corrección aplicada

Se reemplazó la lectura directa de `uiState.value` por `collectAsState()`,
permitiendo que Compose observe correctamente los cambios del `StateFlow`
y actualice la interfaz.

## Validación

Se ejecutó:

text
.\gradlew lintDebug

Resultado:

BUILD SUCCESSFUL

Regresión

Después de aplicar la corrección, se realizó el push a la rama feat/Naurimar.

GitHub Actions ejecutó nuevamente las pruebas y la ejecución terminó correctamente con estado verde.

Estado

Completado.
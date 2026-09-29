# Plan de Pruebas — PréstamoLab CTMA

## 1. Objetivo

Verificar que las funcionalidades principales de PréstamoLab CTMA cumplan sus criterios de aceptación y funcionen de manera estable.

## 2. Alcance

Se validan principalmente:

- Consulta del catálogo.
- Consulta del detalle de un equipo.
- Disponibilidad de equipos.
- Registro de solicitudes.
- Validación de datos.
- Prevención de solicitudes duplicadas.
- Consulta de mis solicitudes.
- Cancelación de solicitudes.
- Búsqueda de equipos.
- Filtro por categoría.
- Confirmación de datos antes de registrar.
- Manejo de equipos y solicitudes inexistentes.

## 3. Tipos de pruebas

### Pruebas manuales

Se realizan recorridos funcionales sobre la aplicación y se registran los resultados como:

- PASS: comportamiento esperado.
- FAIL: comportamiento incorrecto.
- BLOCKED: prueba que no puede ejecutarse por una dependencia o bloqueo.

### Pruebas unitarias

Se utilizan pruebas automatizadas con JUnit para validar reglas del Repository y del ViewModel.

Actualmente se cuenta con:

- 18 pruebas del `InMemoryPrestamoRepository`.
- 8 pruebas del `PrestamoViewModel`.
- Total: 26 pruebas unitarias.

### Pruebas de interfaz

Se utilizan pruebas instrumentadas para verificar recorridos de la aplicación Android.

## 4. Casos principales

| ID | Caso | Resultado esperado |
|---|---|---|
| TC-01 | Consultar catálogo | Se muestran los equipos registrados. |
| TC-02 | Consultar detalle existente | Se muestran los datos del equipo. |
| TC-03 | Consultar equipo inexistente | Se muestra un estado recuperable sin cierre inesperado. |
| TC-04 | Solicitar equipo disponible | Se crea la solicitud correctamente. |
| TC-05 | Solicitar equipo no disponible | La solicitud es rechazada y se informa al usuario. |
| TC-06 | Propósito inválido | Se muestra validación y no se crea la solicitud. |
| TC-07 | Duración inválida | Se muestra validación y no se crea la solicitud. |
| TC-08 | Doble solicitud del mismo equipo | Se evita la duplicación. |
| TC-09 | Consultar mis solicitudes | Se muestran las solicitudes registradas. |
| TC-10 | Cancelar solicitud solicitada | La solicitud pasa a CANCELADA y el equipo queda disponible. |
| TC-11 | Buscar equipo | Se muestran coincidencias con la búsqueda. |
| TC-12 | Filtrar por categoría | Se muestran únicamente equipos de la categoría seleccionada. |
| TC-13 | Confirmar datos | El resumen coincide con los datos ingresados antes de guardar. |

## 5. Evidencias

Las evidencias funcionales se almacenan en el repositorio dentro de la carpeta `docs`.

También se conservan evidencias relacionadas con las pruebas automatizadas y la corrección de defectos.

## 6. Criterios de aceptación de las pruebas

Una funcionalidad se considera validada cuando:

1. Cumple sus criterios de aceptación.
2. No presenta cierres inesperados.
3. Las validaciones impiden datos inválidos.
4. Las pruebas automatizadas correspondientes son exitosas.
5. Existe evidencia reproducible cuando aplica.

## 7. Ejecución automatizada

Las pruebas unitarias se ejecutan mediante:

    ./gradlew testDebugUnitTest

Android Lint se ejecuta mediante:

    ./gradlew lintDebug

Las pruebas instrumentadas se ejecutan mediante:

    ./gradlew connectedDebugAndroidTest

GitHub Actions automatiza los controles de calidad configurados para el proyecto.

## 8. Estado

Plan de pruebas correspondiente al incremento de Semana 5.
# Plan de Pruebas — PréstamoLab CTMA

## 1. Objetivo

Verificar el incremento Android del MVP frente a las historias de usuario, criterios de aceptación y reglas de negocio definidas para PréstamoLab CTMA.

La cobertura de pruebas prioriza la disponibilidad de equipos, validaciones de datos, prevención de solicitudes duplicadas, navegación, consistencia de estados, transiciones de solicitudes y accesibilidad básica.

La ejecución documentada en esta entrega se limita a los casos que cuentan con evidencia real disponible.

## 2. Build objetivo

- Versión documental: `0.1.0`.
- Build real: completar con la versión del APK o build utilizada durante la ejecución.
- La versión exacta utilizada para las evidencias debe coincidir con la registrada en la Bitácora de Ejecución.

## 3. Alcance incluido

Las pruebas consideran las siguientes funcionalidades del incremento:

- Catálogo de equipos.
- Visualización del detalle mediante `equipoId`.
- Registro de solicitudes de préstamo.
- Validación de destino obligatorio.
- Validación del propósito entre 10 y 180 caracteres.
- Validación de duración entre 1 y 8 horas.
- Rechazo de solicitudes sobre equipos en estado `RESERVADO` o `PRESTADO`.
- Prevención de solicitudes duplicadas.
- Actualización del estado de disponibilidad del equipo.
- Consulta de solicitudes en "Mis solicitudes".
- Visualización del detalle mediante `solicitudId`.
- Cancelación de solicitudes en estado `SOLICITADA`.
- Manejo seguro de identificadores inexistentes.
- Accesibilidad básica mediante textos, etiquetas y comportamiento con fuente aumentada.

## 4. Exclusiones

No forman parte del alcance de esta versión:

- Backend remoto.
- Autenticación real.
- Inventario institucional real.
- Persistencia de información después de cerrar la aplicación.
- Integración con sistemas externos.
- Operación logística real de aprobación, entrega y devolución.

Los estados adicionales definidos por el modelo de datos no implican que exista una operación completa de aprobación, entrega o devolución en la interfaz actual.

## 5. Base de prueba

La planificación se basa en:

- Product Goal.
- Product Backlog.
- Criterios de aceptación.
- Reglas de negocio RN-01 a RN-09.
- Matriz de riesgos.
- Datos sintéticos del proyecto.
- Suite de casos de prueba `TC-01` a `TC-18`.
- Arquitectura y comportamiento implementados en el incremento Android.

## 6. Técnicas de caja negra

Se utilizan las siguientes técnicas:

1. **Partición de equivalencia:** para separar entradas válidas e inválidas.
2. **Análisis de valores límite:** para propósito y duración.
3. **Tabla de decisión:** para validar el comportamiento según disponibilidad del equipo.
4. **Transición de estados:** para solicitudes y disponibilidad de equipos.
5. **Casos de uso:** para validar los flujos principales de la aplicación.
6. **Pruebas negativas:** para entradas inválidas, identificadores inexistentes y acciones no permitidas.

## 7. Riesgos priorizados

- **R-01:** dos solicitudes activas para el mismo equipo.
- **R-02:** aceptación de datos fuera de rango.
- **R-03:** un ID inexistente provoca cierre o comportamiento incorrecto.
- **R-04:** catálogo, solicitudes y disponibilidad quedan inconsistentes.
- **R-05:** pérdida de legibilidad o usabilidad con fuente aumentada.

La relación entre riesgos y casos de prueba se encuentra documentada en la **Matriz de Riesgos**.

## 8. Ambiente requerido

Para ejecutar las pruebas se requiere:

- Android Studio compatible con el proyecto.
- JDK configurado de acuerdo con el proyecto.
- Emulador o dispositivo Android compatible con la versión mínima definida por la aplicación.
- Fuente del sistema en tamaño normal.
- Prueba adicional con tamaño de fuente aumentado para TC-18.
- Datos sintéticos definidos en `Datos-Sinteticos.md`.

## 9. Criterios de entrada

Antes de comenzar una ejecución se debe verificar:

- Proyecto sincronizado correctamente.
- Proyecto compilable.
- Aplicación instalada en el emulador o dispositivo.
- Datos sintéticos disponibles.
- Casos de prueba y sus precondiciones preparados.
- Build identificado mediante su versión correspondiente.

## 10. Criterios de salida

Para esta entrega, los criterios de salida son:

- Los casos seleccionados para ejecución cuentan con un resultado observado.
- Cada resultado registrado en la Bitácora tiene respaldo mediante evidencia cuando corresponda.
- Los casos no ejecutados permanecen identificados como no ejecutados y no se reportan como PASS.
- Los resultados FAIL, si aparecen, cuentan con una descripción del comportamiento observado y evidencia suficiente para reproducirlos.
- Los defectos reales se registran únicamente cuando existe una discrepancia reproducible.
- La Matriz de Trazabilidad refleja cuáles casos fueron ejecutados y cuáles permanecen sin ejecución.
- README, Bitácora, Sprint Review e Informe Ejecutivo reflejan los resultados reales de esta entrega.

### Estado de ejecución de esta entrega

La entrega cuenta con evidencia disponible para:

- TC-01
- TC-02
- TC-03
- TC-04
- TC-05

Los casos TC-06 a TC-18 permanecen definidos en la suite, pero no se consideran ejecutados mientras no exista una ejecución documentada.

## 11. Convención de resultados

- **PASS:** el resultado observado coincide con el resultado esperado.
- **FAIL:** existe una discrepancia reproducible entre el resultado esperado y el observado.
- **BLOCKED:** una condición externa o una precondición impide completar el caso.
- **No ejecutado:** el caso está definido en la suite, pero no cuenta con una ejecución documentada en esta entrega.

## 12. Evidencia

Las evidencias disponibles se organizan en:

`docs/evidencias/`

Se recomienda utilizar el identificador del caso para facilitar la trazabilidad, por ejemplo:

- `TC-01.png`
- `TC-02.png`
- `TC-03.png`
- `TC-04.png`
- `TC-05.png`

No es necesario generar una evidencia independiente para cada uno de los 18 casos si el alcance de la entrega y las indicaciones del instructor no lo requieren.

Las evidencias utilizadas deben corresponder a ejecuciones reales. No se deben fabricar capturas, videos ni resultados.

## 13. Responsabilidades

Cada integrante debe conocer las pruebas asociadas a las decisiones técnicas que defiende y estar en capacidad de explicar:

- qué caso de prueba se ejecutó;
- cuál era la precondición;
- qué pasos se realizaron;
- cuál era el resultado esperado;
- qué resultado se observó;
- qué evidencia respalda la ejecución;
- y qué defecto se registraría en caso de encontrar una discrepancia reproducible.

## 14. Registro y trazabilidad

Los resultados de las pruebas se registran en:

- `Bitacora.md`
- `Casos-de-Prueba.md`
- `Matriz-Riesgos.md`
- `Matriz-Trazabilidad.md`
- `Registro-Defectos.md`

La información de estos documentos debe mantenerse consistente. Un caso que no haya sido ejecutado no debe aparecer como PASS en ninguno de ellos.
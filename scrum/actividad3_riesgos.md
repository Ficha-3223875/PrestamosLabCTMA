# Actividad 3 - Riesgos y Priorización de Pruebas

## Proyecto: PréstamoLab CTMA

El equipo identificó los principales riesgos que pueden afectar el funcionamiento
del primer incremento de PréstamoLab CTMA. Estos riesgos permiten priorizar las
pruebas según su probabilidad e impacto sobre el usuario y las reglas de negocio.

## Matriz de riesgos

| ID | Riesgo | Probabilidad | Impacto | Nivel | Estrategia de cobertura |
|---|---|---|---|---|---|
| R-01 | Dos solicitudes activas reservan el mismo equipo. | Alta | Alta | Crítico | Prueba de disponibilidad y doble pulsación de Guardar. |
| R-02 | La aplicación acepta datos fuera de los rangos permitidos. | Alta | Media | Alto | Partición de equivalencia y pruebas de valores límite. |
| R-03 | Un identificador de equipo o solicitud inexistente provoca el cierre de la aplicación. | Media | Alta | Alto | Pruebas negativas de navegación con IDs inexistentes. |
| R-04 | El catálogo no refleja correctamente el cambio de disponibilidad después de crear o cancelar una solicitud. | Media | Alta | Alto | Prueba del flujo completo y pruebas de regresión. |
| R-05 | Las acciones o textos esenciales dejan de ser utilizables con el tamaño de fuente aumentado. | Media | Media | Medio | Pruebas básicas de accesibilidad con fuente aumentada. |
| R-06 | Una solicitud se cancela desde un estado donde la transición no está permitida. | Media | Alta | Alto | Pruebas de transición de estados válidas e inválidas. |
| R-07 | Una doble pulsación sobre Guardar crea solicitudes duplicadas. | Alta | Alta | Crítico | Ejecutar una prueba específica de doble pulsación y comprobar el número de solicitudes creadas. |
| R-08 | Los mensajes de validación no indican claramente cuál dato debe corregirse. | Media | Media | Medio | Pruebas negativas del formulario y revisión de mensajes de error. |

## Priorización de las pruebas

Los riesgos clasificados como **Crítico** y **Alto** tendrán prioridad durante
el diseño y ejecución de las pruebas, debido a que pueden afectar directamente
las reglas principales del proceso de préstamo.

Se dará especial atención a:

1. Evitar solicitudes duplicadas.
2. Impedir préstamos de equipos no disponibles.
3. Validar correctamente los datos del formulario.
4. Controlar identificadores inexistentes sin cierres inesperados.
5. Mantener coherencia entre el estado de una solicitud y la disponibilidad del equipo.
6. Validar las transiciones permitidas entre estados.

Los riesgos de nivel **Medio**, especialmente los relacionados con accesibilidad
y claridad de los mensajes, también serán evaluados para garantizar que las
funciones principales continúen siendo comprensibles y utilizables.

## Relación con las historias de usuario

- **R-01 y R-07:** relacionados con HU-03 y HU-05.
- **R-02:** relacionado con HU-04.
- **R-03:** relacionado con HU-02 y HU-06.
- **R-04:** relacionado con HU-03 y HU-07.
- **R-05 y R-08:** relacionados con HU-08.
- **R-06:** relacionado con HU-07.

## Conclusión

La identificación temprana de riesgos permite orientar las pruebas hacia los
comportamientos que podrían afectar en mayor medida el objetivo del incremento.
La matriz será utilizada posteriormente como base para diseñar los casos de
prueba y establecer su prioridad de ejecución.
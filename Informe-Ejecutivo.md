# Informe Ejecutivo de Calidad — PréstamoLab CTMA

**Build documental:** 0.1.0  
**Estado de ejecución:** Ejecución parcial documentada mediante evidencia real.

## 1. Alcance

Se construyó un prototipo Android educativo para consultar equipos, registrar solicitudes de préstamo y consultar/cancelar solicitudes.

La aplicación utiliza datos sintéticos y un repositorio en memoria. El alcance corresponde al incremento definido para PréstamoLab CTMA.

Las principales funcionalidades consideradas son:

- Consulta del catálogo de equipos.
- Consulta del detalle de un equipo.
- Registro de solicitudes de préstamo.
- Validación de los datos de la solicitud.
- Control de disponibilidad de los equipos.
- Consulta de solicitudes.
- Cancelación de solicitudes.
- Manejo de identificadores inexistentes.
- Navegación entre las pantallas principales.
- Accesibilidad básica.

## 2. Arquitectura

La solución utiliza una arquitectura separada en capas:

- **UI:** Jetpack Compose para la presentación y las interacciones.
- **ViewModel:** gestión del estado de la pantalla mediante `StateFlow`.
- **Repository:** separación de la lógica de acceso y manipulación de datos.
- **InMemoryPrestamoRepository:** implementación utilizada para trabajar con datos sintéticos en memoria.

Las validaciones y reglas principales de disponibilidad se mantienen fuera de los composables para separar la presentación de la lógica de negocio.

## 3. Cobertura planificada

La suite de pruebas contiene **18 casos de prueba**, orientados a cubrir:

- Consulta del catálogo.
- Consulta del detalle mediante `equipoId`.
- Manejo de IDs inexistentes.
- Valores límite del propósito: 9, 10, 180 y 181 caracteres.
- Valores límite de duración: 0, 1, 8 y 9 horas.
- Equipos disponibles y no disponibles.
- Prevención de solicitudes duplicadas.
- Creación de solicitudes.
- Actualización de disponibilidad.
- Consulta y cancelación de solicitudes.
- Transiciones de estado.
- Navegación.
- Accesibilidad con fuente aumentada.

## 4. Ejecución realizada

Para esta entrega se cuenta con evidencia real de ejecución para los siguientes casos:

| Ejecución | Caso | Resultado |
|---|---|---|
| EX-001 | TC-01 | PASS |
| EX-002 | TC-02 | PASS |
| EX-003 | TC-03 | PASS |
| EX-004 | TC-04 | PASS |
| EX-005 | TC-05 | PASS |

Por lo tanto:

- **Casos definidos:** 18.
- **Casos ejecutados y documentados:** 5.
- **PASS:** 5.
- **FAIL:** 0.
- **BLOCKED:** 0.
- **Casos no ejecutados en esta entrega:** 13.

Los casos TC-06 a TC-18 permanecen definidos dentro de la suite de pruebas, pero no se presentan como ejecutados debido a que no cuentan con una ejecución documentada mediante evidencia en esta entrega.

## 5. Defectos

Durante las ejecuciones documentadas de TC-01 a TC-05 **no se registraron defectos reproducibles**.

No se han creado defectos artificiales para completar la documentación.

El Registro de Defectos permanece disponible para documentar cualquier discrepancia reproducible que pueda aparecer durante ejecuciones posteriores.

**Conclusión:** no se encontraron defectos reproducibles en las ejecuciones documentadas.

## 6. Riesgo residual

Existe riesgo residual debido a que no todos los casos de la suite han sido ejecutados en esta entrega.

Las principales áreas que requieren validación adicional son:

- Prevención de solicitudes duplicadas.
- Validaciones de los límites superiores e inferiores restantes.
- Solicitud de equipos `RESERVADO` o `PRESTADO`.
- Consistencia de disponibilidad después de crear y cancelar solicitudes.
- Cancelación de solicitudes ya canceladas.
- Navegación mediante identificadores.
- Comportamiento con fuente aumentada.

Por esta razón, la ausencia de defectos en las cinco ejecuciones documentadas **no implica que se hayan descartado defectos en toda la suite**.

## 7. Limitaciones

El incremento presenta las siguientes limitaciones:

- No existe backend remoto.
- No existe autenticación real.
- No se utiliza un inventario institucional real.
- La persistencia se realiza en memoria durante la ejecución.
- Los datos utilizados son sintéticos.
- No se almacenan datos personales reales.
- El gestor de datos utilizado es simulado.
- Los estados de aprobación, entrega y devolución están contemplados en el modelo, pero no representan una operación logística institucional completa.
- La ejecución documentada en esta entrega cubre únicamente 5 de los 18 casos definidos.

## 8. Definition of Done

La implementación y la documentación del incremento se encuentran preparadas.

La validación de la Definition of Done debe considerar:

- Código implementado.
- Arquitectura documentada.
- Suite de 18 casos definida.
- Casos ejecutados con evidencia real cuando corresponda.
- Resultados registrados en la Bitácora.
- Defectos registrados únicamente cuando sean reproducibles.
- Trazabilidad actualizada.
- Documentación Scrum y de calidad actualizada.

Para esta entrega, la parte de ejecución y evidencia es **parcial**, ya que se documentaron 5 de los 18 casos de prueba.

Por lo tanto, no se debe afirmar que la totalidad de la Definition of Done está validada mediante ejecución completa.

## 9. Recomendación de calidad

Con base exclusivamente en las ejecuciones documentadas, se observa un resultado favorable:

- 5 casos ejecutados.
- 5 casos PASS.
- 0 casos FAIL.
- 0 defectos reproducibles registrados.

Sin embargo, debido a que 13 casos de la suite aún no cuentan con ejecución documentada, la recomendación debe expresarse como:

### **ACEPTABLE CON CONDICIONES**

La aplicación presenta resultados satisfactorios en las ejecuciones documentadas, pero se recomienda completar posteriormente la ejecución de los casos restantes para aumentar la cobertura y reducir el riesgo residual antes de considerar la validación de calidad como completa.

## 10. Conclusión ejecutiva

El incremento Android de PréstamoLab CTMA presenta una base funcional y técnica preparada para el alcance definido.

Las ejecuciones documentadas muestran resultados PASS y no se identificaron defectos reproducibles en los cinco casos ejecutados.

No obstante, la suite contiene 18 casos y únicamente 5 cuentan con evidencia en esta entrega. Por lo tanto, el resultado debe interpretarse como una **validación parcial**, no como una certificación de que todas las funcionalidades fueron probadas.

La principal acción recomendada es completar las ejecuciones restantes cuando sea necesario y actualizar la Bitácora, la Matriz de Trazabilidad y el Registro de Defectos con los resultados reales obtenidos.
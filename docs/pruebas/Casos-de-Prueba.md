# Suite de Casos de Prueba — PréstamoLab CTMA

La suite contiene 18 casos de prueba diseñados para verificar el flujo principal de consulta de equipos, creación y cancelación de solicitudes, validaciones, navegación, consistencia de estados, prevención de duplicación y accesibilidad.

Los datos utilizados por la aplicación son sintéticos y se encuentran definidos en el repositorio `InMemoryPrestamoRepository`.

## Datos principales utilizados

| ID equipo | Equipo | Categoría | Estado inicial |
|---|---|---|---|
| 1 | Multímetro digital | Electrónica | DISPONIBLE |
| 2 | Kit de electrónica | Electrónica | DISPONIBLE |
| 3 | Tableta Android | Tecnología | RESERVADO |
| 4 | Cámara digital | Audiovisual | DISPONIBLE |
| 5 | Taladro eléctrico | Herramientas | PRESTADO |

---

## Casos de prueba

| ID | Precondición / datos | Pasos resumidos | Resultado esperado | Técnica | Riesgo |
|---|---|---|---|---|---|
| TC-01 | Aplicación recién iniciada | Abrir la aplicación y consultar el catálogo | Se muestran los equipos con nombre, categoría, descripción y estado de disponibilidad | Caso de uso | R-04 |
| TC-02 | Equipo 1 — Multímetro digital DISPONIBLE | Seleccionar el equipo 1 y abrir su detalle | El detalle corresponde al Multímetro digital y muestra su categoría, estado y descripción | Caso de uso | R-03 |
| TC-03 | `equipoId=999` | Intentar abrir el detalle de un equipo que no existe | La aplicación muestra el estado "Equipo no encontrado" y no se cierra abruptamente | Negativa | R-03 |
| TC-04 | Propósito con 9 caracteres | Seleccionar un equipo disponible, diligenciar los datos y escribir un propósito de 9 caracteres | No se crea la solicitud y se informa que el propósito debe tener entre 10 y 180 caracteres | Límite | R-02 |
| TC-05 | Propósito con 10 caracteres y demás datos válidos | Diligenciar una solicitud utilizando un propósito de exactamente 10 caracteres | El propósito es aceptado y la solicitud puede ser creada | Límite | R-02 |
| TC-06 | Propósito con 180 caracteres y demás datos válidos | Diligenciar una solicitud utilizando un propósito de exactamente 180 caracteres | El propósito es aceptado y la solicitud puede ser creada | Límite | R-02 |
| TC-07 | Propósito con 181 caracteres | Diligenciar una solicitud utilizando un propósito de 181 caracteres | No se crea la solicitud y se muestra el mensaje de validación correspondiente | Límite | R-02 |
| TC-08 | Duración de 0 horas | Seleccionar un equipo disponible e intentar registrar una solicitud con duración 0 | La solicitud es rechazada porque la duración debe estar entre 1 y 8 horas | Límite | R-02 |
| TC-09 | Duración de 1 hora y demás datos válidos | Registrar una solicitud con duración de exactamente 1 hora | La solicitud puede ser creada correctamente | Límite | R-02 |
| TC-10 | Duración de 8 horas y demás datos válidos | Registrar una solicitud con duración de exactamente 8 horas | La solicitud puede ser creada correctamente | Límite | R-02 |
| TC-11 | Duración de 9 horas | Intentar registrar una solicitud con duración de 9 horas | La solicitud es rechazada y se muestra el mensaje de validación correspondiente | Límite | R-02 |
| TC-12 | Equipo 3 — Tableta Android RESERVADO / Equipo 5 — Taladro eléctrico PRESTADO | Abrir el detalle de uno de los equipos no disponibles e intentar solicitarlo | El botón de solicitud aparece deshabilitado o indica que el equipo no está disponible; no se crea una solicitud | Decisión | R-04 |
| TC-13 | Equipo disponible y formulario válido | Diligenciar una solicitud y realizar una doble pulsación o intento de guardar dos veces | No se generan dos solicitudes activas para el mismo equipo; solamente puede existir una solicitud SOLICITADA y el equipo queda RESERVADO | Riesgo / duplicación | R-01 |
| TC-14 | Equipo 2 — Kit de electrónica DISPONIBLE y datos válidos | Abrir el detalle, seleccionar "Solicitar préstamo", diligenciar destino, propósito y duración y guardar | Se crea una solicitud en estado SOLICITADA, el equipo pasa a RESERVADO y el catálogo refleja el nuevo estado | Caso de uso | R-04 |
| TC-15 | Existe una solicitud en estado SOLICITADA | Abrir "Mis solicitudes", seleccionar la solicitud y utilizar la opción de cancelar | La solicitud cambia a CANCELADA y el equipo asociado vuelve a estado DISPONIBLE | Transición | R-04 |
| TC-16 | Existe una solicitud en estado CANCELADA | Abrir el detalle de una solicitud cancelada e intentar cancelarla nuevamente | La acción de cancelación no está disponible o no produce ningún cambio en la solicitud ni en la disponibilidad del equipo | Transición negativa | R-04 |
| TC-17 | Flujo Catálogo → Detalle → Solicitar | Navegar desde el catálogo al detalle de un equipo, abrir el formulario de solicitud y utilizar el botón de volver | La aplicación regresa al destino anterior correspondiente y conserva el `equipoId` necesario para continuar el flujo | Navegación | R-03 |
| TC-18 | Dispositivo/emulador con tamaño de fuente aumentado y textos largos | Revisar catálogo, detalle y formulario de solicitud con fuente aumentada y contenido largo | Los textos y las acciones esenciales continúan siendo comprensibles y utilizables, sin perder la acción principal | Accesibilidad | R-05 |

---

## Reglas cubiertas

| Regla | Descripción | Casos relacionados |
|---|---|---|
| RN-01 | Solo se puede solicitar un equipo que esté DISPONIBLE | TC-12, TC-14 |
| RN-02 | El ambiente/destino de la solicitud es obligatorio | Validación implementada en el Repository; se considera cobertura complementaria |
| RN-03 | El propósito debe tener entre 10 y 180 caracteres | TC-04, TC-05, TC-06, TC-07 |
| RN-04 | La duración debe estar entre 1 y 8 horas | TC-08, TC-09, TC-10, TC-11 |
| RN-05 | Una solicitud válida queda en estado SOLICITADA | TC-14 |
| RN-06 | No se deben crear solicitudes duplicadas para el mismo equipo | TC-13 |
| RN-07 | Solo una solicitud SOLICITADA puede cancelarse y al cancelar se libera el equipo | TC-15, TC-16 |
| RN-08 | Los identificadores inexistentes deben manejarse sin cierre abrupto | TC-03, TC-17 |
| RN-09 | Los datos utilizados para las pruebas son sintéticos | Todos los casos |

---

## Técnicas de caja negra utilizadas

La suite aplica diferentes técnicas de diseño de pruebas:

### 1. Análisis de valores límite

Se utilizan los límites definidos para las reglas de negocio:

- Propósito: **9, 10, 180 y 181 caracteres**.
- Duración: **0, 1, 8 y 9 horas**.

Casos relacionados:

- TC-04
- TC-05
- TC-06
- TC-07
- TC-08
- TC-09
- TC-10
- TC-11

### 2. Pruebas de decisión

Se comprueba el comportamiento de la aplicación dependiendo de la disponibilidad del equipo:

- DISPONIBLE → permite solicitar.
- RESERVADO → no permite solicitar.
- PRESTADO → no permite solicitar.

Casos relacionados:

- TC-12
- TC-14

### 3. Pruebas de transición de estados

Se verifica el cambio de estado de una solicitud y del equipo asociado:

`DISPONIBLE → RESERVADO`

`SOLICITADA → CANCELADA`

`RESERVADO → DISPONIBLE`

Casos relacionados:

- TC-14
- TC-15
- TC-16

### 4. Pruebas negativas

Se comprueban entradas o situaciones que deben ser rechazadas:

- ID inexistente.
- Propósito demasiado corto.
- Propósito demasiado largo.
- Duración inferior al mínimo.
- Duración superior al máximo.
- Equipo no disponible.
- Cancelación de una solicitud ya cancelada.

Casos relacionados:

- TC-03
- TC-04
- TC-07
- TC-08
- TC-11
- TC-12
- TC-16

### 5. Prueba de riesgo de duplicación

Se verifica que un mismo equipo no genere dos solicitudes activas ante un intento de doble guardado.

Caso relacionado:

- TC-13

---

## Cobertura de accesibilidad

El caso TC-18 verifica de forma práctica que el contenido principal de la aplicación continúe siendo comprensible cuando el usuario utiliza una fuente aumentada.

También se consideran:

- textos descriptivos para los estados;
- mensajes específicos de validación;
- descripción de los iconos mediante `contentDescription`;
- acciones principales claramente identificables;
- uso de componentes Material 3.

---

## Consideraciones de ejecución

Los 18 casos forman parte de la suite de pruebas diseñada para el proyecto.

La bitácora de ejecución registra únicamente los casos que cuentan con evidencia real disponible para esta entrega. Los casos que no hayan sido ejecutados no deben marcarse como PASS ni FAIL sin una ejecución verificable.

Las evidencias disponibles se encuentran organizadas por identificador de caso dentro de:

`docs/evidencias/`

## Resultado esperado de la suite

La suite permite verificar principalmente:

1. Consulta del catálogo.
2. Consulta del detalle de un equipo.
3. Manejo de identificadores inexistentes.
4. Validación de propósito.
5. Validación de duración.
6. Control de disponibilidad.
7. Prevención de solicitudes duplicadas.
8. Creación de solicitudes.
9. Actualización de estados.
10. Cancelación de solicitudes.
11. Liberación de equipos.
12. Navegación entre las pantallas.
13. Accesibilidad básica.
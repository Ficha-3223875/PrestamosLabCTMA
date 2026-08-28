# Actividad 2 - Historias de Usuario y Criterios de Aceptación

## Proyecto: PréstamoLab CTMA

Las siguientes historias de usuario representan las principales necesidades
identificadas para el primer incremento de PréstamoLab CTMA. Cada historia
incluye criterios de aceptación verificables que posteriormente servirán como
base para el diseño y ejecución de las pruebas de software.

---

## HU-01 - Consultar catálogo de equipos

**Como** aprendiz,  
**quiero** consultar el catálogo de equipos y herramientas,  
**para** conocer cuáles recursos están disponibles para solicitar en préstamo.

### Criterios de aceptación

- **CA-01.1:** Dado que existen equipos registrados, cuando el usuario ingresa al catálogo, entonces el sistema muestra los equipos disponibles para consulta.
- **CA-01.2:** Cada equipo debe mostrar como mínimo su nombre, categoría y estado de disponibilidad.
- **CA-01.3:** Si no existen equipos para mostrar, la aplicación debe presentar un estado vacío comprensible y no debe cerrarse inesperadamente.


---

## HU-02 - Consultar detalle de un equipo

**Como** aprendiz,  
**quiero** seleccionar un equipo del catálogo y consultar su información detallada,  
**para** conocer sus características y disponibilidad antes de solicitarlo.

### Criterios de aceptación

- **CA-02.1:** Dado un equipo existente, cuando el usuario lo selecciona en el catálogo, entonces la aplicación muestra la pantalla de detalle correspondiente a ese equipo.
- **CA-02.2:** La navegación al detalle debe utilizar el identificador `equipoId` del equipo seleccionado.
- **CA-02.3:** El detalle debe mostrar como mínimo el nombre, categoría y estado de disponibilidad del equipo.
- **CA-02.4:** Si se intenta consultar un `equipoId` inexistente, la aplicación debe mostrar un mensaje o estado recuperable y no debe cerrarse inesperadamente.


---

## HU-03 - Registrar solicitud de préstamo

**Como** aprendiz,  
**quiero** registrar una solicitud de préstamo para un equipo disponible,  
**para** utilizarlo temporalmente durante una actividad de formación.

### Criterios de aceptación

- **CA-03.1:** Dado un equipo en estado DISPONIBLE y un formulario válido, cuando el usuario pulsa Guardar, entonces se crea una solicitud en estado SOLICITADA.
- **CA-03.2:** La solicitud debe registrar el equipo seleccionado, ambiente o destino, propósito y duración estimada.
- **CA-03.3:** Solo se puede crear una solicitud cuando el equipo se encuentra DISPONIBLE.
- **CA-03.4:** Después de crear correctamente la solicitud, el equipo debe cambiar su estado de DISPONIBLE a RESERVADO.

---

## HU-04 - Validar datos de la solicitud

**Como** aprendiz,  
**quiero** recibir validaciones al diligenciar una solicitud,  
**para** evitar registrar información incompleta o incorrecta.

### Criterios de aceptación

- **CA-04.1:** El ambiente o destino es obligatorio y no se debe permitir guardar la solicitud si está vacío.
- **CA-04.2:** El propósito debe contener entre 10 y 180 caracteres.
- **CA-04.3:** Un propósito con menos de 10 o más de 180 caracteres debe ser rechazado y mostrar un mensaje específico.
- **CA-04.4:** La duración del préstamo debe estar entre 1 y 8 horas.
- **CA-04.5:** Una duración menor de 1 hora o mayor de 8 horas debe impedir el registro de la solicitud.
- **CA-04.6:** Cuando exista un error de validación, los demás datos diligenciados por el usuario deben conservarse.

---

## HU-05 - Evitar solicitudes duplicadas

**Como** aprendiz,  
**quiero** que una acción de guardado genere una sola solicitud,  
**para** evitar reservas duplicadas del mismo equipo.

### Criterios de aceptación

- **CA-05.1:** Dado un formulario válido, cuando el usuario pulsa Guardar una vez, entonces se crea una única solicitud.
- **CA-05.2:** Si el usuario pulsa rápidamente el botón Guardar dos veces, la aplicación no debe crear dos solicitudes.
- **CA-05.3:** Después de registrar la solicitud, el equipo debe quedar RESERVADO e impedir una nueva solicitud activa sobre el mismo recurso.

---

## HU-06 - Consultar mis solicitudes

**Como** aprendiz,  
**quiero** consultar las solicitudes de préstamo que he realizado,  
**para** conocer su información y estado actual.

### Criterios de aceptación

- **CA-06.1:** La aplicación debe disponer de una sección denominada "Mis solicitudes".
- **CA-06.2:** Cada solicitud debe mostrar información suficiente para identificar el equipo y su estado.
- **CA-06.3:** Al seleccionar una solicitud, la aplicación debe permitir consultar su detalle utilizando `solicitudId`.
- **CA-06.4:** Si no existen solicitudes, la aplicación debe mostrar un estado vacío comprensible.
- **CA-06.5:** Un `solicitudId` inexistente debe producir un estado recuperable sin provocar el cierre inesperado de la aplicación.

---

## HU-07 - Cancelar una solicitud

**Como** aprendiz,  
**quiero** cancelar una solicitud que todavía no ha sido procesada,  
**para** liberar el equipo cuando ya no necesite el préstamo.

### Criterios de aceptación

- **CA-07.1:** Solo una solicitud en estado SOLICITADA puede ser cancelada en el primer incremento.
- **CA-07.2:** Al cancelar correctamente una solicitud SOLICITADA, su estado debe cambiar a CANCELADA.
- **CA-07.3:** La cancelación debe actualizar de manera coherente la disponibilidad del equipo asociado.
- **CA-07.4:** Una solicitud que ya se encuentre CANCELADA no debe poder cancelarse nuevamente.
- **CA-07.5:** La aplicación no debe permitir cancelar solicitudes en estados donde la transición no esté autorizada.

---

## HU-08 - Mantener una interfaz accesible y comprensible

**Como** usuario de PréstamoLab CTMA,  
**quiero** visualizar claramente los estados, mensajes y acciones de la aplicación,  
**para** utilizar sus funciones sin depender exclusivamente del color o del tamaño normal del texto.

### Criterios de aceptación

- **CA-08.1:** La disponibilidad de un equipo debe comunicarse mediante texto o elementos descriptivos y no únicamente mediante color.
- **CA-08.2:** Los mensajes de error de los formularios deben indicar claramente qué dato debe corregirse.
- **CA-08.3:** Las acciones esenciales deben continuar siendo utilizables cuando el dispositivo tenga el tamaño de fuente aumentado.
- **CA-08.4:** Los textos esenciales no deben quedar truncados de forma que impidan comprender la acción o información presentada.
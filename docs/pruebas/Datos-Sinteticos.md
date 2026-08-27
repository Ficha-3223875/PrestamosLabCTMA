# Datos Sintéticos

Los datos utilizados para las pruebas son ficticios y se emplean únicamente para validar el funcionamiento de PréstamoLab CTMA. No contienen información personal real.

## 1. Equipos del catálogo

| ID | Equipo | Categoría | Estado inicial |
|---|---|---|---|
| 1 | Multímetro digital | Electrónica | DISPONIBLE |
| 2 | Kit de electrónica | Electrónica | DISPONIBLE |
| 3 | Tableta Android | Tecnología | RESERVADO |
| 4 | Cámara digital | Audiovisual | DISPONIBLE |
| 5 | Taladro eléctrico | Herramientas | PRESTADO |

## 2. Datos para solicitudes

Para los casos que requieren una solicitud válida se utilizan datos ficticios:

| Campo | Dato sintético |
|---|---|
| Destino | Laboratorio de Electrónica |
| Propósito | Práctica de laboratorio de electrónica |
| Duración | 2 horas |
| Equipo de prueba principal | Kit de electrónica (ID 2) |

Estos datos pueden reutilizarse en los casos de prueba que requieran una solicitud válida, modificando únicamente el campo que se esté evaluando.

## 3. Datos para pruebas de límites

### Propósito

La regla establece que el propósito debe contener entre **10 y 180 caracteres**.

Se utilizan los siguientes valores:

| Valor | Longitud | Uso |
|---|---:|---|
| `Prueba123` | 9 | Límite inferior inválido |
| Texto de 10 caracteres | 10 | Límite inferior válido |
| Texto de 180 caracteres | 180 | Límite superior válido |
| Texto de 181 caracteres | 181 | Límite superior inválido |

### Duración

La regla establece que la duración debe estar entre **1 y 8 horas**.

| Valor | Uso |
|---:|---|
| 0 horas | Límite inferior inválido |
| 1 hora | Límite inferior válido |
| 8 horas | Límite superior válido |
| 9 horas | Límite superior inválido |

## 4. Datos de disponibilidad

Se utilizan diferentes estados de equipo para comprobar las reglas de disponibilidad:

| Equipo | Estado | Uso |
|---|---|---|
| Multímetro digital (ID 1) | DISPONIBLE | Solicitud válida |
| Kit de electrónica (ID 2) | DISPONIBLE | Solicitud válida y pruebas de creación |
| Tableta Android (ID 3) | RESERVADO | Intento de solicitud sobre equipo no disponible |
| Cámara digital (ID 4) | DISPONIBLE | Equipo disponible para pruebas |
| Taladro eléctrico (ID 5) | PRESTADO | Intento de solicitud sobre equipo no disponible |

## 5. Datos de solicitudes y estados

Para las pruebas de transición se utilizan los estados definidos por el modelo de la aplicación:

- `SOLICITADA`
- `APROBADA`
- `ENTREGADA`
- `DEVUELTA`
- `CANCELADA`
- `RECHAZADA`

El flujo implementado y comprobado principalmente en esta versión es:

`DISPONIBLE → RESERVADO`

al crear una solicitud válida, y:

`SOLICITADA → CANCELADA`

con la liberación del equipo:

`RESERVADO → DISPONIBLE`

Los demás estados forman parte del modelo de datos, pero no representan operaciones logísticas completas implementadas en la interfaz actual.

## 6. Datos para navegación

| Dato | Valor | Uso |
|---|---|---|
| Equipo existente | `equipoId=1` | Consulta de detalle válido |
| Equipo existente | `equipoId=2` | Consulta y solicitud válida |
| Equipo inexistente | `equipoId=999` | Prueba de manejo de identificador inexistente |
| Solicitud existente | ID generado durante la creación | Consulta/cancelación |
| Solicitud inexistente | ID no existente | Validación de recuperación del estado |

## 7. Datos para pruebas negativas

Se consideran las siguientes situaciones:

- Propósito con menos de 10 caracteres.
- Propósito con más de 180 caracteres.
- Duración de 0 horas.
- Duración superior a 8 horas.
- Equipo en estado `RESERVADO`.
- Equipo en estado `PRESTADO`.
- Identificador de equipo inexistente.
- Intento de cancelar una solicitud que ya está `CANCELADA`.
- Intento de crear una segunda solicitud activa para el mismo equipo.

## 8. Consideraciones

Todos los datos anteriores son sintéticos y no corresponden a personas, equipos reales, préstamos reales ni información personal.

Los datos de prueba pueden cambiar de estado durante la ejecución. Por ejemplo, un equipo inicialmente `DISPONIBLE` puede pasar a `RESERVADO` después de crear una solicitud y posteriormente volver a `DISPONIBLE` cuando dicha solicitud sea cancelada.

Por esta razón, los casos que dependan de un estado específico deben ejecutarse con las precondiciones indicadas en cada caso de prueba.
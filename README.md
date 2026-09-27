# PréstamoLab CTMA

## Descripción del proyecto

PréstamoLab CTMA es una aplicación móvil desarrollada en Android para apoyar la consulta, solicitud, seguimiento y devolución de equipos y herramientas de formación.

El proyecto fue desarrollado como actividad integradora del programa de Análisis y Desarrollo de Software - ADSO, combinando desarrollo móvil Android, Scrum, pruebas de software, control de versiones con Git/GitHub y automatización de calidad.

La aplicación permite consultar un catálogo de equipos, conocer su disponibilidad, realizar solicitudes de préstamo, consultar los préstamos realizados, cancelar solicitudes, registrar devoluciones y adjuntar evidencia fotográfica.

También incorpora persistencia local, programación asíncrona, notificaciones y funcionalidades relacionadas con las capacidades del dispositivo.

---

## Objetivo

Desarrollar una aplicación Android que permita gestionar de forma sencilla y trazable el préstamo de equipos y herramientas de formación, manteniendo información sobre su disponibilidad y el estado de las solicitudes.

El proyecto busca aplicar buenas prácticas de arquitectura Android, persistencia de datos, gestión de estado, pruebas de software y trabajo colaborativo mediante GitHub.

---

## Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- StateFlow y Flow
- Coroutines
- Room
- DataStore
- Retrofit
- OkHttp
- MockWebServer
- JUnit
- Compose UI Test
- Android Lint
- Git
- GitHub
- GitHub Actions

---

## Arquitectura

El proyecto utiliza una arquitectura separada por responsabilidades.

La interfaz de usuario está desarrollada con Jetpack Compose. Las pantallas envían eventos al ViewModel y observan el estado de la aplicación mediante StateFlow.

El ViewModel se encarga de coordinar las acciones realizadas por el usuario.

El Repository funciona como punto de acceso a los datos y permite separar la interfaz de las fuentes de información.

La estructura general es:

Compose UI
↓
ViewModel
↓
StateFlow / Flow
↓
Repository
↓
Room / DataStore / Fuente remota

Esta separación facilita el mantenimiento del código y permite realizar pruebas de forma independiente.

---

## Funcionalidades principales

### Catálogo de equipos

La pantalla principal muestra los equipos y herramientas disponibles para préstamo.

Cada elemento contiene información como:

- Nombre
- Categoría
- Descripción
- Estado

Los estados principales utilizados para los equipos son:

`DISPONIBLE`

`RESERVADO`

`PRESTADO`

También existe la opción **Solo disponibles**, que permite filtrar el catálogo.

---

## Detalle del equipo

Al seleccionar un equipo se abre una pantalla con su información detallada.

La navegación utiliza el identificador del equipo para recuperar los datos correspondientes.

Desde esta pantalla se puede iniciar una solicitud de préstamo si el equipo se encuentra disponible.

---

## Solicitud de préstamo

El formulario solicita la siguiente información:

- Ambiente o destino
- Propósito del préstamo
- Duración estimada

Antes de guardar una solicitud se aplican diferentes reglas de negocio.

El ambiente o destino es obligatorio.

El propósito debe contener entre 10 y 180 caracteres.

La duración debe estar entre 1 y 8 horas.

Solo se pueden solicitar equipos en estado `DISPONIBLE`.

Cuando se crea correctamente una solicitud, el equipo cambia a estado `RESERVADO`.

También se evita que una doble pulsación genere dos solicitudes iguales.

---

## Mis préstamos

La aplicación cuenta con la sección **Mis préstamos**, donde pueden consultarse las solicitudes creadas.

Una solicitud puede manejar diferentes estados, entre ellos:

`SOLICITADA`

`CANCELADA`

`DEVUELTA`

Cuando una solicitud se encuentra en estado `SOLICITADA`, puede cancelarse.

Al cancelar la solicitud, el equipo vuelve a estar disponible.

También se implementó el registro de devolución. Cuando un préstamo es devuelto, el equipo vuelve nuevamente a estado `DISPONIBLE`.

---

## Persistencia local con Room

Inicialmente los datos del proyecto eran simulados y permanecían únicamente durante la ejecución de la aplicación.

El proyecto fue posteriormente evolucionado utilizando Room.

Room permite conservar información como equipos y solicitudes de préstamo aun después de cerrar y volver a abrir la aplicación.

Durante las pruebas se verificó que una solicitud creada permanece almacenada después de cerrar completamente la aplicación y volver a iniciarla.

Room funciona como la fuente local principal de datos de PréstamoLab.

---

## DataStore

También se incorporó DataStore para almacenar preferencias sencillas de la aplicación.

Esto permite separar las preferencias del usuario de la información principal almacenada en Room.

---

## Flow y StateFlow

La aplicación utiliza Flow y StateFlow para mantener una interfaz reactiva.

Cuando cambia información como el estado de un equipo o una solicitud, la interfaz puede actualizarse automáticamente a partir de los datos observados.

Esto permite mantener sincronizadas las diferentes pantallas de la aplicación.

---

## Sincronización

En la pantalla principal se encuentra la opción:

`Sincronizar catálogo`

Esta funcionalidad representa la comunicación de la aplicación con una fuente remota.

El proyecto incorpora Retrofit y OkHttp para la capa de comunicación HTTP.

Para poder trabajar y probar el proyecto sin depender permanentemente de un servidor externo, también se utiliza una fuente remota simulada.

La aplicación registra la fecha y hora de la última sincronización realizada.

---

## Evidencia fotográfica

PréstamoLab permite asociar evidencia fotográfica a un préstamo.

Para esto se utiliza el Photo Picker de Android.

La aplicación no almacena directamente la imagen completa dentro de la base de datos.

En su lugar se conserva la URI que referencia la imagen seleccionada.

Este enfoque evita guardar imágenes como Bitmap o Base64 dentro de Room y permite mantener únicamente la referencia y los metadatos necesarios.

Cuando se selecciona una imagen, en el detalle del préstamo se muestra que existe una evidencia asociada junto con su URI.

---

## Notificaciones y recordatorios

El proyecto también incorpora recordatorios relacionados con los préstamos.

Cuando se crea una solicitud se programa un recordatorio teniendo en cuenta la duración definida para el préstamo.

Para esta funcionalidad se utilizan componentes de Android como:

`AlarmManager`

`PendingIntent`

`BroadcastReceiver`

La clase encargada de recibir el evento programado es:

`LoanReminderReceiver`

En versiones recientes de Android también se solicita el permiso correspondiente para mostrar notificaciones.

Durante las pruebas se verificó mediante ADB que las alarmas de PréstamoLab quedan registradas correctamente en el sistema.

---

## Capacidad adicional del dispositivo

Además de la evidencia fotográfica y las notificaciones, se implementó una funcionalidad relacionada directamente con las capacidades del dispositivo.

La aplicación cuenta con una pantalla llamada **Dispositivo**.

En ella se muestra información como:

- Porcentaje actual de batería.
- Espacio de almacenamiento disponible para la aplicación.

Esta funcionalidad permite demostrar el uso de información proporcionada directamente por el dispositivo Android.

Durante las pruebas realizadas en el emulador se mostraron correctamente tanto el porcentaje de batería como el almacenamiento disponible.

La aplicación no necesita consultar ubicación, contactos ni otros datos personales para esta funcionalidad.

---

## Seguridad y privacidad

El proyecto utiliza datos de demostración y no está diseñado para almacenar información personal real.

Se aplicaron algunas consideraciones básicas de privacidad:

- No almacenar contraseñas o tokens en texto plano.
- No utilizar datos personales reales.
- Solicitar permisos únicamente cuando una funcionalidad los requiere.
- Guardar referencias URI para las evidencias fotográficas.
- Mantener separadas las responsabilidades entre interfaz, lógica y datos.

---

## Pruebas de software

El proyecto contiene pruebas automáticas y pruebas realizadas manualmente.

Se utilizaron pruebas para comprobar reglas de negocio, persistencia y funcionamiento de la interfaz.

Entre las pruebas realizadas se encuentran:

`LoanValidatorTest`

Comprueba las reglas de validación del formulario.

`RemoteApiTest`

Comprueba la capa HTTP utilizando MockWebServer.

`PrestamoDatabaseTest`

Comprueba operaciones relacionadas con Room.

`PrestamoLabUiTest`

Comprueba elementos importantes de la interfaz desarrollada con Jetpack Compose.

También se realizaron pruebas manuales sobre:

- Catálogo
- Detalle
- Solicitud de préstamo
- Validaciones
- Cambio de disponibilidad
- Cancelación
- Devolución
- Persistencia
- Evidencia fotográfica
- Notificaciones
- Información del dispositivo
- Sincronización

---

## Resultados de las pruebas

Durante la validación final del proyecto se ejecutaron correctamente los siguientes comandos:

```bash
./gradlew clean assembleDebug
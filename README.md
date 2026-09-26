# PréstamoLab CTMA

Aplicación móvil desarrollada en Android Studio para consultar equipos disponibles y gestionar solicitudes de préstamo dentro del contexto de formación CTMA.

## 📱 Descripción

PréstamoLab CTMA permite a los aprendices consultar un catálogo de equipos, revisar el detalle de cada equipo y realizar solicitudes de préstamo cuando el equipo está disponible.

La aplicación también permite consultar las solicitudes realizadas, revisar su información y cancelar aquellas que todavía se encuentran en estado `SOLICITADA`.

El proyecto utiliza un repositorio en memoria para almacenar la información durante la ejecución de la aplicación.

---

## 🎯 Objetivo

Desarrollar un incremento funcional de una aplicación móvil que permita:

- Consultar un catálogo de equipos.
- Ver el detalle de un equipo.
- Identificar la disponibilidad de los equipos.
- Crear solicitudes de préstamo.
- Validar la información antes de guardar.
- Consultar las solicitudes realizadas.
- Consultar el detalle de una solicitud.
- Cancelar solicitudes permitidas.
- Actualizar la disponibilidad del equipo después de una solicitud.
- Manejar correctamente identificadores inexistentes sin cerrar la aplicación.

---

## 🛠️ Tecnologías utilizadas

- Kotlin
- Android Studio
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- StateFlow
- Git y GitHub
- Repositorio en memoria

### Configuración principal

- Namespace: `com.example.prestamoslabctma`
- Application ID: `com.example.prestamoslabctma`
- Compile SDK: 36
- Minimum SDK: 24
- Target SDK: 36
- Kotlin: 2.0.21
- Android Gradle Plugin: 9.0.1
- Navigation Compose: 2.8.3

---

# 🏗️ Arquitectura del proyecto

El proyecto está organizado separando modelos, repositorio, lógica de presentación y pantallas.

text
app/
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── prestamoslabctma/
                        ├── data/
                        │   └── repository/
                        │       ├── PrestamoRepository.kt
                        │       └── InMemoryPrestamoRepository.kt
                        │
                        ├── model/
                        │   ├── CategoriaEquipo.kt
                        │   ├── EstadoEquipo.kt
                        │   ├── EstadoSolicitud.kt
                        │   ├── Equipo.kt
                        │   └── SolicitudPrestamo.kt
                        │
                        ├── ui/
                        │   ├── catalogo/
                        │   │   └── CatalogoScreen.kt
                        │   │
                        │   ├── equipo/
                        │   │   └── EquipoDetalleScreen.kt
                        │   │
                        │   ├── solicitud/
                        │   │   └── SolicitudScreen.kt
                        │   │
                        │   └── misprestamos/
                        │       ├── MisSolicitudesScreen.kt
                        │       └── SolicitudDetalleScreen.kt
                        │
                        ├── viewmodel/
                        │   ├── PrestamoUiState.kt
                        │   └── PrestamoViewModel.kt
                        │
                        └── MainActivity.kt


---

📦 Modelo de datos

Equipo

Representa un equipo disponible dentro del catálogo.

Equipo
├── id
├── nombre
├── categoria
└── estado

Categorías

Las categorías disponibles son:

COMPUTO
AUDIOVISUAL
HERRAMIENTA
LABORATORIO
OTRO

Estados del equipo

DISPONIBLE
RESERVADO
PRESTADO

Solicitud de préstamo

Representa una solicitud realizada por el usuario.

SolicitudPrestamo
├── id
├── equipoId
├── ambienteDestino
├── proposito
├── duracionHoras
└── estado

Estados de la solicitud

SOLICITADA
APROBADA
ENTREGADA
DEVUELTA
CANCELADA
RECHAZADA


---

📋 Reglas de negocio

RN-01 — Disponibilidad

Solo se puede solicitar un equipo cuyo estado sea:

DISPONIBLE

Los equipos:

RESERVADO
PRESTADO

no pueden ser solicitados.


---

RN-02 — Ambiente de destino

El ambiente de destino es obligatorio.

No se permite crear una solicitud cuando este campo está vacío.


---

RN-03 — Propósito

El propósito del préstamo debe contener entre:

10 y 180 caracteres

Se validan los límites antes de crear la solicitud.


---

RN-04 — Duración

La duración permitida es de:

1 a 8 horas


---

RN-05 — Creación de solicitudes

El sistema controla el proceso de guardado para evitar múltiples solicitudes generadas por una misma acción.

El botón de guardar se deshabilita mientras se procesa la operación.


---

RN-06 — Reserva del equipo

Cuando una solicitud es creada correctamente, el estado del equipo cambia de:

DISPONIBLE

a:

RESERVADO


---

RN-07 — Cancelación

Una solicitud solamente puede cancelarse cuando su estado es:

SOLICITADA

Cuando se cancela correctamente:

Solicitud → CANCELADA
Equipo → DISPONIBLE


---

🖥️ Pantallas de la aplicación

1. Catálogo de equipos

La pantalla principal permite consultar los equipos registrados.

Muestra:

Nombre del equipo.

Categoría.

Estado.

Acceso al detalle.

Acceso a "Mis solicitudes".



---

2. Detalle del equipo

Permite consultar:

Nombre.

ID.

Categoría.

Estado.

Disponibilidad para préstamo.


Si el equipo está disponible aparece el botón:

Solicitar préstamo

Si no está disponible se muestra un mensaje informativo.


---

3. Solicitar préstamo

Permite registrar:

Ambiente de destino.

Propósito.

Duración.


También contiene validaciones antes de guardar.


---

4. Mis solicitudes

Muestra las solicitudes realizadas durante la ejecución de la aplicación.

Cada solicitud muestra:

ID.

Equipo.

Ambiente.

Duración.

Estado.



---

5. Detalle de solicitud

Permite consultar:

ID de solicitud.

Equipo.

Ambiente de destino.

Propósito.

Duración.

Estado.


Cuando la solicitud está en estado SOLICITADA, aparece la opción para cancelarla.


---

🧭 Navegación

La aplicación utiliza Navigation Compose.

Las rutas principales son:

catalogo
equipo/{equipoId}
solicitar/{equipoId}
mis_solicitudes
solicitud/{solicitudId}

Los identificadores se envían mediante argumentos de navegación.

Ejemplo:

equipo/1

y:

solicitud/1

La aplicación valida los identificadores y muestra mensajes informativos cuando el registro solicitado no existe.


---

🗃️ Repositorio

La aplicación utiliza:

InMemoryPrestamoRepository

Este repositorio mantiene los datos durante la ejecución de la aplicación.

El acceso a los datos se realiza mediante la interfaz:

PrestamoRepository

La interfaz separa la fuente de datos de la lógica utilizada por el ViewModel.


---

🧠 ViewModel

La lógica de presentación se concentra en:

PrestamoViewModel

El ViewModel expone el estado mediante:

StateFlow<PrestamoUiState>

Entre las operaciones principales se encuentran:

obtenerEquipo()
obtenerSolicitud()
crearSolicitud()
cancelarSolicitud()
limpiarMensaje()

La interfaz de usuario no modifica directamente el repositorio.


---

✅ Pruebas realizadas

Se realizaron pruebas funcionales sobre las principales reglas del sistema.

Catálogo

Consulta del catálogo.

Visualización de equipos disponibles.

Visualización de equipos reservados.

Visualización de equipos prestados.


Navegación

Catálogo → detalle del equipo.

Detalle → solicitud.

Catálogo → Mis solicitudes.

Mis solicitudes → detalle de solicitud.

Regreso mediante botones de navegación.


Validaciones

Se comprobó:

Ambiente vacío.

Propósito con menos de 10 caracteres.

Propósito válido.

Propósito de hasta 180 caracteres.

Duración mínima de 1 hora.

Duración máxima de 8 horas.


Solicitudes

Se comprobó:

Creación correcta de una solicitud.

Cambio del equipo a estado RESERVADO.

Visualización de la solicitud en "Mis solicitudes".

Visualización del detalle.

Cancelación de una solicitud SOLICITADA.

Cambio del equipo nuevamente a DISPONIBLE.


Identificadores inexistentes

También se comprobó el comportamiento utilizando identificadores que no existen.

La aplicación muestra mensajes como:

Equipo no encontrado

o:

Solicitud no encontrada

sin cerrar la aplicación.


---

📸 Evidencias

Las evidencias del funcionamiento de la aplicación se encuentran en la carpeta:

Docs/

Entre las evidencias se encuentran:

01_catalogo.png
02_detalle_equipo.png
03_solicitud.png
04_validacion.png
05_solicitud_creada.png
06_equipo_reservado.png
07_mis_solicitudes.png
08_detalle_solicitud.png
09_solicitud_cancelada.png
10_equipo_disponible.png
11_equipo_no_disponible.png
12_equipo_reservado.png

También se incluyen las evidencias adicionales realizadas durante la validación del proyecto.


---

▶️ Ejecución del proyecto

Requisitos

Se requiere:

Android Studio.

JDK compatible con la configuración del proyecto.

Android SDK.

Un emulador Android o dispositivo físico.


Pasos

1. Abrir el proyecto PrestamosLabCTMA en Android Studio.


2. Esperar la sincronización de Gradle.


3. Conectar un dispositivo físico o iniciar un emulador.


4. Ejecutar la aplicación mediante Run.


5. La aplicación inicia en el catálogo de equipos.




---

🔄 Flujo principal

El flujo principal de la aplicación es:

Catálogo
   ↓
Seleccionar equipo
   ↓
Detalle del equipo
   ↓
Solicitar préstamo
   ↓
Completar formulario
   ↓
Validar información
   ↓
Crear solicitud
   ↓
Equipo pasa a RESERVADO
   ↓
Mis solicitudes
   ↓
Detalle de solicitud
   ↓
Cancelar solicitud
   ↓
Solicitud pasa a CANCELADA
   ↓
Equipo vuelve a DISPONIBLE


---

📌 Datos iniciales

La aplicación inicia con equipos de ejemplo para facilitar las pruebas.

Entre ellos:

Computador portátil Lenovo
Video Beam Epson
Multímetro digital
Taladro eléctrico

Los equipos tienen diferentes estados para permitir probar las reglas de disponibilidad.


---

👥 Equipo de trabajo

Proyecto académico desarrollado en el contexto de formación SENA - ADSO.

Proyecto

PréstamoLab CTMA

Tipo

Aplicación móvil Android

Propósito

Gestión de solicitudes de préstamo de equipos


---

📚 Conclusión

PréstamoLab CTMA implementa un flujo funcional para consultar equipos y gestionar solicitudes de préstamo.

El incremento desarrollado incluye catálogo, detalle de equipos, creación de solicitudes, validaciones, consulta de solicitudes, detalle, cancelación y actualización del estado de disponibilidad de los equipos.

La aplicación fue ejecutada y probada mediante diferentes escenarios funcionales, incluyendo casos válidos, casos inválidos y registros inexistentes.

### 📌 Ahora

Guárdalo exactamente como:

text
README.md
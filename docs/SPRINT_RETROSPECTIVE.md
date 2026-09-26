# Sprint Retrospective — PréstamoLab CTMA

## Objetivo

Analizar el trabajo realizado durante el Sprint, identificar aspectos positivos, dificultades encontradas y acciones de mejora para los siguientes incrementos.

## ¿Qué salió bien?

- Se consolidó la estructura principal de la aplicación.
- Se implementó el flujo de catálogo, detalle y solicitud.
- Se incorporaron validaciones de las reglas de negocio.
- Se manejaron equipos y solicitudes inexistentes sin cierre inesperado.
- Se implementó búsqueda y filtro por categoría.
- Se implementó la prevención de solicitudes duplicadas.
- Se crearon pruebas automatizadas para Repository y ViewModel.
- Se ejecutaron 26 pruebas unitarias correctamente.
- Se ejecutaron pruebas instrumentadas en un dispositivo físico.
- Android Lint terminó correctamente después de corregir las observaciones encontradas.
- GitHub Actions automatizó las validaciones del proyecto.
- Se documentaron riesgos, pruebas y trazabilidad.

## ¿Qué dificultades se encontraron?

- Se encontraron observaciones de Android Lint relacionadas con el uso de `StateFlow.value` dentro de la composición.
- Fue necesario corregir la observación y ejecutar nuevamente las validaciones.
- La configuración del entorno Android requirió ajustes para ejecutar las pruebas.
- Algunas funcionalidades de la guía corresponden a incrementos posteriores y todavía no forman parte del incremento actual.

## ¿Qué se mejoró?

- Se reemplazó la lectura directa de `StateFlow.value` por `collectAsState()`.
- Se fortalecieron las pruebas automatizadas.
- Se documentó la trazabilidad entre Historias de Usuario, criterios, riesgos, pruebas y evidencias.
- Se establecieron criterios claros de Definition of Done.
- Se configuró integración continua mediante GitHub Actions.

## Acciones para el siguiente incremento

- Incorporar persistencia local mediante Room.
- Incorporar DataStore para preferencias.
- Utilizar Coroutines y Flow de forma coherente con la arquitectura.
- Preparar la integración con API REST mediante Retrofit y OkHttp.
- Definir claramente la fuente de verdad local y remota.
- Incorporar pruebas de integración para la comunicación con el servicio.
- Mantener las pruebas automatizadas y los controles de calidad en CI.
- Continuar documentando riesgos, defectos y evidencias.

## Conclusión

El Sprint permitió estabilizar el incremento actual y establecer una base técnica y documental para continuar con las funcionalidades de persistencia, sincronización y capacidades adicionales previstas en la guía.

## Estado

Retrospectiva documentada.
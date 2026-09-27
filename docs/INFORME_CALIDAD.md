# Informe ejecutivo de calidad

**Producto:** PréstamoLab CTMA · **Versión:** 0.6.0

El incremento integra catálogo, detalle, solicitud validada, consulta de préstamos, cancelación, devolución, persistencia local, sincronización preparada, evidencia fotográfica, recordatorios y una capacidad física adicional. La arquitectura separa Compose, ViewModel/StateFlow, Repository, Room/DataStore y la capa remota.

Los riesgos principales son duplicación, inconsistencia de disponibilidad, pérdida de datos, IDs inexistentes y fallas remotas. Las mitigaciones incluyen validación desacoplada, guardado protegido, actualización condicional en Room, fuente local canónica y manejo recuperable de errores. Se incluyen pruebas unitarias de límites, integración HTTP con MockWebServer y pruebas instrumentadas de Room/UI.

El pipeline de GitHub Actions está preparado para build, unit tests, Android Lint y publicación del APK. La evidencia definitiva de PASS/FAIL, ejecución del pipeline, screenshots, PRs y reviews debe provenir de una ejecución real y del historial GitHub del equipo.

**Riesgo residual:** la integración remota productiva no tiene un backend institucional real en las guías; por ello el proyecto conserva un mock funcional y una implementación Retrofit preparada para sustituirlo.

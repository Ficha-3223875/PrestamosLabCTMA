# Arquitectura de PréstamoLab CTMA

## Flujo de datos

`Compose UI -> PrestamoViewModel -> PrestamoRepository -> Room / DataStore / Retrofit`

Room es la fuente local canónica. Las pantallas observan `StateFlow`; nunca acceden al DAO ni al cliente HTTP. DataStore conserva preferencias simples. Retrofit sincroniza y traduce fallos en operaciones recuperables.

| Componente | Responsabilidad |
|---|---|
| Compose UI | Representar Loading, Content, Empty, Error y operación; emitir eventos |
| ViewModel | Validar, iniciar corrutinas y exponer UiState/StateFlow |
| Repository | Aplicar reglas, transacciones y sincronización local-first |
| Room | Persistir equipos, solicitudes, devoluciones y metadatos |
| DataStore | Guardar filtro y recordatorios |
| Retrofit/OkHttp | Consumir HTTPS con timeouts y logs seguros |

## Decisiones

- Solicitud y equipo se actualizan en una transacción Room.
- La fotografía se guarda como URI persistible y metadatos.
- El acelerómetro se eligió porque prueba interacción física sin recopilar ubicación ni requerir permisos.
- La sincronización usa estados `LOCAL`, `SUBIENDO`, `SINCRONIZADA` y `FALLIDA`.

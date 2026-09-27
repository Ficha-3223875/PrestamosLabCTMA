# Arquitectura objetivo

```text
Compose UI (catálogo, detalle, solicitud, préstamos, dispositivo)
       ↓ eventos                 ↑ UiState
EquipmentViewModel + StateFlow
       ↓
EquipmentRepository
 ├── Room: EquipmentEntity / LoanEntity / DAO
 ├── DataStore: filtro y última sincronización
 └── RemoteDataSource
      ├── FakeRemoteDataSource (ejecución local del prototipo)
      └── RetrofitRemoteDataSource + OkHttp
```

Room es la fuente local canónica. La UI no manipula la base de datos ni Retrofit. El repositorio decide el origen/actualización de datos. Los identificadores viajan por Navigation Compose y el destino vuelve a consultar el repositorio.

## Estados
Equipo: `DISPONIBLE`, `RESERVADO`, `PRESTADO`.
Solicitud: `SOLICITADA`, `APROBADA`, `ENTREGADA`, `DEVUELTA`, `CANCELADA`, `RECHAZADA`.

El MVP implementa completamente: `SOLICITADA → CANCELADA` y solicitud activa → `DEVUELTA`.

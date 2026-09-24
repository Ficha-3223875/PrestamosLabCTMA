# Contrato API REST

Base URL por ambiente: `BuildConfig.API_BASE_URL`, siempre HTTPS.

| Método | Endpoint | Uso | Respuestas |
|---|---|---|---|
| GET | `/equipos` | Catálogo remoto | 200, 401, 404, 5xx |
| POST | `/solicitudes` | Sincronizar solicitud | 200/201, 400, 401, 409, 5xx |
| POST | `/devoluciones` | Sincronizar devolución | 200/201/204, 400, 401, 404, 5xx |

Los DTO no llegan directamente a Compose. El Repository conserva Room como estado visible. Los fallos dejan el registro en `FALLIDA` para reintento. No se incluyen credenciales; los tokens reales deben inyectarse y nunca registrarse.

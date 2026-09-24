# Seguridad y privacidad

## Permisos y mínimo privilegio

- Internet: requerido por sincronización REST.
- Notificaciones: solicitado únicamente al activar recordatorios en Android 13 o superior.
- Fotografías: OpenDocument permite elegir contenido sin permiso amplio de almacenamiento.
- Acelerómetro: no requiere permiso y no recopila ubicación.
- Cámara, ubicación, Bluetooth y contactos no se solicitan.

## Datos

La base guarda información sintética, solicitudes y URI/metadatos de evidencia. No guarda Bitmap, Base64, contraseñas ni tokens. `allowBackup=false` evita copias automáticas de estos datos del prototipo.

## Red y secretos

La URL exige HTTPS. OkHttp define timeouts. El interceptor usa nivel BASIC solo en debug, NONE en release y redacta `Authorization`. Los secretos reales deben suministrarse fuera del repositorio mediante configuración segura del ambiente.

## Ambientes

`BuildConfig.API_BASE_URL` es el único punto de configuración y se genera desde la propiedad Gradle `API_BASE_URL`. Dev, stage y prod deben suministrar valores CI separados, por ejemplo `./gradlew assembleDebug -PAPI_BASE_URL=https://stage.ejemplo/`, sin incluir credenciales en Git.

## Validación de entradas

Destino obligatorio, propósito 10 a 180, duración 1 a 8, estado permitido para cada transición y evidencia obligatoria para devolución.

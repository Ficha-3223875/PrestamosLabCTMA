# Capacidad física adicional: energía y almacenamiento

Además de Photo Picker, el incremento implementa una capacidad física permitida por la guía: gestión/consulta de **energía y almacenamiento**.

- API batería: `BatteryManager.BATTERY_PROPERTY_CAPACITY`.
- API almacenamiento: `StatFs` sobre el directorio de archivos de la app.
- Pantalla: `device` / `DeviceStatusScreen`.
- Permisos: no requiere ubicación, Bluetooth, contactos ni almacenamiento amplio.
- Privacidad: solo se muestran valores técnicos; no se transmiten ni persisten como datos personales.
- Manejo de error: batería `-1` se presenta como “No disponible”.

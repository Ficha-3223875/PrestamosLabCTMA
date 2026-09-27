# Seguridad y privacidad

- Catálogo y préstamos de demostración usan datos sintéticos.
- No almacenar nombres reales, documentos, contraseñas, tokens ni credenciales.
- `android:usesCleartextTraffic="false"` fuerza evitar HTTP en texto claro.
- URL de API se configura como BuildConfig; el prototipo usa `FakeRemoteDataSource` por defecto.
- Photo Picker limita la selección al recurso elegido por el usuario.
- La base de datos guarda URI/metadatos, no Bitmap/Base64.
- POST_NOTIFICATIONS se solicita únicamente para recordatorios; si se deniega, el flujo de préstamos funciona igual.
- Las entradas del formulario se validan antes de persistir.

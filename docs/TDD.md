# Evidencia de TDD acotado

Regla seleccionada: límites del propósito y duración.

1. **Red:** definir casos que esperan error para propósito de 9/181 caracteres y duración 0/9.
2. **Green:** implementar `LoanValidator.validate` con rangos 10..180 y 1..8.
3. **Refactor:** centralizar mensajes/campos en un mapa de errores independiente de Compose.

Pruebas: `LoanValidatorTest.kt`. La secuencia de commits Red/Green/Refactor debe conservarse en GitHub para que la evidencia temporal sea verificable.

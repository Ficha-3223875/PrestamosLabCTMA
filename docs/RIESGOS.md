# Matriz de riesgos

Escala: Probabilidad (P) e Impacto (I) de 1 a 3. Nivel = P×I.

| ID | Riesgo | P | I | Nivel | Cobertura |
|---|---|---:|---:|---:|---|
| R-01 | Crear solicitudes duplicadas por doble pulsación | 3 | 3 | 9 | Guard `saving` + estado del equipo + TC-07 |
| R-02 | Reservar un equipo ya ocupado | 2 | 3 | 6 | UPDATE condicional Room + TC-08 |
| R-03 | Datos inválidos en formulario | 3 | 2 | 6 | Validator + pruebas 9/10/180/181 y 0/1/8/9 |
| R-04 | ID inexistente produce crash | 2 | 3 | 6 | Estado recuperable + TC-10 |
| R-05 | Perder préstamos al reiniciar | 2 | 3 | 6 | Room + TC-12 |
| R-06 | Falla de red deja UI inconsistente | 2 | 2 | 4 | Local-first + Result + TC-14 |
| R-07 | Guardar imágenes pesadas en BD | 2 | 2 | 4 | Persistir URI/metadatos |
| R-08 | Exponer secretos o HTTP inseguro | 1 | 3 | 3 | Sin tokens; cleartext deshabilitado |
| R-09 | Permiso de notificación denegado | 2 | 1 | 2 | La app sigue funcionando sin notificar |
| R-10 | Capacidad física no disponible | 1 | 1 | 1 | Valor recuperable / texto informativo |

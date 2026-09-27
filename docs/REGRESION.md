# Confirmación y regresión

## Defecto de referencia BUG-03
Riesgo: doble pulsación podría crear dos solicitudes.

**Corrección implementada:** `saving` deshabilita el botón durante la operación y Room cambia el equipo de `DISPONIBLE` a `RESERVADO` mediante UPDATE condicional.

## Confirmación
- Repetir TC-07: dos pulsaciones rápidas deben producir una sola solicitud activa.

## Regresión relacionada
- TC-01 catálogo sigue cargando.
- TC-11 Mis préstamos muestra el registro creado.
- TC-13 cancelar libera el equipo.
- TC-16 devolver libera el equipo.

Los resultados PASS/FAIL se completan con una ejecución real del build entregado.

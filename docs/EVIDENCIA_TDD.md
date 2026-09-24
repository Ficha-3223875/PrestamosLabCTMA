# Evidencia del ciclo TDD

## Regla seleccionada

Una devolución solo puede registrarse cuando la solicitud está `ENTREGADA`, debe exigir evidencia y debe liberar el equipo al completarse.

## Red

Se definieron los escenarios TC-22, TC-23 y TC-24: rechazo antes de entrega, devolución válida y rechazo sin evidencia. Las pruebas están en `PrestamoLabUnitTest`.

## Green

`registrarDevolucion` valida el estado y la URI; después actualiza solicitud y equipo. La implementación en Room ejecuta ambos cambios dentro de `withTransaction`.

## Refactor

La evidencia se agrupó en `EvidenciaDevolucion` y la operación se incorporó al contrato `PrestamoRepository`, permitiendo probarla con el repositorio InMemory sin acoplarla a Compose ni a Android.

La evidencia de ejecución debe anexarse desde el reporte real de `testDebugUnitTest`; este documento describe la correspondencia entre regla, prueba e implementación y no sustituye el resultado del pipeline.

# Matriz de trazabilidad

| HU | Criterio | Riesgo | Caso | Código principal |
|---|---|---|---|---|
| HU-01 | CA-01 | R-02 | TC-01 | `HomeScreen`, `PrestamoDao.observeEquipment` |
| HU-02 | CA-02 | R-04 | TC-02/TC-10 | `PrestamoLabApp`, `loadEquipment` |
| HU-03 | CA-03..CA-08 | R-01/R-02/R-03 | TC-03..TC-09 | `LoanValidator`, `requestLoan`, `RequestLoanScreen` |
| HU-04 | CA-09 | R-05 | TC-11/TC-13 | `LoansScreen`, `LoanDetailScreen`, `cancelLoan` |
| HU-05 | CA-10 | R-02 | TC-16 | `returnLoan` |
| HU-06 | CA-11 | R-05 | TC-12 | Room `PrestamoDatabase` |
| HU-07 | Sincronización recuperable | R-06 | TC-14 | Retrofit/OkHttp + `RemoteApiTest` |
| HU-08 | CA-12 | R-07 | TC-15 | Photo Picker + `evidenceUri` |
| HU-09 | Recordatorio | R-09 | TC-17 | `LoanReminder` |
| HU-10 | Capacidad física | R-10 | TC-18 | `DeviceCapability`, `DeviceStatusScreen` |

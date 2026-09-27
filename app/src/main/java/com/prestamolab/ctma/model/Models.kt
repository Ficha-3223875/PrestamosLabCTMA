package com.prestamolab.ctma.model

enum class EquipmentState { DISPONIBLE, RESERVADO, PRESTADO }
enum class LoanState { SOLICITADA, APROBADA, ENTREGADA, DEVUELTA, CANCELADA, RECHAZADA }

data class Equipment(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val state: EquipmentState = EquipmentState.DISPONIBLE
) {
    val available: Boolean get() = state == EquipmentState.DISPONIBLE
}

data class Loan(
    val id: Int,
    val equipmentId: Int,
    val equipmentName: String,
    val destination: String,
    val purpose: String,
    val durationHours: Int,
    val state: LoanState,
    val evidenceUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

data class LoanDraft(
    val equipmentId: Int,
    val destination: String,
    val purpose: String,
    val durationHours: Int
)

sealed interface LoanResult {
    data class Success(val loanId: Int) : LoanResult
    data class ValidationError(val errors: Map<String, String>) : LoanResult
    data object NotAvailable : LoanResult
    data object NotFound : LoanResult
    data object Duplicate : LoanResult
    data class Failure(val message: String) : LoanResult
}

package com.prestamolab.ctma.model

data class Equipment(
    val id: Int,
    val name: String,
    val description: String,
    val available: Boolean = true
)

sealed interface LoanResult {
    data object Success : LoanResult
    data object NotAvailable : LoanResult
}

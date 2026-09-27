package com.prestamolab.ctma.util

import com.prestamolab.ctma.model.LoanDraft

object LoanValidator {
    fun validate(draft: LoanDraft): Map<String, String> {
        val errors = linkedMapOf<String, String>()
        if (draft.destination.isBlank()) errors["destination"] = "El ambiente o destino es obligatorio."
        val purposeLength = draft.purpose.trim().length
        if (purposeLength !in 10..180) errors["purpose"] = "El propósito debe tener entre 10 y 180 caracteres."
        if (draft.durationHours !in 1..8) errors["duration"] = "La duración debe estar entre 1 y 8 horas."
        return errors
    }
}

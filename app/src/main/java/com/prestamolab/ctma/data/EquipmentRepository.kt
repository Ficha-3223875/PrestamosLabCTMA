package com.prestamolab.ctma.data

import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.LoanResult

interface EquipmentRepository {
    suspend fun getEquipment(): List<Equipment>
    suspend fun requestLoan(equipmentId: Int): LoanResult
}

/**
 * Repository temporal en memoria. Puede reemplazarse posteriormente por Room/API
 * sin cambiar la UI ni el ViewModel.
 */
class InMemoryEquipmentRepository : EquipmentRepository {
    private val equipment = mutableListOf(
        Equipment(1, "Multímetro digital", "Equipo para mediciones eléctricas."),
        Equipment(2, "Taladro", "Herramienta eléctrica de formación."),
        Equipment(3, "Juego de destornilladores", "Kit de herramientas manuales.")
    )

    override suspend fun getEquipment(): List<Equipment> = equipment.toList()

    override suspend fun requestLoan(equipmentId: Int): LoanResult {
        val index = equipment.indexOfFirst { it.id == equipmentId }
        if (index == -1 || !equipment[index].available) return LoanResult.NotAvailable

        equipment[index] = equipment[index].copy(available = false)
        return LoanResult.Success
    }
}

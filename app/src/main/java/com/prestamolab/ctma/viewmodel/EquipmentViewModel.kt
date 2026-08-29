package com.prestamolab.ctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prestamolab.ctma.data.EquipmentRepository
import com.prestamolab.ctma.data.InMemoryEquipmentRepository
import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.LoanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EquipmentUiState(
    val equipment: List<Equipment> = emptyList(),
    val selectedEquipment: Equipment? = null,
    val message: String? = null
)

class EquipmentViewModel(
    private val repository: EquipmentRepository = InMemoryEquipmentRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(EquipmentUiState())
    val uiState: StateFlow<EquipmentUiState> = _uiState.asStateFlow()

    init {
        loadEquipment()
    }

    private fun loadEquipment() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(equipment = repository.getEquipment())
        }
    }

    fun select(item: Equipment) {
        _uiState.value = _uiState.value.copy(selectedEquipment = item, message = null)
    }

    fun requestLoan() {
        val selected = _uiState.value.selectedEquipment ?: return

        viewModelScope.launch {
            when (repository.requestLoan(selected.id)) {
                LoanResult.Success -> {
                    val updated = repository.getEquipment()
                    _uiState.value = _uiState.value.copy(
                        equipment = updated,
                        selectedEquipment = updated.firstOrNull { it.id == selected.id },
                        message = "Préstamo solicitado correctamente."
                    )
                }
                LoanResult.NotAvailable -> {
                    _uiState.value = _uiState.value.copy(
                        message = "El equipo no está disponible."
                    )
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
}

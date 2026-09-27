package com.prestamolab.ctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prestamolab.ctma.data.EquipmentRepository
import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.Loan
import com.prestamolab.ctma.model.LoanDraft
import com.prestamolab.ctma.model.LoanResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface ScreenStatus {
    data object Loading : ScreenStatus
    data object Content : ScreenStatus
    data class Error(val message: String) : ScreenStatus
}

data class PrestamoUiState(
    val equipment: List<Equipment> = emptyList(),
    val loans: List<Loan> = emptyList(),
    val selectedEquipment: Equipment? = null,
    val selectedLoan: Loan? = null,
    val availableOnly: Boolean = false,
    val lastSync: Long = 0L,
    val message: String? = null,
    val formErrors: Map<String, String> = emptyMap(),
    val saving: Boolean = false,
    val status: ScreenStatus = ScreenStatus.Loading
)

private data class ObservedData(val equipment: List<Equipment>, val loans: List<Loan>, val filter: Boolean, val sync: Long)

class EquipmentViewModel(private val repository: EquipmentRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(PrestamoUiState())
    val uiState: StateFlow<PrestamoUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch { repository.seedIfNeeded() }
        viewModelScope.launch {
            combine(repository.equipment, repository.loans, repository.availableOnly, repository.lastSync) { eq, loans, filter, sync ->
                ObservedData(eq, loans, filter, sync)
            }.collect { values ->
                _uiState.value = _uiState.value.copy(
                    equipment = values.equipment,
                    loans = values.loans,
                    availableOnly = values.filter,
                    lastSync = values.sync,
                    status = ScreenStatus.Content
                )
            }
        }
    }

    fun loadEquipment(id: Int) = viewModelScope.launch {
        val item = repository.equipmentById(id)
        _uiState.value = _uiState.value.copy(
            selectedEquipment = item,
            message = if (item == null) "No se encontró el equipo solicitado." else null,
            formErrors = emptyMap()
        )
    }

    fun loadLoan(id: Int) = viewModelScope.launch {
        val item = repository.loanById(id)
        _uiState.value = _uiState.value.copy(
            selectedLoan = item,
            message = if (item == null) "No se encontró la solicitud." else null
        )
    }

    fun submitLoan(destination: String, purpose: String, durationText: String, onSuccess: (Int, Int) -> Unit) {
        val equipment = _uiState.value.selectedEquipment ?: return
        if (_uiState.value.saving) return
        val duration = durationText.toIntOrNull() ?: 0
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(saving = true, formErrors = emptyMap(), message = null)
            when (val result = repository.requestLoan(LoanDraft(equipment.id, destination, purpose, duration))) {
                is LoanResult.Success -> {
                    _uiState.value = _uiState.value.copy(saving = false, message = "Solicitud registrada correctamente.")
                    loadEquipment(equipment.id)
                    onSuccess(result.loanId, duration)
                }
                is LoanResult.ValidationError -> _uiState.value = _uiState.value.copy(saving = false, formErrors = result.errors)
                LoanResult.NotAvailable -> _uiState.value = _uiState.value.copy(saving = false, message = "El equipo ya no está disponible.")
                LoanResult.NotFound -> _uiState.value = _uiState.value.copy(saving = false, message = "El equipo no existe.")
                LoanResult.Duplicate -> _uiState.value = _uiState.value.copy(saving = false, message = "Ya existe una solicitud activa para este equipo.")
                is LoanResult.Failure -> _uiState.value = _uiState.value.copy(saving = false, message = result.message)
            }
        }
    }

    fun cancelLoan(id: Int) = viewModelScope.launch {
        val ok = repository.cancelLoan(id)
        _uiState.value = _uiState.value.copy(message = if (ok) "Solicitud cancelada." else "Esta solicitud ya no puede cancelarse.")
        loadLoan(id)
    }

    fun attachEvidence(id: Int, uri: String?) = viewModelScope.launch {
        repository.attachEvidence(id, uri)
        _uiState.value = _uiState.value.copy(message = if (uri != null) "Evidencia asociada." else "Evidencia eliminada.")
        loadLoan(id)
    }

    fun returnLoan(id: Int) = viewModelScope.launch {
        val ok = repository.returnLoan(id)
        _uiState.value = _uiState.value.copy(message = if (ok) "Devolución registrada y equipo liberado." else "No es posible registrar la devolución.")
        loadLoan(id)
    }

    fun setAvailableOnly(value: Boolean) = viewModelScope.launch { repository.setAvailableOnly(value) }

    fun sync() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(message = "Sincronizando catálogo…")
        val result = repository.sync()
        _uiState.value = _uiState.value.copy(message = result.fold({ "Sincronización completada." }, { "No fue posible sincronizar: ${it.message}" }))
    }

    fun clearMessage() { _uiState.value = _uiState.value.copy(message = null) }

    class Factory(private val repository: EquipmentRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = EquipmentViewModel(repository) as T
    }
}

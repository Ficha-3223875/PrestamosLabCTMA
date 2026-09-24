package com.example.prestamoslabctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prestamoslabctma.data.local.Preferencias
import com.example.prestamoslabctma.data.local.PreferenciasUsuario
import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.repository.EvidenciaDevolucion
import com.example.prestamoslabctma.repository.PrestamoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ValidacionSolicitud(val ambienteError: String? = null, val propositoError: String? = null, val duracionError: String? = null) {
    val esValida get() = ambienteError == null && propositoError == null && duracionError == null
}

data class PrestamoUiState(
    val catalogo: EstadoCarga<List<Equipo>> = EstadoCarga.Cargando,
    val solicitudes: EstadoCarga<List<SolicitudPrestamo>> = EstadoCarga.Cargando,
    val preferencias: Preferencias = Preferencias(),
    val operacionEnCurso: Boolean = false,
    val mensaje: String? = null
)

class PrestamoViewModel(
    private val repository: PrestamoRepository,
    private val preferenciasUsuario: PreferenciasUsuario? = null
) : ViewModel() {
    private val _uiState = MutableStateFlow(PrestamoUiState())
    val uiState: StateFlow<PrestamoUiState> = _uiState.asStateFlow()
    val equipos: StateFlow<List<Equipo>> = repository.equipos.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
    val solicitudes: StateFlow<List<SolicitudPrestamo>> = repository.solicitudes.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { runCatching { repository.inicializar() }.onFailure(::mostrarError) }
        viewModelScope.launch {
            repository.equipos
                .catch { _uiState.update { s -> s.copy(catalogo = EstadoCarga.Error(mensajeDe(it))) } }
                .collect { lista -> _uiState.update { it.copy(catalogo = if (lista.isEmpty()) EstadoCarga.Vacio else EstadoCarga.Contenido(lista)) } }
        }
        viewModelScope.launch {
            repository.solicitudes
                .catch { _uiState.update { s -> s.copy(solicitudes = EstadoCarga.Error(mensajeDe(it))) } }
                .collect { lista -> _uiState.update { it.copy(solicitudes = if (lista.isEmpty()) EstadoCarga.Vacio else EstadoCarga.Contenido(lista)) } }
        }
        preferenciasUsuario?.let { servicio ->
            viewModelScope.launch { servicio.flujo.collect { p -> _uiState.update { it.copy(preferencias = p) } } }
        }
    }

    fun validar(ambiente: String, proposito: String, duracionTexto: String): ValidacionSolicitud {
        val horas = duracionTexto.toIntOrNull()
        return ValidacionSolicitud(
            ambienteError = if (ambiente.isBlank()) "El ambiente o destino es obligatorio" else null,
            propositoError = if (proposito.trim().length !in 10..180) "El propósito debe tener entre 10 y 180 caracteres" else null,
            duracionError = if (horas == null || horas !in 1..8) "La duración debe estar entre 1 y 8 horas" else null
        )
    }

    fun crear(equipoId: Int, ambiente: String, proposito: String, duracionTexto: String, alFinalizar: (Boolean) -> Unit = {}) {
        val validacion = validar(ambiente, proposito, duracionTexto)
        if (!validacion.esValida) { mensaje("Corrige los campos indicados"); alFinalizar(false); return }
        ejecutar("Solicitud creada y guardada localmente", alFinalizar) {
            repository.crearSolicitud(equipoId, ambiente, proposito, duracionTexto.toInt()).getOrThrow()
        }
    }

    fun cancelar(id: Int) = ejecutar("Solicitud cancelada. El equipo vuelve a estar DISPONIBLE") { repository.cancelarSolicitud(id).getOrThrow() }
    fun entregar(id: Int, alFinalizar: (Boolean) -> Unit = {}) = ejecutar("Préstamo marcado como ENTREGADO", alFinalizar) { repository.registrarEntrega(id).getOrThrow() }
    fun devolver(id: Int, evidencia: EvidenciaDevolucion) = ejecutar("Devolución registrada y equipo liberado") { repository.registrarDevolucion(id, evidencia).getOrThrow() }
    fun sincronizar() = ejecutar("Sincronización completada") { repository.sincronizar().getOrThrow() }
    fun guardarFiltro(valor: String) { preferenciasUsuario?.let { viewModelScope.launch { it.guardarFiltro(valor) } } }
    fun guardarRecordatorios(valor: Boolean) { preferenciasUsuario?.let { viewModelScope.launch { it.guardarRecordatorios(valor) } } }
    fun limpiarMensaje() { _uiState.update { it.copy(mensaje = null) } }

    private fun ejecutar(exito: String, alFinalizar: (Boolean) -> Unit = {}, bloque: suspend () -> Unit) {
        if (_uiState.value.operacionEnCurso) return
        viewModelScope.launch {
            _uiState.update { it.copy(operacionEnCurso = true, mensaje = null) }
            try {
                bloque()
                _uiState.update { it.copy(operacionEnCurso = false, mensaje = exito) }
                alFinalizar(true)
            } catch (e: CancellationException) { throw e }
            catch (e: Exception) {
                _uiState.update { it.copy(operacionEnCurso = false, mensaje = mensajeDe(e)) }
                alFinalizar(false)
            }
        }
    }

    private fun mensaje(texto: String) { _uiState.update { it.copy(mensaje = texto) } }
    private fun mostrarError(error: Throwable) = mensaje(mensajeDe(error))
    private fun mensajeDe(error: Throwable) = error.message ?: "Ocurrió un error. Intenta nuevamente."
}

class PrestamoViewModelFactory(
    private val repository: PrestamoRepository,
    private val preferencias: PreferenciasUsuario? = null
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PrestamoViewModel(repository, preferencias) as T
}

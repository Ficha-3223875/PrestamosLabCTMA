
package com.example.prestamolab_ctma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.prestamolab_ctma.data.local.DataStoreManager
import com.example.prestamolab_ctma.data.repository.PrestamoRepository
import com.example.prestamolab_ctma.model.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface LoadState {
 data object Loading : LoadState
 data object Empty : LoadState
 data object Content : LoadState
 data class Error(val message: String) : LoadState
}

data class PrestamoUiState(
 val equipos: List<Equipo> = emptyList(),
 val solicitudes: List<SolicitudPrestamo> = emptyList(),
 val filtroCategoria: String = "TODAS",
 val loadState: LoadState = LoadState.Loading,
 val mensaje: String? = null
)

class PrestamoViewModel(
 private val repository: PrestamoRepository,
 private val preferences: DataStoreManager
) : ViewModel() {

 private val _ui = MutableStateFlow(PrestamoUiState())
 val uiState: StateFlow<PrestamoUiState> = _ui.asStateFlow()

 init {
  viewModelScope.launch {
   launch {
    preferences.filtroCategoria.collect { valor ->
     _ui.update {
      it.copy(filtroCategoria = valor)
     }
    }
   }

   runCatching {
    repository.inicializar()
   }.onFailure { error ->
    _ui.update { estado ->
     estado.copy(
      loadState = LoadState.Error(
       error.message ?: "Error al inicializar"
      )
     )
    }
   }

   combine(
    repository.observarEquipos(),
    repository.observarSolicitudes()
   ) { equipos, solicitudes ->
    equipos to solicitudes
   }.catch { error ->
    _ui.update { estado ->
     estado.copy(
      loadState = LoadState.Error(
       error.message ?: "Error al cargar los datos"
      )
     )
    }
   }.collect { (equipos, solicitudes) ->
    _ui.update { estado ->
     estado.copy(
      equipos = equipos,
      solicitudes = solicitudes,
      loadState = if (equipos.isEmpty()) {
       LoadState.Empty
      } else {
       LoadState.Content
      }
     )
    }
   }
  }
 }

 fun equipo(
  id: Int,
  onResult: (Equipo?) -> Unit
 ) = viewModelScope.launch {
  onResult(repository.obtenerEquipo(id))
 }

 fun solicitud(
  id: Int,
  onResult: (SolicitudPrestamo?) -> Unit
 ) = viewModelScope.launch {
  onResult(repository.obtenerSolicitud(id))
 }

 fun crearSolicitud(
  e: Int,
  d: String,
  p: String,
  h: Int,
  onSuccess: () -> Unit = {}
 ) = viewModelScope.launch {
  val resultado = repository.crearSolicitud(e, d, p, h)

  _ui.update { estado ->
   estado.copy(
    mensaje = resultado.fold(
     onSuccess = { solicitud ->
      "Solicitud #${solicitud.id} creada correctamente."
     },
     onFailure = { error ->
      error.message ?: "Error al crear la solicitud"
     }
    )
   )
  }

  if (resultado.isSuccess) {
   onSuccess()
  }
 }

 fun cancelarSolicitud(id: Int) = viewModelScope.launch {
  val resultado = repository.cancelarSolicitud(id)

  _ui.update { estado ->
   estado.copy(
    mensaje = resultado.fold(
     onSuccess = {
      "Solicitud cancelada."
     },
     onFailure = { error ->
      error.message ?: "Error al cancelar la solicitud"
     }
    )
   )
  }
 }

 fun guardarEvidencia(
  id: Int,
  uri: String,
  nombre: String
 ) = viewModelScope.launch {
  val resultado = repository.guardarEvidencia(id, uri, nombre)

  _ui.update { estado ->
   estado.copy(
    mensaje = resultado.fold(
     onSuccess = {
      "Evidencia guardada localmente."
     },
     onFailure = { error ->
      error.message ?: "Error al guardar la evidencia"
     }
    )
   )
  }
 }

 fun guardarUbicacion(
  id: Int,
  lat: Double,
  lon: Double
 ) = viewModelScope.launch {
  repository.guardarUbicacion(id, lat, lon)
 }

 fun sincronizar() = viewModelScope.launch {
  val resultado = repository.sincronizar()

  _ui.update { estado ->
   estado.copy(
    mensaje = resultado.fold(
     onSuccess = {
      "Sincronización completada."
     },
     onFailure = { error ->
      "Sincronización no disponible: ${
       error.message ?: "Error desconocido"
      }"
     }
    )
   )
  }
 }

 fun guardarFiltro(v: String) = viewModelScope.launch {
  preferences.guardarFiltro(v)
 }

 fun limpiarMensaje() {
  _ui.update { estado ->
   estado.copy(mensaje = null)
  }
 }

 class Factory(
  private val r: PrestamoRepository,
  private val p: DataStoreManager
 ) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(
   c: Class<T>
  ): T {
   return PrestamoViewModel(r, p) as T
  }
 }
}
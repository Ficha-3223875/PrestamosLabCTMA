package com.example.prestamoslabctma.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.prestamoslabctma.data.repository.RemoteRepositoryProvider
import com.example.prestamoslabctma.data.repository.RepositoryProvider
import com.example.prestamoslabctma.data.repository.RoomPrestamoRepository
import com.example.prestamoslabctma.device.PrestamoNotificationManager
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PrestamoViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val _uiState =
        MutableStateFlow(
            PrestamoUiState()
        )

    val uiState: StateFlow<PrestamoUiState> =
        _uiState.asStateFlow()

    private val repository:
            RoomPrestamoRepository by lazy {
        RepositoryProvider.obtenerRepository(
            getApplication<Application>()
        )
    }

    private val remoteRepository =
        RemoteRepositoryProvider.repository

    private val notificationManager =
        PrestamoNotificationManager(
            getApplication<Application>()
        )

    init {
        cargarDatos()
    }

    private fun cargarDatos() {

        // ============================
        // EQUIPOS DESDE FASTAPI
        // ============================

        viewModelScope.launch {

            try {

                val equipos =
                    remoteRepository.obtenerEquipos()

                Log.d(
                    "PRESTAMOLAB_API",
                    "EQUIPOS DESDE FASTAPI: ${equipos.size}"
                )

                _uiState.update {
                    it.copy(
                        equipos = equipos,
                        mensaje = null
                    )
                }

            } catch (error: Exception) {

                Log.e(
                    "PRESTAMOLAB_API",
                    "ERROR CARGANDO EQUIPOS DESDE FASTAPI",
                    error
                )

                // Respaldo local con Room
                try {

                    repository.cargarDatosIniciales()

                    repository
                        .obtenerEquipos()
                        .catch { errorLocal ->

                            Log.e(
                                "PRESTAMOLAB",
                                "ERROR EN FLOW LOCAL DE EQUIPOS",
                                errorLocal
                            )

                            _uiState.update {
                                it.copy(
                                    mensaje =
                                        "No fue posible cargar los equipos."
                                )
                            }
                        }
                        .collect { equiposLocal ->

                            Log.d(
                                "PRESTAMOLAB",
                                "EQUIPOS DESDE ROOM: ${equiposLocal.size}"
                            )

                            _uiState.update {
                                it.copy(
                                    equipos = equiposLocal,
                                    mensaje =
                                        "Sin conexión con el servidor. Usando datos locales."
                                )
                            }
                        }

                } catch (errorLocal: Exception) {

                    Log.e(
                        "PRESTAMOLAB",
                        "ERROR CARGANDO DATOS LOCALES",
                        errorLocal
                    )

                    _uiState.update {
                        it.copy(
                            mensaje =
                                "No fue posible cargar los equipos."
                        )
                    }
                }
            }
        }

        // ============================
        // SOLICITUDES DESDE FASTAPI
        // ============================

        viewModelScope.launch {

            try {

                val solicitudes =
                    remoteRepository.obtenerSolicitudes()

                Log.d(
                    "PRESTAMOLAB_API",
                    "SOLICITUDES DESDE FASTAPI: ${solicitudes.size}"
                )

                _uiState.update {
                    it.copy(
                        solicitudes = solicitudes
                    )
                }

            } catch (error: Exception) {

                Log.e(
                    "PRESTAMOLAB_API",
                    "ERROR CARGANDO SOLICITUDES DESDE FASTAPI",
                    error
                )

                // Respaldo local con Room
                try {

                    repository
                        .obtenerSolicitudes()
                        .catch { errorLocal ->

                            Log.e(
                                "PRESTAMOLAB",
                                "ERROR EN FLOW LOCAL DE SOLICITUDES",
                                errorLocal
                            )

                            _uiState.update {
                                it.copy(
                                    mensaje =
                                        "No fue posible cargar las solicitudes."
                                )
                            }
                        }
                        .collect { solicitudesLocal ->

                            Log.d(
                                "PRESTAMOLAB",
                                "SOLICITUDES DESDE ROOM: ${solicitudesLocal.size}"
                            )

                            _uiState.update {
                                it.copy(
                                    solicitudes = solicitudesLocal
                                )
                            }
                        }

                } catch (errorLocal: Exception) {

                    Log.e(
                        "PRESTAMOLAB",
                        "ERROR CARGANDO SOLICITUDES LOCALES",
                        errorLocal
                    )
                }
            }
        }
    }

    fun obtenerEquipo(
        id: Int
    ) =
        _uiState.value.equipos.find {
            it.id == id
        }

    fun obtenerSolicitud(
        id: Int
    ) =
        _uiState.value.solicitudes.find {
            it.id == id
        }

    fun crearSolicitud(
        equipoId: Int,
        ambienteDestino: String,
        proposito: String,
        duracionHoras: Int,
        onResultado: (Boolean) -> Unit = {}
    ) {

        if (_uiState.value.guardando) {
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    guardando = true,
                    mensaje = null
                )
            }

            val solicitud =
                SolicitudPrestamo(
                    id = 0,
                    equipoId = equipoId,
                    ambienteDestino =
                        ambienteDestino.trim(),
                    proposito =
                        proposito.trim(),
                    duracionHoras =
                        duracionHoras,
                    estado =
                        EstadoSolicitud.SOLICITADA
                )

            try {

                // ==================================
                // CREAR SOLICITUD EN FASTAPI
                // ==================================

                val creada =
                    remoteRepository.crearSolicitud(
                        solicitud
                    )

                Log.d(
                    "PRESTAMOLAB_API",
                    "SOLICITUD CREADA EN FASTAPI: ${creada.id}"
                )

                // Agregar la solicitud creada
                // inmediatamente al estado de la app.
                _uiState.update { estadoActual ->

                    estadoActual.copy(
                        solicitudes =
                            listOf(creada) +
                                    estadoActual.solicitudes,
                        equipos =
                            estadoActual.equipos.map { equipo ->

                                if (equipo.id == equipoId) {
                                    equipo.copy(
                                        estado =
                                            EstadoEquipo.RESERVADO
                                    )
                                } else {
                                    equipo
                                }
                            },
                        mensaje =
                            "Solicitud creada correctamente.",
                        guardando = false
                    )
                }

                notificationManager.mostrarNotificacion(
                    titulo = "Solicitud creada",
                    mensaje =
                        "Tu solicitud de préstamo fue registrada correctamente."
                )

                onResultado(true)

            } catch (error: Exception) {

                Log.e(
                    "PRESTAMOLAB_API",
                    "ERROR AL CREAR SOLICITUD EN FASTAPI",
                    error
                )

                _uiState.update {
                    it.copy(
                        mensaje =
                            "No fue posible crear la solicitud en el servidor.",
                        guardando = false
                    )
                }

                onResultado(false)
            }
        }
    }

    fun cancelarSolicitud(
        id: Int
    ) {

        viewModelScope.launch {

            try {

                val cancelada =
                    repository.cancelarSolicitud(id)

                if (cancelada) {

                    _uiState.update {
                        it.copy(
                            mensaje =
                                "Solicitud cancelada correctamente."
                        )
                    }

                    notificationManager.mostrarNotificacion(
                        titulo = "Solicitud cancelada",
                        mensaje =
                            "La solicitud fue cancelada correctamente."
                    )

                } else {

                    _uiState.update {
                        it.copy(
                            mensaje =
                                "La solicitud no se puede cancelar."
                        )
                    }
                }

            } catch (error: Exception) {

                Log.e(
                    "PRESTAMOLAB",
                    "ERROR AL CANCELAR SOLICITUD",
                    error
                )

                _uiState.update {
                    it.copy(
                        mensaje =
                            "No fue posible cancelar la solicitud."
                    )
                }
            }
        }
    }

    fun devolverPrestamo(
        id: Int
    ) {

        viewModelScope.launch {

            try {

                val devuelto =
                    repository.devolverPrestamo(id)

                if (devuelto) {

                    _uiState.update {
                        it.copy(
                            mensaje =
                                "Préstamo devuelto correctamente."
                        )
                    }

                    notificationManager.mostrarNotificacion(
                        titulo = "Préstamo devuelto",
                        mensaje =
                            "La devolución fue registrada correctamente."
                    )

                } else {

                    _uiState.update {
                        it.copy(
                            mensaje =
                                "El préstamo no se puede devolver en su estado actual."
                        )
                    }
                }

            } catch (error: Exception) {

                Log.e(
                    "PRESTAMOLAB",
                    "ERROR AL DEVOLVER PRESTAMO",
                    error
                )

                _uiState.update {
                    it.copy(
                        mensaje =
                            "No fue posible registrar la devolución."
                    )
                }
            }
        }
    }

    fun limpiarMensaje() {

        _uiState.update {
            it.copy(
                mensaje = null
            )
        }
    }
}
package com.example.prestamoslabctma.model

enum class CategoriaEquipo(val etiqueta: String) { ELECTRONICA("Electrónica"), MEDICION("Medición"), COMPUTO("Cómputo"), AUDIOVISUAL("Audiovisual"), HERRAMIENTA("Herramienta") }
enum class EstadoEquipo { DISPONIBLE, RESERVADO, PRESTADO }
enum class EstadoSolicitud { SOLICITADA, APROBADA, ENTREGADA, DEVUELTA, CANCELADA, RECHAZADA }
enum class EstadoSincronizacion { LOCAL, SUBIENDO, SINCRONIZADA, FALLIDA }

data class Equipo(val id: Int, val nombre: String, val categoria: CategoriaEquipo, val estado: EstadoEquipo)
data class SolicitudPrestamo(
    val id: Int,
    val equipoId: Int,
    val ambienteDestino: String,
    val proposito: String,
    val duracionHoras: Int,
    val estado: EstadoSolicitud,
    val creadaEn: Long = System.currentTimeMillis(),
    val devolucionEn: Long? = null,
    val evidenciaUri: String? = null,
    val evidenciaNombre: String? = null,
    val evidenciaTipo: String? = null,
    val estadoSincronizacion: EstadoSincronizacion = EstadoSincronizacion.LOCAL,
    val sensorAcelerometroVerificado: Boolean = false
)

sealed interface EstadoCarga<out T> {
    data object Cargando : EstadoCarga<Nothing>
    data class Contenido<T>(val datos: T) : EstadoCarga<T>
    data object Vacio : EstadoCarga<Nothing>
    data class Error(val mensaje: String) : EstadoCarga<Nothing>
}

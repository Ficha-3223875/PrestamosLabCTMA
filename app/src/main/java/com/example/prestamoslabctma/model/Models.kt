package com.example.prestamoslabctma.model

enum class CategoriaEquipo(val etiqueta: String) { ELECTRONICA("Electrónica"), MEDICION("Medición"), COMPUTO("Cómputo"), AUDIOVISUAL("Audiovisual"), HERRAMIENTA("Herramienta") }
enum class EstadoEquipo { DISPONIBLE, RESERVADO, PRESTADO }
enum class EstadoSolicitud { SOLICITADA, APROBADA, ENTREGADA, DEVUELTA, CANCELADA, RECHAZADA }

data class Equipo(val id: Int, val nombre: String, val categoria: CategoriaEquipo, val estado: EstadoEquipo)
data class SolicitudPrestamo(val id: Int, val equipoId: Int, val ambienteDestino: String, val proposito: String, val duracionHoras: Int, val estado: EstadoSolicitud)

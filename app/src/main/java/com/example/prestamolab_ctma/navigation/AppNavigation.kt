package com.example.prestamolab_ctma.navigation

sealed class AppScreen {
    data object Catalogo : AppScreen()
    data class DetalleEquipo(val equipoId: Int) : AppScreen()
    data class Solicitar(val equipoId: Int) : AppScreen()
    data object MisSolicitudes : AppScreen()
    data class DetalleSolicitud(val solicitudId: Int) : AppScreen()
}

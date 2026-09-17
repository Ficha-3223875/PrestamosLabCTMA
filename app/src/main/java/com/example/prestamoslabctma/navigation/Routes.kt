package com.example.prestamoslabctma.navigation
object Routes {
    const val CATALOGO = "catalogo"; const val MIS_SOLICITUDES = "mis_solicitudes"
    const val EQUIPO = "equipo/{equipoId}"; const val SOLICITAR = "solicitar/{equipoId}"; const val SOLICITUD = "solicitud/{solicitudId}"
    fun equipo(id:Int)="equipo/$id"; fun solicitar(id:Int)="solicitar/$id"; fun solicitud(id:Int)="solicitud/$id"
}

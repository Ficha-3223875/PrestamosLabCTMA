package com.example.prestamolab_ctma.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.prestamolab_ctma.model.*

@Entity(tableName = "equipos")
data class EquipoEntity(@PrimaryKey val id:Int,val nombre:String,val categoria:String,val estado:String,val descripcion:String)
@Entity(tableName = "solicitudes")
data class SolicitudEntity(@PrimaryKey val id:Int,val equipoId:Int,val ambienteDestino:String,val proposito:String,val duracionHoras:Int,val estado:String,val evidenciaUri:String?=null,val evidenciaNombre:String?=null,val latitud:Double?=null,val longitud:Double?=null)
fun EquipoEntity.toDomain()=Equipo(id,nombre,runCatching{CategoriaEquipo.valueOf(categoria)}.getOrDefault(CategoriaEquipo.ELECTRONICA),runCatching{EstadoEquipo.valueOf(estado)}.getOrDefault(EstadoEquipo.DISPONIBLE),descripcion)
fun Equipo.toEntity()=EquipoEntity(id,nombre,categoria.name,estado.name,descripcion)
fun SolicitudEntity.toDomain()=SolicitudPrestamo(id,equipoId,ambienteDestino,proposito,duracionHoras,runCatching{EstadoSolicitud.valueOf(estado)}.getOrDefault(EstadoSolicitud.SOLICITADA),evidenciaUri,evidenciaNombre,latitud,longitud)
fun SolicitudPrestamo.toEntity()=SolicitudEntity(id,equipoId,ambienteDestino,proposito,duracionHoras,estado.name,evidenciaUri,evidenciaNombre,latitud,longitud)

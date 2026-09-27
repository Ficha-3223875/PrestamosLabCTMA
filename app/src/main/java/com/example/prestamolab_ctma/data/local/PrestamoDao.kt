package com.example.prestamolab_ctma.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao interface PrestamoDao {
 @Query("SELECT * FROM equipos ORDER BY nombre") fun observarEquipos():Flow<List<EquipoEntity>>
 @Query("SELECT * FROM solicitudes ORDER BY id DESC") fun observarSolicitudes():Flow<List<SolicitudEntity>>
 @Query("SELECT * FROM equipos WHERE id=:id LIMIT 1") suspend fun obtenerEquipo(id:Int):EquipoEntity?
 @Query("SELECT * FROM solicitudes WHERE id=:id LIMIT 1") suspend fun obtenerSolicitud(id:Int):SolicitudEntity?
 @Query("SELECT COUNT(*) FROM equipos") suspend fun contarEquipos():Int
 @Query("SELECT COALESCE(MAX(id),0) FROM solicitudes") suspend fun maxSolicitudId():Int
 @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun guardarEquipos(items:List<EquipoEntity>)
 @Insert(onConflict=OnConflictStrategy.REPLACE) suspend fun guardarSolicitud(item:SolicitudEntity)
 @Update suspend fun actualizarEquipo(item:EquipoEntity)
 @Update suspend fun actualizarSolicitud(item:SolicitudEntity)
}

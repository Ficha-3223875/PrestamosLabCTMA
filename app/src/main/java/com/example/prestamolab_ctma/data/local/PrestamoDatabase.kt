package com.example.prestamolab_ctma.data.local
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities=[EquipoEntity::class,SolicitudEntity::class],version=1,exportSchema=false)
abstract class PrestamoDatabase:RoomDatabase(){abstract fun prestamoDao():PrestamoDao
 companion object { @Volatile private var INSTANCE:PrestamoDatabase?=null
  fun getInstance(context:Context)=INSTANCE?:synchronized(this){INSTANCE?:Room.databaseBuilder(context.applicationContext,PrestamoDatabase::class.java,"prestamolab.db").build().also{INSTANCE=it}}
 }
}

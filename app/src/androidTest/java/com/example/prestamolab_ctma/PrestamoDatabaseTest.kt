package com.example.prestamolab_ctma
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.prestamolab_ctma.data.local.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class) class PrestamoDatabaseTest{private lateinit var db:PrestamoDatabase
 @Before fun setup(){db=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(),PrestamoDatabase::class.java).allowMainThreadQueries().build()}
 @After fun close(){db.close()}
 @Test fun guardaEquipo()=runBlocking{db.prestamoDao().guardarEquipos(listOf(EquipoEntity(99,"Prueba","TECNOLOGIA","DISPONIBLE","Dato")));assert(db.prestamoDao().obtenerEquipo(99)!=null)}
}

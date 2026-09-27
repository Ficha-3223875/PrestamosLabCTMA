package com.prestamolab.ctma

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prestamolab.ctma.data.local.EquipmentEntity
import com.prestamolab.ctma.data.local.PrestamoDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrestamoDatabaseTest {
    private lateinit var db: PrestamoDatabase
    @Before fun setup(){ db=Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PrestamoDatabase::class.java).allowMainThreadQueries().build() }
    @After fun close(){ db.close() }
    @Test fun equipment_persists_in_room()= runTest {
        db.prestamoDao().upsertEquipment(listOf(EquipmentEntity(1,"Multímetro","Medición","Demo","DISPONIBLE")))
        assertEquals("Multímetro", db.prestamoDao().observeEquipment().first().first().name)
    }
}

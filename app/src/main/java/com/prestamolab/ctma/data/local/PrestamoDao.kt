package com.prestamolab.ctma.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PrestamoDao {
    @Query("SELECT * FROM equipment ORDER BY name")
    fun observeEquipment(): Flow<List<EquipmentEntity>>

    @Query("SELECT * FROM loans ORDER BY createdAt DESC")
    fun observeLoans(): Flow<List<LoanEntity>>

    @Query("SELECT * FROM equipment WHERE id = :id LIMIT 1")
    suspend fun equipmentById(id: Int): EquipmentEntity?

    @Query("SELECT * FROM loans WHERE id = :id LIMIT 1")
    suspend fun loanById(id: Int): LoanEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEquipment(items: List<EquipmentEntity>)

    @Query("UPDATE equipment SET state = 'RESERVADO' WHERE id = :id AND state = 'DISPONIBLE'")
    suspend fun reserveIfAvailable(id: Int): Int

    @Query("UPDATE equipment SET state = 'DISPONIBLE' WHERE id = :id")
    suspend fun makeAvailable(id: Int)

    @Insert
    suspend fun insertLoan(loan: LoanEntity): Long

    @Query("UPDATE loans SET state = :state WHERE id = :id")
    suspend fun updateLoanState(id: Int, state: String)

    @Query("UPDATE loans SET evidenceUri = :uri WHERE id = :id")
    suspend fun updateEvidence(id: Int, uri: String?)

    @Query("SELECT COUNT(*) FROM loans WHERE equipmentId = :equipmentId AND state IN ('SOLICITADA','APROBADA','ENTREGADA')")
    suspend fun activeLoanCount(equipmentId: Int): Int

    @Query("SELECT COUNT(*) FROM equipment")
    suspend fun equipmentCount(): Int
}

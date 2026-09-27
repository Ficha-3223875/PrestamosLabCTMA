package com.prestamolab.ctma.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "equipment")
data class EquipmentEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val state: String
)

@Entity(
    tableName = "loans",
    foreignKeys = [ForeignKey(
        entity = EquipmentEntity::class,
        parentColumns = ["id"],
        childColumns = ["equipmentId"],
        onDelete = ForeignKey.RESTRICT
    )],
    indices = [Index("equipmentId")]
)
data class LoanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val equipmentId: Int,
    val destination: String,
    val purpose: String,
    val durationHours: Int,
    val state: String,
    val evidenceUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

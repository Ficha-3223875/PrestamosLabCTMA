package com.prestamolab.ctma.data

import androidx.room.withTransaction
import com.prestamolab.ctma.data.local.EquipmentEntity
import com.prestamolab.ctma.data.local.LoanEntity
import com.prestamolab.ctma.data.local.PrestamoDatabase
import com.prestamolab.ctma.data.preferences.UserPreferences
import com.prestamolab.ctma.data.remote.RemoteDataSource
import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.EquipmentState
import com.prestamolab.ctma.model.Loan
import com.prestamolab.ctma.model.LoanDraft
import com.prestamolab.ctma.model.LoanResult
import com.prestamolab.ctma.model.LoanState
import com.prestamolab.ctma.util.LoanValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

interface EquipmentRepository {
    val equipment: Flow<List<Equipment>>
    val loans: Flow<List<Loan>>
    val availableOnly: Flow<Boolean>
    val lastSync: Flow<Long>
    suspend fun seedIfNeeded()
    suspend fun equipmentById(id: Int): Equipment?
    suspend fun loanById(id: Int): Loan?
    suspend fun requestLoan(draft: LoanDraft): LoanResult
    suspend fun cancelLoan(id: Int): Boolean
    suspend fun attachEvidence(id: Int, uri: String?)
    suspend fun returnLoan(id: Int): Boolean
    suspend fun setAvailableOnly(value: Boolean)
    suspend fun sync(): Result<Unit>
}

class RoomEquipmentRepository(
    private val db: PrestamoDatabase,
    private val preferences: UserPreferences,
    private val remote: RemoteDataSource
) : EquipmentRepository {
    private val dao = db.prestamoDao()

    override val availableOnly = preferences.availableOnly
    override val lastSync = preferences.lastSync

    override val equipment: Flow<List<Equipment>> = combine(
        dao.observeEquipment(), preferences.availableOnly
    ) { entities, onlyAvailable ->
        entities.map { it.toModel() }.let { list -> if (onlyAvailable) list.filter { it.available } else list }
    }

    override val loans: Flow<List<Loan>> = combine(dao.observeLoans(), dao.observeEquipment()) { loans, equipment ->
        val names = equipment.associate { it.id to it.name }
        loans.map { it.toModel(names[it.equipmentId] ?: "Equipo #${it.equipmentId}") }
    }

    override suspend fun seedIfNeeded() {
        if (dao.equipmentCount() == 0) sync().getOrElse {
            dao.upsertEquipment(FakeSeeds.equipment)
        }
    }

    override suspend fun equipmentById(id: Int): Equipment? = dao.equipmentById(id)?.toModel()

    override suspend fun loanById(id: Int): Loan? {
        val entity = dao.loanById(id) ?: return null
        val equipment = dao.equipmentById(entity.equipmentId)
        return entity.toModel(equipment?.name ?: "Equipo #${entity.equipmentId}")
    }

    override suspend fun requestLoan(draft: LoanDraft): LoanResult {
        val errors = LoanValidator.validate(draft)
        if (errors.isNotEmpty()) return LoanResult.ValidationError(errors)
        val equipment = dao.equipmentById(draft.equipmentId) ?: return LoanResult.NotFound
        if (equipment.state != EquipmentState.DISPONIBLE.name) return LoanResult.NotAvailable
        if (dao.activeLoanCount(draft.equipmentId) > 0) return LoanResult.Duplicate

        return try {
            var loanId = 0L
            db.withTransaction {
                if (dao.reserveIfAvailable(draft.equipmentId) != 1) return@withTransaction
                loanId = dao.insertLoan(
                    LoanEntity(
                        equipmentId = draft.equipmentId,
                        destination = draft.destination.trim(),
                        purpose = draft.purpose.trim(),
                        durationHours = draft.durationHours,
                        state = LoanState.SOLICITADA.name
                    )
                )
            }
            if (loanId > 0) LoanResult.Success(loanId.toInt()) else LoanResult.NotAvailable
        } catch (e: Exception) {
            LoanResult.Failure(e.message ?: "No fue posible guardar la solicitud.")
        }
    }

    override suspend fun cancelLoan(id: Int): Boolean = db.withTransaction {
        val loan = dao.loanById(id) ?: return@withTransaction false
        if (loan.state != LoanState.SOLICITADA.name) return@withTransaction false
        dao.updateLoanState(id, LoanState.CANCELADA.name)
        dao.makeAvailable(loan.equipmentId)
        true
    }

    override suspend fun attachEvidence(id: Int, uri: String?) = dao.updateEvidence(id, uri)

    override suspend fun returnLoan(id: Int): Boolean = db.withTransaction {
        val loan = dao.loanById(id) ?: return@withTransaction false
        if (loan.state !in setOf(LoanState.SOLICITADA.name, LoanState.APROBADA.name, LoanState.ENTREGADA.name)) {
            return@withTransaction false
        }
        dao.updateLoanState(id, LoanState.DEVUELTA.name)
        dao.makeAvailable(loan.equipmentId)
        true
    }

    override suspend fun setAvailableOnly(value: Boolean) = preferences.setAvailableOnly(value)

    override suspend fun sync(): Result<Unit> = runCatching {
        val remoteItems = remote.fetchEquipment()
        val localStates = remoteItems.associate { dto -> dto.id to dao.equipmentById(dto.id)?.state }
        dao.upsertEquipment(remoteItems.map { dto ->
            EquipmentEntity(
                id = dto.id,
                name = dto.name,
                category = dto.category,
                description = dto.description,
                state = localStates[dto.id] ?: dto.state
            )
        })
        preferences.markSynced()
    }
}

private fun EquipmentEntity.toModel() = Equipment(id, name, category, description, EquipmentState.valueOf(state))
private fun LoanEntity.toModel(equipmentName: String) = Loan(
    id, equipmentId, equipmentName, destination, purpose, durationHours,
    LoanState.valueOf(state), evidenceUri, createdAt
)

private object FakeSeeds {
    val equipment = listOf(
        EquipmentEntity(1, "Multímetro digital", "Medición", "Equipo para mediciones eléctricas.", "DISPONIBLE"),
        EquipmentEntity(2, "Taladro", "Herramienta eléctrica", "Herramienta eléctrica de formación.", "DISPONIBLE"),
        EquipmentEntity(3, "Juego de destornilladores", "Herramienta manual", "Kit de herramientas manuales.", "DISPONIBLE")
    )
}

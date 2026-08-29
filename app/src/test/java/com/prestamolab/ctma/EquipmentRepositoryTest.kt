package com.prestamolab.ctma

import com.prestamolab.ctma.data.InMemoryEquipmentRepository
import com.prestamolab.ctma.model.LoanResult

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EquipmentRepositoryTest {

    @Test
    fun repository_returns_training_equipment() = runTest {
        val items = InMemoryEquipmentRepository().getEquipment()
        assertEquals(3, items.size)
        assertEquals("Multímetro digital", items.first().name)
    }

    @Test
    fun request_loan_marks_equipment_as_unavailable() = runTest {
        val repository = InMemoryEquipmentRepository()

        assertTrue(repository.getEquipment().first().available)

        assertEquals(LoanResult.Success, repository.requestLoan(1))

        val updated = repository.getEquipment().first { it.id == 1 }
        assertFalse(updated.available)
    }

    @Test
    fun request_loan_is_rejected_when_equipment_is_unavailable() = runTest {
        val repository = InMemoryEquipmentRepository()

        repository.requestLoan(1)

        assertEquals(LoanResult.NotAvailable, repository.requestLoan(1))
    }
}

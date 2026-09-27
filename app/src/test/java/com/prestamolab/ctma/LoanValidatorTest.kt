package com.prestamolab.ctma

import com.prestamolab.ctma.model.LoanDraft
import com.prestamolab.ctma.util.LoanValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LoanValidatorTest {
    @Test fun valid_limits_are_accepted() {
        assertTrue(LoanValidator.validate(LoanDraft(1,"Lab 301","Práctica de medición",1)).isEmpty())
        assertTrue(LoanValidator.validate(LoanDraft(1,"Lab 301","1234567890",8)).isEmpty())
    }
    @Test fun invalid_destination_is_rejected() { assertTrue(LoanValidator.validate(LoanDraft(1,"","Propósito válido",1)).containsKey("destination")) }
    @Test fun purpose_boundaries_are_checked() {
        assertTrue(LoanValidator.validate(LoanDraft(1,"A","123456789",1)).containsKey("purpose"))
        assertFalse(LoanValidator.validate(LoanDraft(1,"A","1234567890",1)).containsKey("purpose"))
        assertFalse(LoanValidator.validate(LoanDraft(1,"A","x".repeat(180),1)).containsKey("purpose"))
        assertTrue(LoanValidator.validate(LoanDraft(1,"A","x".repeat(181),1)).containsKey("purpose"))
    }
    @Test fun duration_boundaries_are_checked() {
        assertTrue(LoanValidator.validate(LoanDraft(1,"A","Propósito válido",0)).containsKey("duration"))
        assertFalse(LoanValidator.validate(LoanDraft(1,"A","Propósito válido",1)).containsKey("duration"))
        assertFalse(LoanValidator.validate(LoanDraft(1,"A","Propósito válido",8)).containsKey("duration"))
        assertTrue(LoanValidator.validate(LoanDraft(1,"A","Propósito válido",9)).containsKey("duration"))
    }
}

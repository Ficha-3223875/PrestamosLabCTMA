package com.prestamolab.ctma

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertExists
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class PrestamoLabUiTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun home_displays_equipment_and_loan_flow() {
        composeRule.onNodeWithText("PréstamoLab CTMA").assertExists()
        composeRule.onNodeWithText("Multímetro digital").assertExists()
        composeRule.onNodeWithText("Disponible").assertExists()

        composeRule.onNodeWithText("Multímetro digital").performClick()
        composeRule.onNodeWithText("Solicitar préstamo").assertExists()
        composeRule.onNodeWithText("Solicitar préstamo").performClick()
        composeRule.onNodeWithText("Préstamo solicitado correctamente.").assertExists()
    }
}

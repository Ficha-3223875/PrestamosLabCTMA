package com.prestamolab.ctma

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.EquipmentState
import com.prestamolab.ctma.ui.DeviceStatusScreen
import com.prestamolab.ctma.ui.HomeScreen
import org.junit.Rule
import org.junit.Test

class PrestamoLabUiTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun catalog_and_device_capability_are_visible() {

        val showDevice = mutableStateOf(false)

        rule.setContent {
            MaterialTheme {
                if (showDevice.value) {
                    DeviceStatusScreen(
                        onBack = { showDevice.value = false }
                    )
                } else {
                    HomeScreen(
                        equipment = listOf(
                            Equipment(
                                id = 1,
                                name = "Multímetro digital",
                                category = "Medición",
                                description = "Equipo para mediciones eléctricas.",
                                state = EquipmentState.DISPONIBLE
                            )
                        ),
                        availableOnly = false,
                        lastSync = 0L,
                        onFilter = {},
                        onSync = {},
                        onSelect = {},
                        onLoans = {},
                        onDevice = { showDevice.value = true }
                    )
                }
            }
        }

        rule.onNodeWithText("PréstamoLab CTMA")
            .fetchSemanticsNode()

        rule.onNodeWithText("Multímetro digital")
            .fetchSemanticsNode()

        rule.onNodeWithText("Dispositivo")
            .performClick()

        rule.waitForIdle()

        rule.onNodeWithText(
            "Capacidad física adicional: energía y almacenamiento"
        ).fetchSemanticsNode()
    }
}
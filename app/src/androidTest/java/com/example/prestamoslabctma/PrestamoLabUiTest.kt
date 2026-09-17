package com.example.prestamoslabctma

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

/** Pruebas instrumentadas de navegación y formulario. Requieren emulador/dispositivo Android. */
class PrestamoLabUiTest {
    @get:Rule val compose = createAndroidComposeRule<MainActivity>()
    @Test fun tc17_catalogoYDetalleNavegan(){ compose.onNodeWithTag("catalogo").assertExists(); compose.onAllNodesWithText("Ver detalle")[0].performClick(); compose.onNodeWithText("ID del equipo: 1").assertExists() }
    @Test fun tc18_formularioMuestraValidaciones(){ compose.onAllNodesWithText("Solicitar")[0].performClick(); compose.onNodeWithTag("guardar").performClick(); compose.onNodeWithText("El ambiente o destino es obligatorio").assertExists(); compose.onNodeWithText("El propósito debe tener entre 10 y 180 caracteres").assertExists() }
}

package com.example.prestamolab_ctma
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.prestamolab_ctma.model.*
import org.junit.*
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class) class PrestamoUiTest{@get:Rule val rule=createComposeRule();@Test fun cardVisible(){rule.setContent{EquipoCardTest()};rule.onNodeWithText("Equipo prueba").assertIsDisplayed()}}
@androidx.compose.runtime.Composable private fun EquipoCardTest(){androidx.compose.material3.Card{androidx.compose.material3.Text("Equipo prueba")}}

package com.prestamolab.ctma.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prestamolab.ctma.viewmodel.EquipmentViewModel

@Composable
fun PrestamoLabApp(viewModel: EquipmentViewModel = viewModel()) {
    val navController = rememberNavController()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                equipment = state.equipment,
                onSelect = {
                    viewModel.select(it)
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            DetailScreen(
                equipment = state.selectedEquipment,
                message = state.message,
                onBack = { navController.popBackStack() },
                onLoan = viewModel::requestLoan,
                onDismissMessage = viewModel::clearMessage
            )
        }
    }
}

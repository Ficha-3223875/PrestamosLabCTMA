package com.example.prestamoslabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prestamoslabctma.ui.catalogo.CatalogoScreen
import com.example.prestamoslabctma.ui.equipo.EquipoDetalleScreen
import com.example.prestamoslabctma.ui.misprestamos.MisSolicitudesScreen
import com.example.prestamoslabctma.ui.misprestamos.SolicitudDetalleScreen
import com.example.prestamoslabctma.ui.solicitud.SolicitudScreen
import com.example.prestamoslabctma.ui.theme.PrestamosLabCtmaTheme
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PrestamosLabCtmaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val viewModel = remember {
        PrestamoViewModel()
    }

    NavHost(
        navController = navController,
        startDestination = "catalogo"
    ) {

        // =========================
        // CATÁLOGO
        // =========================

        composable("catalogo") {
            CatalogoScreen(
                viewModel = viewModel,
                onEquipoClick = { equipoId ->
                    navController.navigate("equipo/$equipoId")
                },
                onMisSolicitudesClick = {
                    navController.navigate("mis_solicitudes")
                }
            )
        }

        // =========================
        // DETALLE DEL EQUIPO
        // =========================

        composable(
            route = "equipo/{equipoId}",
            arguments = listOf(
                navArgument("equipoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val equipoId =
                backStackEntry.arguments?.getInt("equipoId")

            if (equipoId != null) {
                EquipoDetalleScreen(
                    equipoId = equipoId,
                    viewModel = viewModel,
                    onSolicitarClick = { id ->
                        navController.navigate("solicitar/$id")
                    },
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // =========================
        // CREAR SOLICITUD
        // =========================

        composable(
            route = "solicitar/{equipoId}",
            arguments = listOf(
                navArgument("equipoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val equipoId =
                backStackEntry.arguments?.getInt("equipoId")

            if (equipoId != null) {
                SolicitudScreen(
                    equipoId = equipoId,
                    viewModel = viewModel,
                    onSolicitudCreada = {
                        navController.popBackStack(
                            "catalogo",
                            inclusive = false
                        )
                    },
                    onCancelar = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // =========================
        // MIS SOLICITUDES
        // =========================

        composable("mis_solicitudes") {
            MisSolicitudesScreen(
                viewModel = viewModel,
                onSolicitudClick = { solicitudId ->
                    navController.navigate(
                        "solicitud/$solicitudId"
                    )
                }
            )
        }

        // =========================
        // DETALLE DE SOLICITUD
        // =========================

        composable(
            route = "solicitud/{solicitudId}",
            arguments = listOf(
                navArgument("solicitudId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val solicitudId =
                backStackEntry.arguments?.getInt("solicitudId")

            if (solicitudId != null) {
                SolicitudDetalleScreen(
                    solicitudId = solicitudId,
                    viewModel = viewModel,
                    onVolver = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
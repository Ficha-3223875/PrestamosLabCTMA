package com.example.prestamoslabctma

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.prestamoslabctma.device.PrestamoNotificationManager
import com.example.prestamoslabctma.ui.catalogo.CatalogoScreen
import com.example.prestamoslabctma.ui.equipo.EquipoDetalleScreen
import com.example.prestamoslabctma.ui.misprestamos.MisSolicitudesScreen
import com.example.prestamoslabctma.ui.misprestamos.SolicitudDetalleScreen
import com.example.prestamoslabctma.ui.solicitud.SolicitudScreen
import com.example.prestamoslabctma.ui.theme.PrestamosLabCtmaTheme
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // El permiso se solicitará solamente
            // cuando todavía no esté concedido.
        }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val notificationManager =
            PrestamoNotificationManager(this)

        notificationManager.crearCanal()

        solicitarPermisoNotificaciones()

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

    private fun solicitarPermisoNotificaciones() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            val permisoConcedido =
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED

            if (!permisoConcedido) {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }
    }
}

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    val viewModel: PrestamoViewModel =
        viewModel()

    NavHost(
        navController = navController,
        startDestination = "catalogo"
    ) {

        composable("catalogo") {

            CatalogoScreen(
                viewModel = viewModel,

                onEquipoClick = { equipoId ->

                    navController.navigate(
                        "equipo/$equipoId"
                    )
                },

                onMisSolicitudesClick = {

                    navController.navigate(
                        "mis_solicitudes"
                    )
                }
            )
        }

        composable(
            route = "equipo/{equipoId}",

            arguments = listOf(
                navArgument("equipoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val equipoId =
                backStackEntry.arguments
                    ?.getInt("equipoId")

            if (equipoId != null) {

                EquipoDetalleScreen(
                    equipoId = equipoId,
                    viewModel = viewModel,

                    onSolicitarClick = { id ->

                        navController.navigate(
                            "solicitar/$id"
                        )
                    },

                    onVolver = {

                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            route = "solicitar/{equipoId}",

            arguments = listOf(
                navArgument("equipoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val equipoId =
                backStackEntry.arguments
                    ?.getInt("equipoId")

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

        composable(
            route = "solicitud/{solicitudId}",

            arguments = listOf(
                navArgument("solicitudId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val solicitudId =
                backStackEntry.arguments
                    ?.getInt("solicitudId")

            if (solicitudId != null) {

                SolicitudDetalleScreen(
                    solicitudId = solicitudId,
                    viewModel = viewModel,

                    onVolver = {

                        navController.popBackStack()
                    },

                    onSolicitudCancelada = {

                        navController.popBackStack(
                            "mis_solicitudes",
                            inclusive = false
                        )
                    }
                )
            }
        }
    }
}
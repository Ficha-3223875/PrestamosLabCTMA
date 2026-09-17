package com.example.prestamoslabctma.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.prestamoslabctma.navigation.Routes
import com.example.prestamoslabctma.ui.screens.*
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamoLabApp(vm: PrestamoViewModel) {
    val nav = rememberNavController()
    val current by nav.currentBackStackEntryAsState()
    val route = current?.destination?.route
    Scaffold(
        topBar = { TopAppBar(title = { Text("PréstamoLab CTMA") }) },
        bottomBar = {
            if (route == Routes.CATALOGO || route == Routes.MIS_SOLICITUDES) NavigationBar {
                NavigationBarItem(selected = route == Routes.CATALOGO, onClick = { nav.navigate(Routes.CATALOGO) { popUpTo(Routes.CATALOGO); launchSingleTop = true } }, icon = { Text("▦") }, label = { Text("Catálogo") })
                NavigationBarItem(selected = route == Routes.MIS_SOLICITUDES, onClick = { nav.navigate(Routes.MIS_SOLICITUDES) { launchSingleTop = true } }, icon = { Text("☷") }, label = { Text("Mis solicitudes") })
            }
        }
    ) { padding ->
        NavHost(nav, startDestination = Routes.CATALOGO, modifier = Modifier.padding(padding)) {
            composable(Routes.CATALOGO) { CatalogoScreen(vm, { nav.navigate(Routes.equipo(it)) }, { nav.navigate(Routes.solicitar(it)) }) }
            composable(Routes.MIS_SOLICITUDES) { MisSolicitudesScreen(vm) { nav.navigate(Routes.solicitud(it)) } }
            composable(Routes.EQUIPO, arguments=listOf(navArgument("equipoId"){type=NavType.IntType})) { back -> EquipoDetalleScreen(vm, back.arguments?.getInt("equipoId") ?: -1, { nav.popBackStack() }, { nav.navigate(Routes.solicitar(it)) }) }
            composable(Routes.SOLICITAR, arguments=listOf(navArgument("equipoId"){type=NavType.IntType})) { back -> SolicitarScreen(vm, back.arguments?.getInt("equipoId") ?: -1, { nav.popBackStack() }) { nav.navigate(Routes.MIS_SOLICITUDES) { popUpTo(Routes.CATALOGO) } } }
            composable(Routes.SOLICITUD, arguments=listOf(navArgument("solicitudId"){type=NavType.IntType})) { back -> SolicitudDetalleScreen(vm, back.arguments?.getInt("solicitudId") ?: -1) { nav.popBackStack() } }
        }
    }
}

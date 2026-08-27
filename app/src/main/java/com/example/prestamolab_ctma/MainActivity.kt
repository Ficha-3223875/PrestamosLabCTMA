package com.example.prestamolab_ctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prestamolab_ctma.model.CategoriaEquipo
import com.example.prestamolab_ctma.model.Equipo
import com.example.prestamolab_ctma.model.EstadoEquipo
import com.example.prestamolab_ctma.model.EstadoSolicitud
import com.example.prestamolab_ctma.model.SolicitudPrestamo
import com.example.prestamolab_ctma.navigation.AppScreen
import com.example.prestamolab_ctma.ui.theme.PrestamolabctmaTheme
import com.example.prestamolab_ctma.viewmodel.PrestamoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrestamolabctmaTheme {
                PrestamoLabApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrestamoLabApp(vm: PrestamoViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    var screen by remember { mutableStateOf<AppScreen>(AppScreen.Catalogo) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.mensaje) {
        state.mensaje?.let {
            snackbarHostState.showSnackbar(it)
            vm.limpiarMensaje()
        }
    }

    fun back() {
        screen = when (screen) {
            is AppScreen.Catalogo -> AppScreen.Catalogo
            is AppScreen.DetalleEquipo -> AppScreen.Catalogo
            is AppScreen.Solicitar -> {
                val id = (screen as AppScreen.Solicitar).equipoId
                AppScreen.DetalleEquipo(id)
            }
            is AppScreen.MisSolicitudes -> AppScreen.Catalogo
            is AppScreen.DetalleSolicitud -> AppScreen.MisSolicitudes
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PréstamoLab CTMA ANT", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    if (screen !is AppScreen.Catalogo) {
                        IconButton(onClick = { back() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { screen = AppScreen.MisSolicitudes }) {
                        Icon(Icons.Default.List, contentDescription = "Mis solicitudes")
                    }
                    IconButton(onClick = { screen = AppScreen.Catalogo }) {
                        Icon(Icons.Default.Home, contentDescription = "Catálogo")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when (val s = screen) {
            AppScreen.Catalogo -> CatalogoScreen(
                state.equipos,
                onEquipo = { screen = AppScreen.DetalleEquipo(it) },
                onSolicitudes = { screen = AppScreen.MisSolicitudes },
                modifier = Modifier.padding(padding)
            )
            is AppScreen.DetalleEquipo -> {
                val equipo = vm.equipo(s.equipoId)
                if (equipo != null) DetalleEquipoScreen(
                    equipo = equipo,
                    onSolicitar = { screen = AppScreen.Solicitar(equipo.id) },
                    modifier = Modifier.padding(padding)
                ) else ErrorScreen("Equipo no encontrado", Modifier.padding(padding))
            }
            is AppScreen.Solicitar -> {
                val equipo = vm.equipo(s.equipoId)
                if (equipo != null) SolicitarScreen(
                    equipo = equipo,
                    onGuardar = { destino, proposito, horas ->
                        val creada = vm.crearSolicitud(equipo.id, destino, proposito, horas)
                        if (creada) {
                            screen = AppScreen.MisSolicitudes
                        }
                        creada
                    },
                    modifier = Modifier.padding(padding)
                ) else ErrorScreen("Equipo no encontrado", Modifier.padding(padding))
            }
            AppScreen.MisSolicitudes -> MisSolicitudesScreen(
                solicitudes = state.solicitudes,
                equipos = state.equipos,
                onDetalle = { screen = AppScreen.DetalleSolicitud(it) },
                modifier = Modifier.padding(padding)
            )
            is AppScreen.DetalleSolicitud -> {
                val solicitud = vm.solicitud(s.solicitudId)
                if (solicitud != null) DetalleSolicitudScreen(
                    solicitud = solicitud,
                    equipo = vm.equipo(solicitud.equipoId),
                    onCancelar = { vm.cancelarSolicitud(solicitud.id) },
                    modifier = Modifier.padding(padding)
                ) else ErrorScreen("Solicitud no encontrada", Modifier.padding(padding))
            }
        }
    }
}

@Composable
fun CatalogoScreen(
    equipos: List<Equipo>,
    onEquipo: (Int) -> Unit,
    onSolicitudes: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Catálogo de equipos", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Consulta disponibilidad y selecciona un equipo para ver su detalle.")
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
            items(equipos, key = { it.id }) { equipo ->
                EquipoCard(equipo, onEquipo)
            }
        }
        Button(onClick = onSolicitudes, modifier = Modifier.fillMaxWidth()) {
            Text("Mis solicitudes")
        }
    }
}

@Composable
fun EquipoCard(equipo: Equipo, onEquipo: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEquipo(equipo.id) }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(equipo.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Categoría: ${categoriaTexto(equipo.categoria)}")
            Text("Estado: ${estadoEquipoTexto(equipo.estado)}", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = { onEquipo(equipo.id) }) {
                Text("Ver detalle")
            }
        }
    }
}

@Composable
fun DetalleEquipoScreen(equipo: Equipo, onSolicitar: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Detalle del equipo", style = MaterialTheme.typography.headlineSmall)
        Text(equipo.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Categoría: ${categoriaTexto(equipo.categoria)}")
        Text("Estado: ${estadoEquipoTexto(equipo.estado)}")
        Text(equipo.descripcion)
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = onSolicitar,
            enabled = equipo.estado == EstadoEquipo.DISPONIBLE,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (equipo.estado == EstadoEquipo.DISPONIBLE) "Solicitar préstamo" else "No disponible")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitarScreen(
    equipo: Equipo,
    onGuardar: (String, String, Int) -> Boolean,
    modifier: Modifier = Modifier
) {
    var destino by remember { mutableStateOf("") }
    var proposito by remember { mutableStateOf("") }
    var duracion by remember { mutableStateOf("1") }
    var error by remember { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    var guardando by remember { mutableStateOf(false) }

    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Solicitar préstamo", style = MaterialTheme.typography.headlineSmall)
        Text("Equipo: ${equipo.nombre}", fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it; error = null },
            label = { Text("Ambiente / destino") },
            supportingText = { Text("Obligatorio") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = proposito,
            onValueChange = { proposito = it; error = null },
            label = { Text("Propósito") },
            supportingText = { Text("${proposito.trim().length}/180 caracteres. Mínimo 10.") },
            modifier = Modifier.fillMaxWidth().height(150.dp)
        )

        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = duracion,
                onValueChange = {},
                readOnly = true,
                label = { Text("Duración (horas)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                (1..8).forEach { hour ->
                    DropdownMenuItem(
                        text = { Text("$hour hora${if (hour > 1) "s" else ""}") },
                        onClick = { duracion = hour.toString(); expanded = false }
                    )
                }
            }
        }

        error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                val horas = duracion.toIntOrNull() ?: 0
                error = when {
                    destino.trim().isEmpty() -> "El destino es obligatorio."
                    proposito.trim().length !in 10..180 -> "El propósito debe tener entre 10 y 180 caracteres."
                    horas !in 1..8 -> "La duración debe estar entre 1 y 8 horas."
                    else -> null
                }
                if (error == null) {
                    guardando = true
                    val creada = onGuardar(destino, proposito, horas)
                    if (!creada) guardando = false
                }
            },
            enabled = !guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (guardando) "Guardando…" else "Guardar solicitud")
        }
    }
}

@Composable
fun MisSolicitudesScreen(
    solicitudes: List<SolicitudPrestamo>,
    equipos: List<Equipo>,
    onDetalle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Mis solicitudes", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(10.dp))
        if (solicitudes.isEmpty()) {
            Text("Aún no tienes solicitudes registradas.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(solicitudes, key = { it.id }) { solicitud ->
                    val equipo = equipos.find { it.id == solicitud.equipoId }
                    Card(Modifier.fillMaxWidth().clickable { onDetalle(solicitud.id) }) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Solicitud #${solicitud.id}", fontWeight = FontWeight.Bold)
                            Text(equipo?.nombre ?: "Equipo no encontrado")
                            Text("Estado: ${estadoSolicitudTexto(solicitud.estado)}")
                            Text("Destino: ${solicitud.ambienteDestino}")
                            TextButton(onClick = { onDetalle(solicitud.id) }) {
                                Text("Ver detalle")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleSolicitudScreen(
    solicitud: SolicitudPrestamo,
    equipo: Equipo?,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    Column(
        modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Detalle de solicitud", style = MaterialTheme.typography.headlineSmall)
        Text("Solicitud #${solicitud.id}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("Equipo: ${equipo?.nombre ?: "No encontrado"}")
        Text("Destino: ${solicitud.ambienteDestino}")
        Text("Propósito: ${solicitud.proposito}")
        Text("Duración: ${solicitud.duracionHoras} hora(s)")
        Text("Estado: ${estadoSolicitudTexto(solicitud.estado)}")

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { mostrarConfirmacion = true },
            enabled = solicitud.estado == EstadoSolicitud.SOLICITADA,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (solicitud.estado == EstadoSolicitud.SOLICITADA) "Cancelar solicitud" else "Cancelación no disponible")
        }
    }

    if (mostrarConfirmacion) {
        AlertDialog(
            onDismissRequest = { mostrarConfirmacion = false },
            title = { Text("Cancelar solicitud") },
            text = { Text("¿Seguro que deseas cancelar esta solicitud? El equipo volverá a estar disponible.") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarConfirmacion = false
                    onCancelar()
                }) { Text("Sí, cancelar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarConfirmacion = false }) { Text("No") }
            }
        )
    }
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center) {
        Text(message, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Regresa y vuelve a intentar.")
    }
}

fun categoriaTexto(categoria: CategoriaEquipo): String = when (categoria) {
    CategoriaEquipo.ELECTRONICA -> "Electrónica"
    CategoriaEquipo.TECNOLOGIA -> "Tecnología"
    CategoriaEquipo.AUDIOVISUAL -> "Audiovisual"
    CategoriaEquipo.HERRAMIENTAS -> "Herramientas"
}

fun estadoEquipoTexto(estado: EstadoEquipo): String = when (estado) {
    EstadoEquipo.DISPONIBLE -> "DISPONIBLE"
    EstadoEquipo.RESERVADO -> "RESERVADO"
    EstadoEquipo.PRESTADO -> "PRESTADO"
}

fun estadoSolicitudTexto(estado: EstadoSolicitud): String = when (estado) {
    EstadoSolicitud.SOLICITADA -> "SOLICITADA"
    EstadoSolicitud.APROBADA -> "APROBADA"
    EstadoSolicitud.ENTREGADA -> "ENTREGADA"
    EstadoSolicitud.DEVUELTA -> "DEVUELTA"
    EstadoSolicitud.CANCELADA -> "CANCELADA"
    EstadoSolicitud.RECHAZADA -> "RECHAZADA"
}

package com.example.prestamoslabctma.ui.screens

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.prestamoslabctma.R
import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.repository.EvidenciaDevolucion
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel
import kotlin.math.abs

@Composable private fun EstadoChip(estado: Any) { AssistChip(onClick = {}, label = { Text(estado.toString()) }) }
@Composable private fun Volver(onBack: () -> Unit) { TextButton(onClick = onBack) { Text("← Volver") } }

@Composable
fun CatalogoScreen(vm: PrestamoViewModel, onDetalle: (Int) -> Unit, onSolicitar: (Int) -> Unit) {
    val equipos by vm.equipos.collectAsStateWithLifecycle()
    val ui by vm.uiState.collectAsStateWithLifecycle()
    var menu by remember { mutableStateOf(false) }
    val filtrados = equipos.filter { ui.preferencias.filtroCategoria == "TODAS" || it.categoria.name == ui.preferencias.filtroCategoria }
    LazyColumn(Modifier.fillMaxSize().padding(16.dp).testTag("catalogo"), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Equipos y herramientas", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Los datos visibles provienen de Room y permanecen después de reiniciar la aplicación.")
            Box {
                OutlinedButton(onClick = { menu = true }) { Text("Categoría: ${ui.preferencias.filtroCategoria}") }
                DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                    listOf("TODAS") + CategoriaEquipo.entries.map { it.name }.forEach { valor ->
                        DropdownMenuItem(text = { Text(valor) }, onClick = { vm.guardarFiltro(valor); menu = false })
                    }
                }
            }
        }
        if (filtrados.isEmpty()) item { Text("No hay equipos para el filtro seleccionado.") }
        items(filtrados, key = { it.id }) { e ->
            Card(Modifier.fillMaxWidth().clickable { onDetalle(e.id) }) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(e.nombre, fontWeight = FontWeight.Bold); Text(e.categoria.etiqueta); EstadoChip(e.estado)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextButton(onClick = { onDetalle(e.id) }) { Text("Ver detalle") }
                        if (e.estado == EstadoEquipo.DISPONIBLE) Button(onClick = { onSolicitar(e.id) }) { Text("Solicitar") }
                    }
                }
            }
        }
    }
}

@Composable
fun EquipoDetalleScreen(vm: PrestamoViewModel, id: Int, onBack: () -> Unit, onSolicitar: (Int) -> Unit) {
    val equipos by vm.equipos.collectAsStateWithLifecycle(); val e = equipos.find { it.id == id }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Volver(onBack)
        if (e == null) { Text("Equipo no encontrado", style = MaterialTheme.typography.headlineSmall); Text("El identificador solicitado no existe. Puedes volver al catálogo.") }
        else { Text(e.nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold); Text("Categoría: ${e.categoria.etiqueta}"); Text("Estado: ${e.estado}"); Text("ID del equipo: ${e.id}"); if (e.estado == EstadoEquipo.DISPONIBLE) Button(onClick = { onSolicitar(e.id) }) { Text("Solicitar préstamo") } else Text("Este equipo no está disponible para nuevas solicitudes.") }
    }
}

@Composable
fun SolicitarScreen(vm: PrestamoViewModel, id: Int, onBack: () -> Unit, onCreada: () -> Unit) {
    val equipos by vm.equipos.collectAsStateWithLifecycle(); val ui by vm.uiState.collectAsStateWithLifecycle(); val e = equipos.find { it.id == id }
    var ambiente by rememberSaveable { mutableStateOf("") }; var proposito by rememberSaveable { mutableStateOf("") }; var duracion by rememberSaveable { mutableStateOf("") }; var intento by rememberSaveable { mutableStateOf(false) }
    val v = vm.validar(ambiente, proposito, duracion)
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Volver(onBack); if (e == null) { Text("Equipo no encontrado"); return@Column }; Text("Solicitar ${e.nombre}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        if (e.estado != EstadoEquipo.DISPONIBLE) { Text("El equipo ya no está DISPONIBLE."); return@Column }
        OutlinedTextField(ambiente, { ambiente = it }, Modifier.fillMaxWidth().testTag("ambiente"), label = { Text("Ambiente o destino") }, isError = intento && v.ambienteError != null, supportingText = { if (intento) v.ambienteError?.let { Text(it) } })
        OutlinedTextField(proposito, { proposito = it }, Modifier.fillMaxWidth().testTag("proposito"), label = { Text("Propósito (10 a 180 caracteres)") }, minLines = 3, isError = intento && v.propositoError != null, supportingText = { if (intento) v.propositoError?.let { Text(it) } })
        OutlinedTextField(duracion, { duracion = it.filter(Char::isDigit) }, Modifier.fillMaxWidth().testTag("duracion"), label = { Text("Duración en horas (1 a 8)") }, isError = intento && v.duracionError != null, supportingText = { if (intento) v.duracionError?.let { Text(it) } })
        Button(enabled = !ui.operacionEnCurso, onClick = { intento = true; if (v.esValida) vm.crear(id, ambiente, proposito, duracion) { if (it) onCreada() } }, modifier = Modifier.fillMaxWidth().testTag("guardar")) { Text(if (ui.operacionEnCurso) "Guardando…" else "Guardar solicitud") }
    }
}

@Composable
fun MisSolicitudesScreen(vm: PrestamoViewModel, onDetalle: (Int) -> Unit) {
    val solicitudes by vm.solicitudes.collectAsStateWithLifecycle(); val equipos by vm.equipos.collectAsStateWithLifecycle()
    LazyColumn(Modifier.fillMaxSize().padding(16.dp).testTag("misSolicitudes"), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { Text("Mis préstamos", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold); Text("Seguimiento local y estado de sincronización.") }
        if (solicitudes.isEmpty()) item { Text("Aún no has creado solicitudes de préstamo.") }
        items(solicitudes, key = { it.id }) { s -> Card(Modifier.fillMaxWidth().clickable { onDetalle(s.id) }) { Column(Modifier.padding(16.dp)) { Text(equipos.find { it.id == s.equipoId }?.nombre ?: "Equipo ${s.equipoId}", fontWeight = FontWeight.Bold); Text("Solicitud #${s.id}"); EstadoChip(s.estado); Text("Sincronización: ${s.estadoSincronizacion}") } } }
    }
}

@Composable
fun SolicitudDetalleScreen(vm: PrestamoViewModel, id: Int, onBack: () -> Unit) {
    val solicitudes by vm.solicitudes.collectAsStateWithLifecycle(); val equipos by vm.equipos.collectAsStateWithLifecycle(); val ui by vm.uiState.collectAsStateWithLifecycle(); val s = solicitudes.find { it.id == id }
    val context = LocalContext.current
    var evidenciaUri by rememberSaveable { mutableStateOf<String?>(null) }
    var evidenciaNombre by rememberSaveable { mutableStateOf("evidencia_devolucion") }
    var sensorVerificado by rememberSaveable { mutableStateOf(false) }
    val selector = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let { context.contentResolver.takePersistableUriPermission(it, android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION); evidenciaUri = it.toString(); evidenciaNombre = it.lastPathSegment ?: "evidencia_devolucion" }
    }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Volver(onBack)
        if (s == null) { Text("Solicitud no encontrada", style = MaterialTheme.typography.headlineSmall); Text("El identificador solicitado no existe.") }
        else {
            Text("Solicitud #${s.id}", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Equipo: ${equipos.find { it.id == s.equipoId }?.nombre ?: s.equipoId}"); Text("Destino: ${s.ambienteDestino}"); Text("Propósito: ${s.proposito}"); Text("Duración: ${s.duracionHoras} hora(s)"); Text("Estado: ${s.estado}"); Text("Sincronización: ${s.estadoSincronizacion}")
            when (s.estado) {
                EstadoSolicitud.SOLICITADA -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { Button(onClick = { vm.entregar(s.id) { ok -> if (ok && ui.preferencias.recordatorios) notificarDevolucion(context, s.id) } }) { Text("Registrar entrega") }; OutlinedButton(onClick = { vm.cancelar(s.id) }) { Text("Cancelar") } }
                EstadoSolicitud.ENTREGADA -> {
                    Text("Evidencia y capacidad física", fontWeight = FontWeight.Bold)
                    OutlinedButton(onClick = { selector.launch(arrayOf("image/*")) }) { Text(if (evidenciaUri == null) "Seleccionar fotografía" else "Cambiar fotografía") }
                    evidenciaUri?.let { Text("Archivo: $evidenciaNombre") }
                    VerificacionAcelerometro { sensorVerificado = true }
                    Button(enabled = evidenciaUri != null && !ui.operacionEnCurso, onClick = { vm.devolver(s.id, EvidenciaDevolucion(evidenciaUri.orEmpty(), evidenciaNombre, "image/*", sensorVerificado)) }) { Text("Confirmar devolución") }
                }
                EstadoSolicitud.DEVUELTA -> { Text("Devolución confirmada"); Text("Evidencia: ${s.evidenciaNombre ?: "registrada"}"); Text("Acelerómetro verificado: ${if (s.sensorAcelerometroVerificado) "Sí" else "No"}") }
                else -> Text("Esta solicitud no admite acciones en su estado actual.")
            }
        }
    }
}

@Composable
private fun VerificacionAcelerometro(onVerificado: () -> Unit) {
    val context = LocalContext.current
    var mensaje by remember { mutableStateOf("Mueve suavemente el dispositivo para comprobar el acelerómetro.") }
    DisposableEffect(Unit) {
        val manager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val listener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
            override fun onSensorChanged(event: SensorEvent) {
                val movimiento = abs(event.values[0]) + abs(event.values[1]) + abs(event.values[2])
                if (movimiento > 13f) { mensaje = "Acelerómetro verificado correctamente."; onVerificado(); manager.unregisterListener(this) }
            }
        }
        if (sensor == null) mensaje = "Este dispositivo no dispone de acelerómetro." else manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose { manager.unregisterListener(listener) }
    }
    Text(mensaje)
}

@Composable
fun PreferenciasScreen(vm: PrestamoViewModel, onBack: () -> Unit) {
    val ui by vm.uiState.collectAsStateWithLifecycle(); val context = LocalContext.current
    val permiso = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido -> vm.guardarRecordatorios(concedido) }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Volver(onBack); Text("Preferencias", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Recordatorios de devolución"); Switch(checked = ui.preferencias.recordatorios, onCheckedChange = { activo -> if (activo && Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) permiso.launch(Manifest.permission.POST_NOTIFICATIONS) else vm.guardarRecordatorios(activo) }) }
        Button(enabled = !ui.operacionEnCurso, onClick = vm::sincronizar) { Text("Sincronizar ahora") }
        Text("La sincronización usa HTTPS, tiempos de espera y no registra tokens ni datos sensibles.")
    }
}

private fun notificarDevolucion(context: Context, id: Int) {
    if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) return
    val builder = if (Build.VERSION.SDK_INT >= 26) Notification.Builder(context, "devoluciones") else Notification.Builder(context)
    val notification = builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("PréstamoLab CTMA").setContentText("Recuerda registrar la devolución del préstamo #$id.").setAutoCancel(true).build()
    context.getSystemService(NotificationManager::class.java).notify(id, notification)
}

package com.prestamolab.ctma.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.prestamolab.ctma.model.Equipment
import com.prestamolab.ctma.model.Loan
import com.prestamolab.ctma.model.LoanState
import com.prestamolab.ctma.util.readDeviceStatus
import java.text.DateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(equipment: List<Equipment>, availableOnly: Boolean, lastSync: Long, onFilter: (Boolean)->Unit, onSync:()->Unit, onSelect:(Int)->Unit, onLoans:()->Unit, onDevice:()->Unit) {
    Scaffold(topBar = { TopAppBar(title={Text("PréstamoLab CTMA")}, actions={ TextButton(onClick=onLoans){Text("Mis préstamos")} }) }) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
            item { Text("Equipos y herramientas de formación", style=MaterialTheme.typography.headlineSmall); Text("Consulta disponibilidad y solicita un préstamo.") }
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween) {
                    Row { Checkbox(checked=availableOnly, onCheckedChange=onFilter); Text("Solo disponibles", Modifier.padding(top=12.dp)) }
                    TextButton(onClick=onDevice){ Text("Dispositivo") }
                }
                Button(onClick=onSync, modifier=Modifier.fillMaxWidth()){Text("Sincronizar catálogo")}
                if (lastSync > 0) Text("Última sincronización: ${DateFormat.getDateTimeInstance().format(Date(lastSync))}", style=MaterialTheme.typography.bodySmall)
            }
            if (equipment.isEmpty()) item { Text("No hay equipos para mostrar.") }
            items(equipment, key={it.id}) { item ->
                Card(onClick={onSelect(item.id)}, modifier=Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(4.dp)) {
                        Text(item.name, style=MaterialTheme.typography.titleMedium); Text(item.category); Text(item.description)
                        Text("Estado: ${item.state.name}", modifier=Modifier.semantics { contentDescription = "Estado ${item.state.name}" })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(equipment: Equipment?, message: String?, onBack:()->Unit, onRequest:()->Unit) {
    Scaffold(topBar={TopAppBar(title={Text("Detalle del equipo")}, navigationIcon={TextButton(onClick=onBack){Text("Volver")}})}) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
            if (equipment == null) { Text(message ?: "No se encontró el equipo."); return@Column }
            Text(equipment.name, style=MaterialTheme.typography.headlineMedium); Text("Categoría: ${equipment.category}"); Text(equipment.description); Text("Estado: ${equipment.state.name}")
            Button(onClick=onRequest, enabled=equipment.available, modifier=Modifier.fillMaxWidth()) { Text(if(equipment.available) "Solicitar préstamo" else "No disponible") }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestLoanScreen(equipment: Equipment?, errors: Map<String,String>, message:String?, saving:Boolean, onBack:()->Unit, onSubmit:(String,String,String)->Unit) {
    var destination by rememberSaveable { mutableStateOf("") }; var purpose by rememberSaveable { mutableStateOf("") }; var duration by rememberSaveable { mutableStateOf("1") }
    Scaffold(topBar={TopAppBar(title={Text("Solicitar préstamo")}, navigationIcon={TextButton(onClick=onBack){Text("Volver")}})}) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
            Text(equipment?.name ?: "Equipo no encontrado", style=MaterialTheme.typography.titleLarge)
            OutlinedTextField(destination,{destination=it}, label={Text("Ambiente o destino")}, isError=errors["destination"]!=null, supportingText={errors["destination"]?.let{Text(it)}}, modifier=Modifier.fillMaxWidth())
            OutlinedTextField(purpose,{purpose=it}, label={Text("Propósito (10 a 180 caracteres)")}, isError=errors["purpose"]!=null, supportingText={errors["purpose"]?.let{Text(it)}}, modifier=Modifier.fillMaxWidth(), minLines=3)
            OutlinedTextField(duration,{duration=it.filter(Char::isDigit)}, label={Text("Duración en horas (1 a 8)")}, isError=errors["duration"]!=null, supportingText={errors["duration"]?.let{Text(it)}}, modifier=Modifier.fillMaxWidth())
            Button(onClick={onSubmit(destination,purpose,duration)}, enabled=!saving && equipment?.available==true, modifier=Modifier.fillMaxWidth()){Text(if(saving) "Guardando…" else "Guardar solicitud")}
            message?.let{Text(it)}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoansScreen(loans: List<Loan>, onBack:()->Unit, onSelect:(Int)->Unit) {
    Scaffold(topBar={TopAppBar(title={Text("Mis préstamos")}, navigationIcon={TextButton(onClick=onBack){Text("Volver")}})}) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement=Arrangement.spacedBy(10.dp)) {
            if (loans.isEmpty()) item { Text("Aún no hay solicitudes registradas.") }
            items(loans,key={it.id}) { loan -> Card(onClick={onSelect(loan.id)}, modifier=Modifier.fillMaxWidth()) { Column(Modifier.padding(16.dp)){Text(loan.equipmentName, style=MaterialTheme.typography.titleMedium); Text("Solicitud #${loan.id} · ${loan.state.name}"); Text("Destino: ${loan.destination}") } } }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanDetailScreen(loan: Loan?, message:String?, onBack:()->Unit, onCancel:(Int)->Unit, onEvidence:(Int,String?)->Unit, onReturn:(Int)->Unit) {
    val context = LocalContext.current
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (loan != null && uri != null) {
            try { context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION) } catch (_: Exception) { }
            onEvidence(loan.id, uri.toString())
        }
    }
    Scaffold(topBar={TopAppBar(title={Text("Detalle de solicitud")}, navigationIcon={TextButton(onClick=onBack){Text("Volver")}})}) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement=Arrangement.spacedBy(10.dp)) {
            if (loan == null) { Text(message ?: "Solicitud no encontrada."); return@Column }
            Text("${loan.equipmentName} · #${loan.id}", style=MaterialTheme.typography.headlineSmall); Text("Estado: ${loan.state.name}"); Text("Destino: ${loan.destination}"); Text("Propósito: ${loan.purpose}"); Text("Duración: ${loan.durationHours} h")
            Text(if(loan.evidenceUri == null) "Sin evidencia fotográfica" else "Evidencia asociada: ${loan.evidenceUri}")
            Button(onClick={picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))}, modifier=Modifier.fillMaxWidth()){Text("Seleccionar evidencia fotográfica")}
            if (loan.state == LoanState.SOLICITADA) OutlinedButton(onClick={onCancel(loan.id)}, modifier=Modifier.fillMaxWidth()){Text("Cancelar solicitud")}
            if (loan.state in setOf(LoanState.SOLICITADA, LoanState.APROBADA, LoanState.ENTREGADA)) Button(onClick={onReturn(loan.id)}, modifier=Modifier.fillMaxWidth()){Text("Registrar devolución")}
            message?.let{Text(it)}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceStatusScreen(onBack:()->Unit) {
    val context = LocalContext.current
    val status = remember { readDeviceStatus(context) }
    Scaffold(topBar={TopAppBar(title={Text("Estado del dispositivo")}, navigationIcon={TextButton(onClick=onBack){Text("Volver")}})}) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) {
            Text("Capacidad física adicional: energía y almacenamiento", style=MaterialTheme.typography.titleLarge)
            Text("Batería: ${if(status.batteryPercent >= 0) "${status.batteryPercent}%" else "No disponible"}")
            Text("Almacenamiento libre de la app: %.2f GB".format(status.freeStorageGb))
            Text("Esta función solo consulta información técnica del dispositivo y no usa ubicación, contactos ni datos personales.")
        }
    }
}

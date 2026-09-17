package com.example.prestamoslabctma.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestamoslabctma.model.*
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable private fun EstadoChip(estado: Any) { AssistChip(onClick={}, label={ Text(estado.toString()) }) }
@Composable private fun Volver(onBack:()->Unit) { TextButton(onClick=onBack){ Text("← Volver") } }

@Composable
fun CatalogoScreen(vm: PrestamoViewModel, onDetalle:(Int)->Unit, onSolicitar:(Int)->Unit) {
    val equipos by vm.equipos.collectAsState()
    LazyColumn(Modifier.fillMaxSize().padding(16.dp).testTag("catalogo"), verticalArrangement=Arrangement.spacedBy(10.dp)) {
        item { Text("Equipos y herramientas", style=MaterialTheme.typography.headlineSmall, fontWeight=FontWeight.Bold); Text("Consulta disponibilidad y solicita recursos para tus prácticas.") }
        if(equipos.isEmpty()) item { Text("No hay equipos disponibles para mostrar.") }
        items(equipos, key={it.id}) { e -> Card(Modifier.fillMaxWidth().clickable{onDetalle(e.id)}) { Column(Modifier.padding(16.dp), verticalArrangement=Arrangement.spacedBy(6.dp)) { Text(e.nombre, fontWeight=FontWeight.Bold); Text(e.categoria.etiqueta); EstadoChip(e.estado); Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) { TextButton(onClick={onDetalle(e.id)}){Text("Ver detalle")}; if(e.estado==EstadoEquipo.DISPONIBLE) Button(onClick={onSolicitar(e.id)}){Text("Solicitar")}} } } }
    }
}

@Composable
fun EquipoDetalleScreen(vm:PrestamoViewModel, id:Int, onBack:()->Unit, onSolicitar:(Int)->Unit) {
    val equipos by vm.equipos.collectAsState(); val e=equipos.find{it.id==id}
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement=Arrangement.spacedBy(12.dp)) { Volver(onBack); if(e==null){Text("Equipo no encontrado", style=MaterialTheme.typography.headlineSmall); Text("El identificador solicitado no existe. Puedes volver al catálogo.")} else {Text(e.nombre, style=MaterialTheme.typography.headlineMedium, fontWeight=FontWeight.Bold); Text("Categoría: ${e.categoria.etiqueta}"); Text("Estado: ${e.estado}"); Text("ID del equipo: ${e.id}"); if(e.estado==EstadoEquipo.DISPONIBLE) Button(onClick={onSolicitar(e.id)}){Text("Solicitar préstamo")} else Text("Este equipo no está disponible para nuevas solicitudes.")}}
}

@Composable
fun SolicitarScreen(vm:PrestamoViewModel,id:Int,onBack:()->Unit,onCreada:()->Unit){
    val equipos by vm.equipos.collectAsState(); val e=equipos.find{it.id==id}; var ambiente by rememberSaveable { mutableStateOf("") }; var proposito by rememberSaveable{mutableStateOf("")}; var duracion by rememberSaveable{mutableStateOf("")}; var intento by rememberSaveable{mutableStateOf(false)}; var mensaje by rememberSaveable{mutableStateOf<String?>(null)}; var guardando by rememberSaveable{mutableStateOf(false)}; val v=vm.validar(ambiente,proposito,duracion)
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement=Arrangement.spacedBy(10.dp)){Volver(onBack); if(e==null){Text("Equipo no encontrado");return@Column}; Text("Solicitar ${e.nombre}",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold); if(e.estado!=EstadoEquipo.DISPONIBLE){Text("El equipo ya no está DISPONIBLE.");return@Column}
        OutlinedTextField(ambiente,{ambiente=it},Modifier.fillMaxWidth().testTag("ambiente"),label={Text("Ambiente o destino")},isError=intento&&v.ambienteError!=null,supportingText={if(intento) v.ambienteError?.let{Text(it)}})
        OutlinedTextField(proposito,{proposito=it},Modifier.fillMaxWidth().testTag("proposito"),label={Text("Propósito (10 a 180 caracteres)")},minLines=3,isError=intento&&v.propositoError!=null,supportingText={if(intento) v.propositoError?.let{Text(it)}})
        OutlinedTextField(duracion,{duracion=it.filter(Char::isDigit)},Modifier.fillMaxWidth().testTag("duracion"),label={Text("Duración en horas (1 a 8)")},isError=intento&&v.duracionError!=null,supportingText={if(intento) v.duracionError?.let{Text(it)}})
        mensaje?.let{Text(it)}
        Button(enabled=!guardando,onClick={ intento=true; if(v.esValida){guardando=true; val r=vm.crear(id,ambiente,proposito,duracion); if(r.isSuccess) onCreada() else {mensaje=r.exceptionOrNull()?.message;guardando=false}}},modifier=Modifier.fillMaxWidth().testTag("guardar")){Text(if(guardando)"Guardando…" else "Guardar solicitud")}
    }
}

@Composable
fun MisSolicitudesScreen(vm:PrestamoViewModel,onDetalle:(Int)->Unit){val solicitudes by vm.solicitudes.collectAsState(); val equipos by vm.equipos.collectAsState(); LazyColumn(Modifier.fillMaxSize().padding(16.dp).testTag("misSolicitudes"),verticalArrangement=Arrangement.spacedBy(10.dp)){item{Text("Mis solicitudes",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold)}; if(solicitudes.isEmpty()) item{Text("Aún no has creado solicitudes de préstamo.")}; items(solicitudes,key={it.id}){s->Card(Modifier.fillMaxWidth().clickable{onDetalle(s.id)}){Column(Modifier.padding(16.dp)){Text(equipos.find{it.id==s.equipoId}?.nombre?:"Equipo ${s.equipoId}",fontWeight=FontWeight.Bold);Text("Solicitud #${s.id}");EstadoChip(s.estado)}}}}}

@Composable
fun SolicitudDetalleScreen(vm:PrestamoViewModel,id:Int,onBack:()->Unit){val solicitudes by vm.solicitudes.collectAsState(); val equipos by vm.equipos.collectAsState(); val s=solicitudes.find{it.id==id}; var mensaje by remember{mutableStateOf<String?>(null)}; Column(Modifier.fillMaxSize().padding(16.dp),verticalArrangement=Arrangement.spacedBy(10.dp)){Volver(onBack); if(s==null){Text("Solicitud no encontrada",style=MaterialTheme.typography.headlineSmall);Text("El identificador solicitado no existe.")}else{Text("Solicitud #${s.id}",style=MaterialTheme.typography.headlineSmall,fontWeight=FontWeight.Bold);Text("Equipo: ${equipos.find{it.id==s.equipoId}?.nombre?:s.equipoId}");Text("Destino: ${s.ambienteDestino}");Text("Propósito: ${s.proposito}");Text("Duración: ${s.duracionHoras} hora(s)");Text("Estado: ${s.estado}");mensaje?.let{Text(it)};if(s.estado==EstadoSolicitud.SOLICITADA) Button(onClick={val r=vm.cancelar(s.id);mensaje=if(r.isSuccess)"Solicitud cancelada. El equipo vuelve a estar DISPONIBLE." else r.exceptionOrNull()?.message}){Text("Cancelar solicitud")}else Text("Esta solicitud no admite cancelación en su estado actual.")}}}

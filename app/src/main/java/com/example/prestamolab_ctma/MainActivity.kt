package com.example.prestamolab_ctma

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prestamolab_ctma.model.CategoriaEquipo
import com.example.prestamolab_ctma.model.Equipo
import com.example.prestamolab_ctma.model.EstadoEquipo
import com.example.prestamolab_ctma.model.EstadoSolicitud
import com.example.prestamolab_ctma.model.SolicitudPrestamo
import com.example.prestamolab_ctma.ui.theme.PrestamolabctmaTheme
import com.example.prestamolab_ctma.viewmodel.LoadState
import com.example.prestamolab_ctma.viewmodel.PrestamoUiState
import com.example.prestamolab_ctma.viewmodel.PrestamoViewModel


class MainActivity : ComponentActivity() {

 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)

  enableEdgeToEdge()

  createChannel(this)

  setContent {
   PrestamolabctmaTheme {
    App()
   }
  }
 }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {

 val app =
  LocalContext.current.applicationContext as PrestamoApplication

 val vm: PrestamoViewModel = viewModel(
  factory = PrestamoViewModel.Factory(
   app.repository,
   app.preferences
  )
 )

 val state by vm.uiState.collectAsStateWithLifecycle()

 var screen by remember {
  mutableStateOf("catalogo")
 }

 var selected by remember {
  mutableIntStateOf(0)
 }

 val snack = remember {
  SnackbarHostState()
 }

 LaunchedEffect(state.mensaje) {
  state.mensaje?.let { mensaje ->
   snack.showSnackbar(mensaje)
   vm.limpiarMensaje()
  }
 }

 Scaffold(
  topBar = {

   TopAppBar(
    title = {
     Text(
      text = "PréstamoLab CTMA",
      fontWeight = FontWeight.Bold
     )
    },
    actions = {

     IconButton(
      onClick = {
       screen = "catalogo"
      }
     ) {
      Icon(
       imageVector = Icons.Default.Home,
       contentDescription = "Catálogo"
      )
     }

     IconButton(
      onClick = {
       screen = "solicitudes"
      }
     ) {
      Icon(
       imageVector = Icons.AutoMirrored.Filled.List,
       contentDescription = "Solicitudes"
      )
     }

     IconButton(
      onClick = {
       vm.sincronizar()
      }
     ) {
      Icon(
       imageVector = Icons.Default.Sync,
       contentDescription = "Sincronizar"
      )
     }
    }
   )
  },

  snackbarHost = {
   SnackbarHost(snack)
  }

 ) { pad ->

  when (screen) {

   "catalogo" -> {

    Catalogo(
     state = state,
     vm = vm,
     onClick = { id ->
      screen = "detalle"
      selected = id
     },
     modifier = Modifier.padding(pad)
    )
   }

   "detalle" -> {

    val equipo = state.equipos.find {
     it.id == selected
    }

    if (equipo != null) {

     Detalle(
      equipo = equipo,
      onSolicitar = {
       screen = "solicitar"
      },
      modifier = Modifier.padding(pad)
     )
    }
   }

   "solicitar" -> {

    val equipo = state.equipos.find {
     it.id == selected
    }

    if (equipo != null) {

     Solicitar(
      equipo = equipo,
      onSave = { destino, proposito, horas ->

       vm.crearSolicitud(
        e = equipo.id,
        d = destino,
        p = proposito,
        h = horas
       ) {
        screen = "solicitudes"
       }
      },
      modifier = Modifier.padding(pad)
     )
    }
   }

   "solicitudes" -> {

    Solicitudes(
     state = state,
     onClick = { id ->
      screen = "solSolicitud"
      selected = id
     },
     modifier = Modifier.padding(pad)
    )
   }

   "solSolicitud" -> {

    val solicitud = state.solicitudes.find {
     it.id == selected
    }

    if (solicitud != null) {

     DetalleSolicitud(
      solicitud = solicitud,
      equipo = state.equipos.find {
       it.id == solicitud.equipoId
      },
      vm = vm,
      modifier = Modifier.padding(pad)
     )
    }
   }
  }
 }
}


@Composable
fun Catalogo(
 state: PrestamoUiState,
 vm: PrestamoViewModel,
 onClick: (Int) -> Unit,
 modifier: Modifier
) {

 val cats =
  listOf("TODAS") + CategoriaEquipo.entries.map {
   it.name
  }

 Column(
  modifier = modifier
   .fillMaxSize()
   .padding(16.dp)
 ) {

  Text(
   text = "Catálogo de equipos",
   style = MaterialTheme.typography.headlineSmall
  )

  Spacer(
   modifier = Modifier.height(12.dp)
  )

  Row(
   horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {

   cats.take(3).forEach { categoria ->

    FilterChip(
     selected = state.filtroCategoria == categoria,

     onClick = {
      vm.guardarFiltro(categoria)
     },

     label = {
      Text(
       text = categoria.take(10)
      )
     }
    )
   }
  }

  Spacer(
   modifier = Modifier.height(12.dp)
  )

  when (val loadState = state.loadState) {

   LoadState.Loading -> {

    Box(
     modifier = Modifier
      .fillMaxWidth()
      .weight(1f),
     contentAlignment = Alignment.Center
    ) {

     CircularProgressIndicator()
    }
   }

   is LoadState.Error -> {

    Box(
     modifier = Modifier
      .fillMaxWidth()
      .weight(1f),
     contentAlignment = Alignment.Center
    ) {

     Text(
      text = loadState.message
     )
    }
   }

   LoadState.Empty -> {

    Box(
     modifier = Modifier
      .fillMaxWidth()
      .weight(1f),
     contentAlignment = Alignment.Center
    ) {

     Text(
      text = "No hay equipos"
     )
    }
   }

   LoadState.Content -> {

    val equiposFiltrados =
     state.equipos.filter {
      state.filtroCategoria == "TODAS" ||
              it.categoria.name == state.filtroCategoria
     }

    LazyColumn(
     modifier = Modifier
      .fillMaxWidth()
      .weight(1f),

     verticalArrangement =
      Arrangement.spacedBy(8.dp)
    ) {

     items(
      items = equiposFiltrados,
      key = { equipo ->
       equipo.id
      }
     ) { equipo ->

      Card(
       modifier = Modifier
        .fillMaxWidth()
        .clickable {
         onClick(equipo.id)
        }
      ) {

       Column(
        modifier = Modifier.padding(16.dp)
       ) {

        Text(
         text = equipo.nombre,
         fontWeight = FontWeight.Bold
        )

        Text(
         text =
          "${equipo.categoria} · ${equipo.estado}"
        )

        Text(
         text = equipo.descripcion
        )
       }
      }
     }
    }
   }
  }
 }
}


@Composable
fun Detalle(
 equipo: Equipo,
 onSolicitar: () -> Unit,
 modifier: Modifier
) {

 Column(
  modifier = modifier
   .fillMaxSize()
   .padding(20.dp),

  verticalArrangement =
   Arrangement.spacedBy(12.dp)
 ) {

  Text(
   text = "Detalle del equipo",
   style = MaterialTheme.typography.headlineSmall
  )

  Text(
   text = equipo.nombre,
   style = MaterialTheme.typography.headlineMedium,
   fontWeight = FontWeight.Bold
  )

  Text(
   text = "Categoría: ${equipo.categoria}"
  )

  Text(
   text = "Estado: ${equipo.estado}"
  )

  Text(
   text = equipo.descripcion
  )

  Button(
   onClick = onSolicitar,
   enabled =
    equipo.estado == EstadoEquipo.DISPONIBLE,
   modifier = Modifier.fillMaxWidth()
  ) {

   Text(
    text = "Solicitar préstamo"
   )
  }
 }
}


@Composable
fun Solicitar(
 equipo: Equipo,
 onSave: (String, String, Int) -> Unit,
 modifier: Modifier
) {

 var destino by remember {
  mutableStateOf("")
 }

 var proposito by remember {
  mutableStateOf("")
 }

 var horas by remember {
  mutableStateOf("2")
 }

 val horasNumero =
  horas.toIntOrNull() ?: 0

 val formularioValido =
  destinoValido(destino) &&
          propositoValido(proposito) &&
          duracionValida(horasNumero)

 Column(
  modifier = modifier
   .fillMaxSize()
   .padding(20.dp),

  verticalArrangement =
   Arrangement.spacedBy(10.dp)
 ) {

  Text(
   text = "Solicitar: ${equipo.nombre}",
   style = MaterialTheme.typography.headlineSmall
  )

  OutlinedTextField(
   value = destino,

   onValueChange = {
    destino = it
   },

   modifier = Modifier.fillMaxWidth(),

   label = {
    Text("Ambiente / destino")
   }
  )

  OutlinedTextField(
   value = proposito,

   onValueChange = {
    proposito = it
   },

   modifier = Modifier.fillMaxWidth(),

   label = {
    Text("Propósito")
   },

   minLines = 3
  )

  OutlinedTextField(
   value = horas,

   onValueChange = {
    horas = it
     .filter(Char::isDigit)
     .take(2)
   },

   modifier = Modifier.fillMaxWidth(),

   label = {
    Text("Duración (1 a 8 horas)")
   }
  )

  Button(
   onClick = {

    onSave(
     destino.trim(),
     proposito.trim(),
     horasNumero
    )
   },

   enabled = formularioValido,

   modifier = Modifier.fillMaxWidth()
  ) {

   Text(
    text = "Enviar solicitud"
   )
  }
 }
}


@Composable
fun Solicitudes(
 state: PrestamoUiState,
 onClick: (Int) -> Unit,
 modifier: Modifier
) {

 if (state.solicitudes.isEmpty()) {

  Box(
   modifier = modifier.fillMaxSize(),
   contentAlignment = Alignment.Center
  ) {

   Text(
    text = "No hay solicitudes"
   )
  }

 } else {

  LazyColumn(
   modifier = modifier
    .fillMaxSize()
    .padding(16.dp),

   verticalArrangement =
    Arrangement.spacedBy(8.dp)
  ) {

   items(
    items = state.solicitudes,
    key = { solicitud ->
     solicitud.id
    }
   ) { solicitud ->

    Card(
     modifier = Modifier
      .fillMaxWidth()
      .clickable {
       onClick(solicitud.id)
      }
    ) {

     Column(
      modifier = Modifier.padding(16.dp)
     ) {

      Text(
       text =
        "Solicitud #${solicitud.id}",

       fontWeight =
        FontWeight.Bold
      )

      Text(
       text =
        "Equipo #${solicitud.equipoId}"
      )

      Text(
       text =
        "Estado: ${solicitud.estado}"
      )
     }
    }
   }
  }
 }
}


@Composable
fun DetalleSolicitud(
 solicitud: SolicitudPrestamo,
 equipo: Equipo?,
 vm: PrestamoViewModel,
 modifier: Modifier
) {

 val context = LocalContext.current

 // Selector de imágenes

 val picker =
  rememberLauncherForActivityResult(
   contract =
    ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->

   uri?.let {

    vm.guardarEvidencia(
     id = solicitud.id,
     uri = it.toString(),
     nombre =
      "evidencia_${solicitud.id}"
    )
   }
  }


 // Solicitud de permisos de ubicación

 val location =
  rememberLauncherForActivityResult(
   contract =
    ActivityResultContracts.RequestMultiplePermissions()
  ) { permisos ->

   val tienePermisoFino =
    permisos[
     Manifest.permission.ACCESS_FINE_LOCATION
    ] == true

   val tienePermisoAproximado =
    permisos[
     Manifest.permission.ACCESS_COARSE_LOCATION
    ] == true

   if (
    tienePermisoFino ||
    tienePermisoAproximado
   ) {

    val locationManager =
     context.getSystemService(
      Context.LOCATION_SERVICE
     ) as LocationManager

    val ubicacion =
     listOf(
      LocationManager.GPS_PROVIDER,
      LocationManager.NETWORK_PROVIDER
     )
      .asSequence()
      .mapNotNull { provider ->

       if (
        ContextCompat.checkSelfPermission(
         context,
         Manifest.permission.ACCESS_FINE_LOCATION
        ) ==
        PackageManager.PERMISSION_GRANTED
        ||
        ContextCompat.checkSelfPermission(
         context,
         Manifest.permission.ACCESS_COARSE_LOCATION
        ) ==
        PackageManager.PERMISSION_GRANTED
       ) {

        runCatching {
         locationManager
          .getLastKnownLocation(provider)
        }.getOrNull()

       } else {
        null
       }
      }
      .firstOrNull()

    if (ubicacion != null) {

     vm.guardarUbicacion(
      id = solicitud.id,
      lat = ubicacion.latitude,
      lon = ubicacion.longitude
     )
    }
   }
  }


 Column(
  modifier = modifier
   .fillMaxSize()
   .padding(20.dp),

  verticalArrangement =
   Arrangement.spacedBy(10.dp)
 ) {

  Text(
   text =
    "Solicitud #${solicitud.id}",

   style =
    MaterialTheme.typography.headlineSmall
  )

  Text(
   text =
    "Equipo: ${
     equipo?.nombre
      ?: solicitud.equipoId
    }"
  )

  Text(
   text =
    "Estado: ${solicitud.estado}"
  )

  Text(
   text =
    "Destino: ${solicitud.ambienteDestino}"
  )

  Text(
   text =
    "Propósito: ${solicitud.proposito}"
  )

  Text(
   text =
    "Duración: ${solicitud.duracionHoras} h"
  )


  // Evidencia fotográfica

  Button(
   onClick = {

    picker.launch(
     PickVisualMediaRequest(
      ActivityResultContracts
       .PickVisualMedia
       .ImageOnly
     )
    )
   },

   modifier =
    Modifier.fillMaxWidth()
  ) {

   Text(
    text =
     "Adjuntar evidencia fotográfica"
   )
  }


  // Ubicación GPS

  Button(
   onClick = {

    location.launch(
     arrayOf(
      Manifest.permission
       .ACCESS_FINE_LOCATION,

      Manifest.permission
       .ACCESS_COARSE_LOCATION
     )
    )
   },

   modifier =
    Modifier.fillMaxWidth()
  ) {

   Text(
    text =
     "Registrar ubicación GPS"
   )
  }


  // Recordatorio

  Button(
   onClick = {

    sendReminder(
     context,
     solicitud.id
    )
   },

   modifier =
    Modifier.fillMaxWidth()
  ) {

   Text(
    text =
     "Recordatorio de devolución"
   )
  }


  // Cancelar solicitud

  if (
   solicitud.estado ==
   EstadoSolicitud.SOLICITADA
  ) {

   OutlinedButton(
    onClick = {

     vm.cancelarSolicitud(
      solicitud.id
     )
    },

    modifier =
     Modifier.fillMaxWidth()
   ) {

    Text(
     text =
      "Cancelar solicitud"
    )
   }
  }
 }
}


// --------------------------------------------------
// NOTIFICACIONES
// --------------------------------------------------

fun createChannel(
 context: Context
) {

 if (Build.VERSION.SDK_INT >= 26) {

  val channel =
   NotificationChannel(
    "devoluciones",
    "Recordatorios de devolución",
    NotificationManager.IMPORTANCE_DEFAULT
   )

  context
   .getSystemService(
    NotificationManager::class.java
   )
   .createNotificationChannel(channel)
 }
}


fun sendReminder(
 context: Context,
 id: Int
) {

 createChannel(context)

 if (
  Build.VERSION.SDK_INT >= 33 &&
  ContextCompat.checkSelfPermission(
   context,
   Manifest.permission.POST_NOTIFICATIONS
  ) != PackageManager.PERMISSION_GRANTED
 ) {
  return
 }

 val notification =
  NotificationCompat.Builder(
   context,
   "devoluciones"
  )
   .setSmallIcon(
    com.example.prestamolab_ctma
     .R.drawable.ic_launcher_foreground
   )
   .setContentTitle(
    "PréstamoLab CTMA"
   )
   .setContentText(
    "Recuerda revisar la devolución de la solicitud #$id."
   )
   .setAutoCancel(true)
   .build()

 context
  .getSystemService(
   NotificationManager::class.java
  )
  .notify(
   id,
   notification
  )
}


// --------------------------------------------------
// VALIDACIONES DEL FORMULARIO
// --------------------------------------------------

fun destinoValido(
 destino: String
): Boolean {

 return destino.trim().length >= 3
}


fun propositoValido(
 proposito: String
): Boolean {

 return proposito.trim().length >= 5
}


fun duracionValida(
 horas: Int
): Boolean {

 return horas in 1..8
}
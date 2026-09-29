package com.example.prestamoslabctma.ui.solicitud

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestamoslabctma.device.LocationManager
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun SolicitudScreen(
    equipoId: Int,
    viewModel: PrestamoViewModel,
    onSolicitudCreada: () -> Unit,
    onCancelar: () -> Unit
) {
    val equipo = viewModel.obtenerEquipo(equipoId)
    val uiState by viewModel.uiState.collectAsState()

    var ambienteDestino by remember {
        mutableStateOf("")
    }

    var proposito by remember {
        mutableStateOf("")
    }

    var duracionHoras by remember {
        mutableIntStateOf(1)
    }

    var intentoGuardar by remember {
        mutableStateOf(false)
    }

    var mostrarConfirmacion by remember {
        mutableStateOf(false)
    }

    var fotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val selectorFoto =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            fotoUri = uri
        }

    val context =
        androidx.compose.ui.platform.LocalContext.current

    val locationManager = remember {
        LocationManager(context)
    }

    var latitud by remember {
        mutableStateOf<Double?>(null)
    }

    var longitud by remember {
        mutableStateOf<Double?>(null)
    }

    var obteniendoUbicacion by remember {
        mutableStateOf(false)
    }

    var mensajeUbicacion by remember {
        mutableStateOf("")
    }

    val permisosUbicacionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.RequestMultiplePermissions()
        ) { permisos ->

            val permisoConcedido =
                permisos[
                    Manifest.permission.ACCESS_FINE_LOCATION
                ] == true ||
                        permisos[
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ] == true

            if (permisoConcedido) {

                obteniendoUbicacion = true
                mensajeUbicacion =
                    "Obteniendo ubicación..."

            } else {

                mensajeUbicacion =
                    "No se concedió el permiso de ubicación."
            }
        }

    fun solicitarUbicacion() {

        if (locationManager.tienePermiso()) {

            obteniendoUbicacion = true
            mensajeUbicacion =
                "Obteniendo ubicación..."

        } else {

            permisosUbicacionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(obteniendoUbicacion) {

        if (obteniendoUbicacion) {

            val ubicacion =
                locationManager.obtenerUbicacion()

            if (ubicacion != null) {

                latitud = ubicacion.first
                longitud = ubicacion.second

                mensajeUbicacion =
                    "Ubicación obtenida correctamente."

            } else {

                mensajeUbicacion =
                    "No fue posible obtener la ubicación."
            }

            obteniendoUbicacion = false
        }
    }

    // =========================
    // VALIDACIONES
    // =========================

    val ambienteLimpio =
        ambienteDestino.trim()

    val propositoLimpio =
        proposito.trim()

    val ambienteValido =
        ambienteLimpio.length in 3..100

    val propositoValido =
        propositoLimpio.length in 10..180

    val duracionValida =
        duracionHoras in 1..8

    // =========================
    // EQUIPO NO ENCONTRADO
    // =========================

    if (equipo == null) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 48.dp
                ),
            verticalArrangement =
                Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Equipo no encontrado",
                style =
                    MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text =
                    "El equipo seleccionado no está disponible."
            )

            Button(
                onClick = onCancelar,
                modifier =
                    Modifier.fillMaxWidth(),
                shape =
                    RoundedCornerShape(14.dp)
            ) {
                Text("Volver")
            }
        }

        return
    }

    // =========================
    // PANTALLA
    // =========================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 20.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Solicitar préstamo",
            style =
                MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text =
                "Completa la información para solicitar el equipo.",
            style =
                MaterialTheme.typography.bodyMedium,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        // =========================
        // INFORMACIÓN DEL USUARIO
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement =
                    Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = "Información del usuario",
                    style =
                        MaterialTheme.typography.labelLarge,
                    color =
                        MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = uiState.usuario.nombre,
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text =
                        "Identificación: ${uiState.usuario.identificacion}",
                    style =
                        MaterialTheme.typography.bodyMedium
                )

                Text(
                    text =
                        "Rol: ${uiState.usuario.rol}",
                    style =
                        MaterialTheme.typography.bodyMedium,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // =========================
        // EQUIPO
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement =
                    Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = "Equipo seleccionado",
                    style =
                        MaterialTheme.typography.labelLarge,
                    color =
                        MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = equipo.nombre,
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "ID: ${equipo.id}",
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // =========================
        // AMBIENTE
        // =========================

        OutlinedTextField(
            value = ambienteDestino,
            onValueChange = {

                if (it.length <= 100) {
                    ambienteDestino = it
                }
            },
            label = {
                Text("Ambiente de destino")
            },
            supportingText = {
                Text("${ambienteDestino.length}/100 caracteres")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError =
                intentoGuardar && !ambienteValido,
            shape = RoundedCornerShape(14.dp)
        )

        if (intentoGuardar && !ambienteValido) {

            Text(
                text =
                    "El ambiente debe tener entre 3 y 100 caracteres.",
                color =
                    MaterialTheme.colorScheme.error,
                style =
                    MaterialTheme.typography.bodySmall
            )
        }

        // =========================
        // PROPÓSITO
        // =========================

        OutlinedTextField(
            value = proposito,
            onValueChange = {

                if (it.length <= 180) {
                    proposito = it
                }
            },
            label = {
                Text("Propósito del préstamo")
            },
            supportingText = {
                Text("${proposito.length}/180 caracteres")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            isError =
                intentoGuardar && !propositoValido,
            shape = RoundedCornerShape(14.dp)
        )

        if (intentoGuardar && !propositoValido) {

            Text(
                text =
                    "El propósito debe tener entre 10 y 180 caracteres.",
                color =
                    MaterialTheme.colorScheme.error,
                style =
                    MaterialTheme.typography.bodySmall
            )
        }

        // =========================
        // DURACIÓN
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Duración del préstamo",
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$duracionHoras hora(s)",
                    style =
                        MaterialTheme.typography.headlineSmall,
                    color =
                        MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    OutlinedButton(
                        onClick = {

                            if (duracionHoras > 1) {
                                duracionHoras--
                            }
                        },
                        modifier =
                            Modifier.weight(1f),
                        shape =
                            RoundedCornerShape(12.dp),
                        enabled =
                            duracionHoras > 1
                    ) {
                        Text("−")
                    }

                    Spacer(
                        modifier =
                            Modifier.width(12.dp)
                    )

                    Button(
                        onClick = {

                            if (duracionHoras < 8) {
                                duracionHoras++
                            }
                        },
                        modifier =
                            Modifier.weight(1f),
                        shape =
                            RoundedCornerShape(12.dp),
                        enabled =
                            duracionHoras < 8
                    ) {
                        Text("+")
                    }
                }

                Text(
                    text =
                        "Puedes solicitar entre 1 y 8 horas.",
                    style =
                        MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // =========================
        // FOTOGRAFÍA
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Evidencia fotográfica",
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text =
                        if (fotoUri != null) {
                            "Fotografía seleccionada correctamente."
                        } else {
                            "Puedes adjuntar una fotografía del equipo."
                        },
                    style =
                        MaterialTheme.typography.bodyMedium
                )

                Button(
                    onClick = {
                        selectorFoto.launch("image/*")
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    Text(
                        text =
                            if (fotoUri != null) {
                                "Cambiar fotografía"
                            } else {
                                "Seleccionar fotografía"
                            }
                    )
                }

                if (fotoUri != null) {

                    Text(
                        text =
                            "Fotografía adjunta ✓",
                        color =
                            MaterialTheme.colorScheme.primary,
                        fontWeight =
                            FontWeight.Bold
                    )
                }
            }
        }

        // =========================
        // GPS
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation =
                CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
        ) {

            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "Ubicación",
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                if (
                    latitud != null &&
                    longitud != null
                ) {

                    Text(
                        text = "Latitud: $latitud"
                    )

                    Text(
                        text = "Longitud: $longitud"
                    )

                } else {

                    Text(
                        text =
                            "Aún no se ha obtenido la ubicación.",
                        color =
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (mensajeUbicacion.isNotBlank()) {

                    Text(
                        text = mensajeUbicacion,
                        color =
                            if (
                                mensajeUbicacion.contains(
                                    "correctamente"
                                )
                            ) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                    )
                }

                Button(
                    onClick = {
                        solicitarUbicacion()
                    },
                    modifier =
                        Modifier.fillMaxWidth(),
                    enabled =
                        !obteniendoUbicacion,
                    shape =
                        RoundedCornerShape(14.dp)
                ) {

                    if (obteniendoUbicacion) {

                        CircularProgressIndicator()

                    } else {

                        Text(
                            text =
                                if (
                                    latitud != null &&
                                    longitud != null
                                ) {
                                    "Actualizar ubicación"
                                } else {
                                    "Obtener mi ubicación"
                                }
                        )
                    }
                }
            }
        }

        // =========================
        // MENSAJE
        // =========================

        uiState.mensaje?.let { mensaje ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {

                Text(
                    text = mensaje,
                    modifier =
                        Modifier.padding(14.dp),
                    color =
                        if (
                            mensaje.contains(
                                "correctamente"
                            )
                        ) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                )
            }
        }

        // =========================
        // REVISAR
        // =========================

        Button(
            onClick = {

                intentoGuardar = true

                if (
                    ambienteValido &&
                    propositoValido &&
                    duracionValida &&
                    !uiState.guardando
                ) {
                    mostrarConfirmacion = true
                }
            },
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(14.dp),
            enabled =
                !uiState.guardando
        ) {

            if (uiState.guardando) {

                CircularProgressIndicator()

            } else {

                Text(
                    text = "Revisar solicitud",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // =========================
        // CANCELAR
        // =========================

        OutlinedButton(
            onClick = onCancelar,
            modifier =
                Modifier.fillMaxWidth(),
            shape =
                RoundedCornerShape(14.dp)
        ) {
            Text("Cancelar")
        }
    }

    // =========================
    // CONFIRMACIÓN
    // =========================

    if (mostrarConfirmacion) {

        AlertDialog(
            onDismissRequest = {
                mostrarConfirmacion = false
            },
            title = {

                Text(
                    text = "Confirmar solicitud",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {

                Column(
                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text =
                            "Revisa los datos antes de registrar el préstamo."
                    )

                    Text(
                        text =
                            "Equipo: ${equipo.nombre}",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                            "Ambiente: $ambienteLimpio"
                    )

                    Text(
                        text =
                            "Propósito: $propositoLimpio"
                    )

                    Text(
                        text =
                            "Duración: $duracionHoras hora(s)"
                    )

                    Text(
                        text =
                            if (fotoUri != null) {
                                "Fotografía: adjunta ✓"
                            } else {
                                "Fotografía: no adjunta"
                            },
                        color =
                            if (fotoUri != null) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                    )

                    Text(
                        text =
                            if (
                                latitud != null &&
                                longitud != null
                            ) {
                                "Ubicación: $latitud, $longitud"
                            } else {
                                "Ubicación: no registrada"
                            }
                    )
                }
            },
            confirmButton = {

                Button(
                    onClick = {

                        if (
                            !uiState.guardando &&
                            ambienteValido &&
                            propositoValido &&
                            duracionValida
                        ) {

                            viewModel.crearSolicitud(
                                equipoId = equipoId,
                                ambienteDestino =
                                    ambienteLimpio,
                                proposito =
                                    propositoLimpio,
                                duracionHoras =
                                    duracionHoras
                            ) { resultado ->

                                if (resultado) {
                                    onSolicitudCreada()
                                }
                            }

                            mostrarConfirmacion = false
                        }
                    },
                    enabled =
                        !uiState.guardando
                ) {
                    Text("Confirmar solicitud")
                }
            },
            dismissButton = {

                OutlinedButton(
                    onClick = {
                        mostrarConfirmacion = false
                    }
                ) {
                    Text("Volver a editar")
                }
            }
        )
    }
}
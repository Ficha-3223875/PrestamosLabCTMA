package com.example.prestamoslabctma.ui.solicitud

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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

    val ambienteValido = ambienteDestino.trim().isNotEmpty()
    val propositoValido = proposito.trim().length in 10..180
    val duracionValida = duracionHoras in 1..8

    if (equipo == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 48.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Equipo no encontrado",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "El equipo con ID $equipoId no existe."
            )

            Button(
                onClick = onCancelar,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Volver")
            }
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Solicitar préstamo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Completa la información para solicitar el equipo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // =========================
        // INFORMACIÓN DEL USUARIO
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Información del usuario",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = uiState.usuario.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Identificación: ${uiState.usuario.identificacion}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Rol: ${uiState.usuario.rol}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // =========================
        // EQUIPO SELECCIONADO
        // =========================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Equipo seleccionado",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = equipo.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "ID: ${equipo.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // =========================
        // AMBIENTE DE DESTINO
        // =========================

        OutlinedTextField(
            value = ambienteDestino,
            onValueChange = {
                ambienteDestino = it
            },
            label = {
                Text("Ambiente de destino")
            },
            supportingText = {
                Text("Ejemplo: Ambiente 101")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = intentoGuardar && !ambienteValido,
            shape = RoundedCornerShape(14.dp)
        )

        if (intentoGuardar && !ambienteValido) {
            Text(
                text = "El ambiente de destino es obligatorio.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
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
            isError = intentoGuardar && !propositoValido,
            shape = RoundedCornerShape(14.dp)
        )

        if (intentoGuardar && !propositoValido) {
            Text(
                text = "El propósito debe tener entre 10 y 180 caracteres.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
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
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Duración del préstamo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$duracionHoras hora(s)",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
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
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        enabled = duracionHoras > 1
                    ) {
                        Text("−")
                    }

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Button(
                        onClick = {
                            if (duracionHoras < 8) {
                                duracionHoras++
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        enabled = duracionHoras < 8
                    ) {
                        Text("+")
                    }
                }

                Text(
                    text = "Puedes solicitar entre 1 y 8 horas.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        if (intentoGuardar && !duracionValida) {
            Text(
                text = "La duración debe estar entre 1 y 8 horas.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
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
                    modifier = Modifier.padding(14.dp),
                    color = if (
                        mensaje.contains("correctamente")
                    ) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
        }

        // =========================
        // BOTÓN REVISAR
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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            enabled = !uiState.guardando
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
        // BOTÓN CANCELAR
        // =========================

        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Cancelar")
        }
    }

    // =========================
    // VENTANA DE CONFIRMACIÓN
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
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = "Revisa los datos antes de registrar el préstamo."
                    )

                    Text(
                        text = "Equipo: ${equipo.nombre}",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Ambiente: ${ambienteDestino.trim()}"
                    )

                    Text(
                        text = "Propósito: ${proposito.trim()}"
                    )

                    Text(
                        text = "Duración: $duracionHoras hora(s)"
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarConfirmacion = false

                        viewModel.crearSolicitud(
                            equipoId = equipoId,
                            ambienteDestino = ambienteDestino,
                            proposito = proposito,
                            duracionHoras = duracionHoras
                        )

                        onSolicitudCreada()
                    },
                    enabled = !uiState.guardando
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
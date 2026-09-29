package com.example.prestamoslabctma.ui.equipo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun EquipoDetalleScreen(
    equipoId: Int,
    viewModel: PrestamoViewModel,
    onSolicitarClick: (Int) -> Unit,
    onVolver: () -> Unit
) {
    val equipo = viewModel.obtenerEquipo(equipoId)

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
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text("Volver al catálogo")
            }
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Detalle del equipo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Información del equipo seleccionado",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = equipo.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "ID del equipo: ${equipo.id}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Categoría: ${textoCategoria(equipo.categoria.name)}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Estado",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                EstadoChip(
                    estado = equipo.estado
                )
            }
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        if (equipo.estado == EstadoEquipo.DISPONIBLE) {

            Button(
                onClick = {
                    onSolicitarClick(equipo.id)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Solicitar préstamo",
                    fontWeight = FontWeight.Bold
                )
            }

        } else {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "Este equipo no está disponible para préstamo.",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Volver al catálogo")
        }
    }
}

@Composable
private fun EstadoChip(
    estado: EstadoEquipo
) {
    val texto = when (estado) {
        EstadoEquipo.DISPONIBLE -> "Disponible"
        EstadoEquipo.RESERVADO -> "Reservado"
        EstadoEquipo.PRESTADO -> "Prestado"
    }

    val containerColor = when (estado) {
        EstadoEquipo.DISPONIBLE ->
            MaterialTheme.colorScheme.primaryContainer

        EstadoEquipo.RESERVADO ->
            MaterialTheme.colorScheme.secondaryContainer

        EstadoEquipo.PRESTADO ->
            MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when (estado) {
        EstadoEquipo.DISPONIBLE ->
            MaterialTheme.colorScheme.onPrimaryContainer

        EstadoEquipo.RESERVADO ->
            MaterialTheme.colorScheme.onSecondaryContainer

        EstadoEquipo.PRESTADO ->
            MaterialTheme.colorScheme.onErrorContainer
    }

    Surface(
        shape = RoundedCornerShape(50.dp),
        color = containerColor
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 7.dp
            ),
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun textoCategoria(
    categoria: String
): String {
    return when (categoria) {
        "COMPUTO" -> "Cómputo"
        "AUDIOVISUAL" -> "Audiovisual"
        "HERRAMIENTA" -> "Herramienta"
        "LABORATORIO" -> "Laboratorio"
        else -> "Otro"
    }
}
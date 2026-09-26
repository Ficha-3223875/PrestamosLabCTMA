package com.example.prestamoslabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun SolicitudDetalleScreen(
    solicitudId: Int,
    viewModel: PrestamoViewModel,
    onVolver: () -> Unit,
    onSolicitudCancelada: () -> Unit
) {
    val solicitud = viewModel.obtenerSolicitud(solicitudId)

    if (solicitud == null) {
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
                text = "Solicitud no encontrada",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "La solicitud con ID $solicitudId no existe."
            )

            Button(
                onClick = onVolver,
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
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 20.dp
            ),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "PréstamoLab CTMA",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Detalle de solicitud",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Información de tu solicitud de préstamo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(4.dp)
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
                    text = "Solicitud #${solicitud.id}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Equipo ID: ${solicitud.equipoId}"
                )

                Text(
                    text = "Ambiente de destino: ${solicitud.ambienteDestino}"
                )

                Text(
                    text = "Propósito:",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = solicitud.proposito,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "Duración: ${solicitud.duracionHoras} hora(s)"
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Estado",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

                EstadoSolicitudChip(
                    estado = solicitud.estado
                )
            }
        }

        if (solicitud.estado == EstadoSolicitud.SOLICITADA) {

            Button(
                onClick = {
                    viewModel.cancelarSolicitud(solicitud.id)

                    // Volver automáticamente a Mis solicitudes
                    onSolicitudCancelada()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = "Cancelar solicitud",
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
                    text = "Esta solicitud ya no se puede cancelar.",
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
            Text("Volver a mis solicitudes")
        }
    }
}

@Composable
private fun EstadoSolicitudChip(
    estado: EstadoSolicitud
) {
    val texto = when (estado) {
        EstadoSolicitud.SOLICITADA -> "Solicitada"
        EstadoSolicitud.APROBADA -> "Aprobada"
        EstadoSolicitud.ENTREGADA -> "Entregada"
        EstadoSolicitud.DEVUELTA -> "Devuelta"
        EstadoSolicitud.CANCELADA -> "Cancelada"
        EstadoSolicitud.RECHAZADA -> "Rechazada"
    }

    val containerColor = when (estado) {
        EstadoSolicitud.SOLICITADA ->
            MaterialTheme.colorScheme.primaryContainer

        EstadoSolicitud.APROBADA ->
            MaterialTheme.colorScheme.secondaryContainer

        EstadoSolicitud.ENTREGADA ->
            MaterialTheme.colorScheme.tertiaryContainer

        EstadoSolicitud.DEVUELTA ->
            MaterialTheme.colorScheme.surfaceVariant

        EstadoSolicitud.CANCELADA ->
            MaterialTheme.colorScheme.errorContainer

        EstadoSolicitud.RECHAZADA ->
            MaterialTheme.colorScheme.errorContainer
    }

    val contentColor = when (estado) {
        EstadoSolicitud.SOLICITADA ->
            MaterialTheme.colorScheme.onPrimaryContainer

        EstadoSolicitud.APROBADA ->
            MaterialTheme.colorScheme.onSecondaryContainer

        EstadoSolicitud.ENTREGADA ->
            MaterialTheme.colorScheme.onTertiaryContainer

        EstadoSolicitud.DEVUELTA ->
            MaterialTheme.colorScheme.onSurfaceVariant

        EstadoSolicitud.CANCELADA ->
            MaterialTheme.colorScheme.onErrorContainer

        EstadoSolicitud.RECHAZADA ->
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
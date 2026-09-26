package com.example.prestamoslabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestamoslabctma.model.EstadoSolicitud
import com.example.prestamoslabctma.model.SolicitudPrestamo
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun MisSolicitudesScreen(
    viewModel: PrestamoViewModel,
    onSolicitudClick: (Int) -> Unit
) {
    val solicitudes = viewModel.uiState.value.solicitudes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 40.dp,
                bottom = 16.dp
            )
    ) {

        Text(
            text = "PréstamoLab CTMA",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Mis solicitudes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Consulta y revisa el estado de tus solicitudes de préstamo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (solicitudes.isEmpty()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "No tienes solicitudes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Cuando solicites un equipo, aparecerá aquí."
                    )
                }
            }

        } else {

            Text(
                text = "${solicitudes.size} solicitud(es)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(
                    items = solicitudes,
                    key = { it.id }
                ) { solicitud ->

                    SolicitudCard(
                        solicitud = solicitud,
                        onClick = {
                            onSolicitudClick(solicitud.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SolicitudCard(
    solicitud: SolicitudPrestamo,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Solicitud #${solicitud.id}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Equipo ID: ${solicitud.equipoId}",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Destino: ${solicitud.ambienteDestino}"
            )

            Text(
                text = "Duración: ${solicitud.duracionHoras} hora(s)"
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            EstadoSolicitudChip(
                estado = solicitud.estado
            )

            Text(
                text = "Toca para ver el detalle",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
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
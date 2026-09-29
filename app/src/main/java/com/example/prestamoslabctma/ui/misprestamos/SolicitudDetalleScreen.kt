package com.example.prestamoslabctma.ui.misprestamos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        if (solicitud == null) {

            Text(
                text = "Solicitud no encontrada",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

        } else {

            Text(
                text = "Detalle de solicitud",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "ID: ${solicitud.id}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Equipo ID: ${solicitud.equipoId}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Ambiente destino: ${solicitud.ambienteDestino}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Propósito: ${solicitud.proposito}"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Duración: ${solicitud.duracionHoras} horas"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Estado: ${solicitud.estado}"
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            if (solicitud.estado == EstadoSolicitud.SOLICITADA) {

                Button(
                    onClick = {
                        viewModel.cancelarSolicitud(solicitud.id)
                        onSolicitudCancelada()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar solicitud")
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            if (
                solicitud.estado == EstadoSolicitud.APROBADA ||
                solicitud.estado == EstadoSolicitud.ENTREGADA
            ) {

                Button(
                    onClick = {
                        viewModel.devolverPrestamo(solicitud.id)
                        onVolver()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar devolución")
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }

            OutlinedButton(
                onClick = onVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}
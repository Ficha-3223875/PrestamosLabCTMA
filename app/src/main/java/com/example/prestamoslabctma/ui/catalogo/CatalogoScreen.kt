package com.example.prestamoslabctma.ui.catalogo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun CatalogoScreen(
    viewModel: PrestamoViewModel,
    onEquipoClick: (Int) -> Unit,
    onMisSolicitudesClick: () -> Unit = {}
) {
    val equipos = viewModel.uiState.value.equipos

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

        // Encabezado
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
            text = "Catálogo de equipos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Consulta los equipos disponibles para solicitar un préstamo.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Botón de mis solicitudes
        Button(
            onClick = onMisSolicitudesClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Mis solicitudes",
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Equipos disponibles",
            style = MaterialTheme.typography.titleLarge,
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
                items = equipos,
                key = { it.id }
            ) { equipo ->

                EquipoCard(
                    equipo = equipo,
                    onClick = {
                        onEquipoClick(equipo.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun EquipoCard(
    equipo: Equipo,
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
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = equipo.nombre,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Categoría: ${textoCategoria(equipo)}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Estado:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                EstadoChip(
                    estado = equipo.estado
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
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
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            color = contentColor,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun textoCategoria(
    equipo: Equipo
): String {
    return when (equipo.categoria.name) {
        "COMPUTO" -> "Cómputo"
        "AUDIOVISUAL" -> "Audiovisual"
        "HERRAMIENTA" -> "Herramienta"
        "LABORATORIO" -> "Laboratorio"
        else -> "Otro"
    }
}
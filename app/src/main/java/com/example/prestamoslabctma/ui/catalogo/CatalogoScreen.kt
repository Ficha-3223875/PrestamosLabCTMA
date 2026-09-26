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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.prestamoslabctma.model.CategoriaEquipo
import com.example.prestamoslabctma.model.Equipo
import com.example.prestamoslabctma.model.EstadoEquipo
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel

@Composable
fun CatalogoScreen(
    viewModel: PrestamoViewModel,
    onEquipoClick: (Int) -> Unit,
    onMisSolicitudesClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val equipos = uiState.equipos

    var textoBusqueda by remember {
        mutableStateOf("")
    }

    var categoriaSeleccionada by remember {
        mutableStateOf<CategoriaEquipo?>(null)
    }

    var menuCategoriaAbierto by remember {
        mutableStateOf(false)
    }

    val equiposFiltrados = equipos.filter { equipo ->

        val coincideBusqueda =
            equipo.nombre.contains(
                textoBusqueda.trim(),
                ignoreCase = true
            )

        val coincideCategoria =
            categoriaSeleccionada == null ||
                    equipo.categoria == categoriaSeleccionada

        coincideBusqueda && coincideCategoria
    }

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
            modifier = Modifier.height(16.dp)
        )

        // =========================
        // BÚSQUEDA - HU-11
        // =========================

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = {
                textoBusqueda = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Buscar equipo")
            },
            placeholder = {
                Text("Escribe el nombre del equipo")
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp)
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // =========================
        // FILTRO - HU-12
        // =========================

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = {
                    menuCategoriaAbierto = true
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = categoriaSeleccionada?.let {
                        "Categoría: ${textoCategoria(it)}"
                    } ?: "Filtrar por categoría"
                )
            }

            DropdownMenu(
                expanded = menuCategoriaAbierto,
                onDismissRequest = {
                    menuCategoriaAbierto = false
                }
            ) {

                DropdownMenuItem(
                    text = {
                        Text("Todas las categorías")
                    },
                    onClick = {
                        categoriaSeleccionada = null
                        menuCategoriaAbierto = false
                    }
                )

                CategoriaEquipo.entries.forEach { categoria ->

                    DropdownMenuItem(
                        text = {
                            Text(
                                textoCategoria(categoria)
                            )
                        },
                        onClick = {
                            categoriaSeleccionada = categoria
                            menuCategoriaAbierto = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = "Equipos",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        if (equiposFiltrados.isEmpty()) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "No se encontraron equipos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(6.dp)
                    )

                    Text(
                        text = "Prueba con otro nombre o categoría.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                items(
                    items = equiposFiltrados,
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
                text = "Categoría: ${textoCategoria(equipo.categoria)}",
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
    categoria: CategoriaEquipo
): String {
    return when (categoria) {
        CategoriaEquipo.COMPUTO -> "Cómputo"
        CategoriaEquipo.AUDIOVISUAL -> "Audiovisual"
        CategoriaEquipo.HERRAMIENTA -> "Herramienta"
        CategoriaEquipo.LABORATORIO -> "Laboratorio"
        CategoriaEquipo.OTRO -> "Otro"
    }
}
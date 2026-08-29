package com.prestamolab.ctma.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prestamolab.ctma.model.Equipment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    equipment: Equipment?,
    message: String?,
    onBack: () -> Unit,
    onLoan: () -> Unit,
    onDismissMessage: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del equipo") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Volver") } }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (equipment == null) {
                Text("No se encontró el equipo.")
                return@Column
            }

            Text(equipment.name, style = MaterialTheme.typography.headlineMedium)
            Text(equipment.description)
            Text(if (equipment.available) "Estado: Disponible" else "Estado: No disponible")

            Button(
                onClick = onLoan,
                enabled = equipment.available,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (equipment.available) "Solicitar préstamo" else "No disponible")
            }

            if (message != null) {
                Text(message)
                TextButton(onClick = onDismissMessage) { Text("Cerrar mensaje") }
            }
        }
    }
}

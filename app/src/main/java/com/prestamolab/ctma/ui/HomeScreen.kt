package com.prestamolab.ctma.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prestamolab.ctma.model.Equipment

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    equipment: List<Equipment>,
    onSelect: (Equipment) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("PréstamoLab CTMA") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    "Equipos y herramientas de formación",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(8.dp))
                Text("Consulta la disponibilidad y solicita un préstamo.")
            }

            items(equipment, key = { it.id }) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onSelect(item) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(item.name, style = MaterialTheme.typography.titleMedium)
                        Text(item.description)
                        Spacer(Modifier.height(6.dp))
                        Text(if (item.available) "Disponible" else "No disponible")
                    }
                }
            }
        }
    }
}

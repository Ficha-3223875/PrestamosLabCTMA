package com.example.prestamoslabctma

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CrearReporteContent(
    uiState: CrearUiState,
    onTituloChange: (String) -> Unit,
    onGuardar: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Text(text = "Crear reporte")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.titulo,
            onValueChange = onTituloChange,
            label = {
                Text("Título")
            },
            isError = uiState.errorTitulo != null,
            supportingText = {
                uiState.errorTitulo?.let {
                    Text(it)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGuardar,
            enabled = !uiState.guardando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (uiState.guardando) {
                    "Guardando..."
                } else {
                    "Guardar"
                }
            )
        }

        uiState.guardadoId?.let {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Reporte guardado correctamente. ID: $it"
            )
        }
    }
}
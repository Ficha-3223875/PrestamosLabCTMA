package com.example.prestamoslabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

class MainActivity : ComponentActivity() {

    private val repository = MemoriaReporteRepository()

    private val crearReporteViewModel: CrearReporteViewModel by viewModels {
        CrearReporteViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    CrearReporteRoute(
                        viewModel = crearReporteViewModel
                    )
                }
            }
        }
    }
}
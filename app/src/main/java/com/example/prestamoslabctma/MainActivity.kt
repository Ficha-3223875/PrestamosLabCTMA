package com.example.prestamoslabctma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prestamoslabctma.ui.PrestamoLabApp
import com.example.prestamoslabctma.ui.theme.PrestamosLabCtmaTheme
import com.example.prestamoslabctma.viewmodel.PrestamoViewModel
import com.example.prestamoslabctma.viewmodel.PrestamoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as PrestamoLabApplication
        setContent {
            PrestamosLabCtmaTheme {
                val vm: PrestamoViewModel = viewModel(factory = PrestamoViewModelFactory(application.repository, application.preferencias))
                PrestamoLabApp(vm)
            }
        }
    }
}

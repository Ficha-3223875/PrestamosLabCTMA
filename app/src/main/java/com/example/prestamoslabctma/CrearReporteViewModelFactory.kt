package com.example.prestamoslabctma

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CrearReporteViewModelFactory(
    private val repository: ReporteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(CrearReporteViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return CrearReporteViewModel(repository) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}
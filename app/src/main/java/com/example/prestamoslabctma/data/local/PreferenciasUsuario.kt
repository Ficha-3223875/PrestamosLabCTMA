package com.example.prestamoslabctma.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("preferencias_usuario")

data class Preferencias(val filtroCategoria: String = "TODAS", val recordatorios: Boolean = true)

class PreferenciasUsuario(private val context: Context) {
    private val filtro = stringPreferencesKey("filtro_categoria")
    private val recordatorios = booleanPreferencesKey("recordatorios")
    val flujo: Flow<Preferencias> = context.dataStore.data.map {
        Preferencias(it[filtro] ?: "TODAS", it[recordatorios] ?: true)
    }
    suspend fun guardarFiltro(valor: String) { context.dataStore.edit { it[filtro] = valor } }
    suspend fun guardarRecordatorios(valor: Boolean) { context.dataStore.edit { it[recordatorios] = valor } }
}

package com.example.prestamoslabctma.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.prestamoslabctma.security.SecurityManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "preferencias_prestamolab"
)

class UserPreferencesDataStore(
    private val context: Context
) {

    companion object {

        private val NOMBRE_USUARIO =
            stringPreferencesKey("nombre_usuario")

        private val AMBIENTE_DESTINO =
            stringPreferencesKey("ambiente_destino")

        private val USUARIO_ID =
            intPreferencesKey("usuario_id")

        private val SESION_ACTIVA =
            booleanPreferencesKey("sesion_activa")
    }

    private val securityManager =
        SecurityManager()

    val nombreUsuario: Flow<String> =
        context.dataStore.data.map { preferencias ->
            val valor =
                preferencias[NOMBRE_USUARIO] ?: ""

            if (valor.isBlank()) {
                ""
            } else {
                val descifrado =
                    securityManager.descifrar(valor)

                if (descifrado.isNotEmpty()) {
                    descifrado
                } else {
                    // Permite leer datos antiguos
                    // que estaban guardados sin cifrar.
                    valor
                }
            }
        }

    val ambienteDestino: Flow<String> =
        context.dataStore.data.map { preferencias ->
            val valor =
                preferencias[AMBIENTE_DESTINO] ?: ""

            if (valor.isBlank()) {
                ""
            } else {
                val descifrado =
                    securityManager.descifrar(valor)

                if (descifrado.isNotEmpty()) {
                    descifrado
                } else {
                    // Compatibilidad con datos anteriores.
                    valor
                }
            }
        }

    val usuarioId: Flow<Int> =
        context.dataStore.data.map { preferencias ->
            preferencias[USUARIO_ID] ?: 0
        }

    val sesionActiva: Flow<Boolean> =
        context.dataStore.data.map { preferencias ->
            preferencias[SESION_ACTIVA] ?: false
        }

    suspend fun guardarNombreUsuario(
        nombre: String
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[NOMBRE_USUARIO] =
                securityManager.cifrar(nombre)
        }
    }

    suspend fun guardarAmbienteDestino(
        ambiente: String
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[AMBIENTE_DESTINO] =
                securityManager.cifrar(ambiente)
        }
    }

    suspend fun guardarUsuarioId(
        id: Int
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[USUARIO_ID] = id
        }
    }

    suspend fun guardarSesionActiva(
        activa: Boolean
    ) {
        context.dataStore.edit { preferencias ->
            preferencias[SESION_ACTIVA] = activa
        }
    }

    suspend fun guardarPreferencias(
        nombre: String,
        ambiente: String,
        usuarioId: Int,
        sesionActiva: Boolean
    ) {
        context.dataStore.edit { preferencias ->

            preferencias[NOMBRE_USUARIO] =
                securityManager.cifrar(nombre)

            preferencias[AMBIENTE_DESTINO] =
                securityManager.cifrar(ambiente)

            preferencias[USUARIO_ID] =
                usuarioId

            preferencias[SESION_ACTIVA] =
                sesionActiva
        }
    }

    suspend fun limpiarPreferencias() {
        context.dataStore.edit { preferencias ->
            preferencias.clear()
        }
    }
}
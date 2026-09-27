package com.example.prestamolab_ctma.data.local
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
private val Context.dataStore by preferencesDataStore("prestamolab_preferences")
class DataStoreManager(private val context:Context){
 private val filtro=stringPreferencesKey("filtro_categoria")
 val filtroCategoria=context.dataStore.data.map{it[filtro]?:"TODAS"}
 suspend fun guardarFiltro(value:String){context.dataStore.edit{it[filtro]=value}}
}

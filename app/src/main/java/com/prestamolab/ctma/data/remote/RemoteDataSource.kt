package com.prestamolab.ctma.data.remote

import com.prestamolab.ctma.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface RemoteDataSource {
    suspend fun fetchEquipment(): List<EquipmentDto>
}

class FakeRemoteDataSource : RemoteDataSource {
    override suspend fun fetchEquipment(): List<EquipmentDto> = listOf(
        EquipmentDto(1, "Multímetro digital", "Medición", "Equipo para mediciones eléctricas.", "DISPONIBLE"),
        EquipmentDto(2, "Taladro", "Herramienta eléctrica", "Herramienta eléctrica de formación.", "DISPONIBLE"),
        EquipmentDto(3, "Juego de destornilladores", "Herramienta manual", "Kit de herramientas manuales.", "DISPONIBLE"),
        EquipmentDto(4, "Kit Arduino", "Electrónica", "Kit educativo para prácticas de prototipado.", "DISPONIBLE"),
        EquipmentDto(5, "Tablet de laboratorio", "Dispositivo", "Tablet para apoyo a prácticas y consulta técnica.", "DISPONIBLE")
    )
}

class RetrofitRemoteDataSource(private val api: PrestamoApi) : RemoteDataSource {
    override suspend fun fetchEquipment(): List<EquipmentDto> = api.getEquipment()

    companion object {
        fun create(): RetrofitRemoteDataSource {
            val client = OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return RetrofitRemoteDataSource(retrofit.create(PrestamoApi::class.java))
        }
    }
}

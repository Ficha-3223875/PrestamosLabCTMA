package com.example.prestamoslabctma

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.prestamoslabctma.data.local.PrestamoDatabase
import com.example.prestamoslabctma.data.local.PreferenciasUsuario
import com.example.prestamoslabctma.data.remote.PrestamoApi
import com.example.prestamoslabctma.repository.RoomPrestamoRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class PrestamoLabApplication : Application() {
    val preferencias by lazy { PreferenciasUsuario(this) }
    val repository by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
            redactHeader("Authorization")
        }
        val cliente = OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        val api = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(cliente)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PrestamoApi::class.java)
        RoomPrestamoRepository(PrestamoDatabase.obtener(this), api)
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager::class.java).createNotificationChannel(
                NotificationChannel("devoluciones", getString(R.string.canal_devoluciones), NotificationManager.IMPORTANCE_DEFAULT)
            )
        }
    }
}

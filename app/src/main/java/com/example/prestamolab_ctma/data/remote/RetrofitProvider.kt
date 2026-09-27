package com.example.prestamolab_ctma.data.remote
import com.example.prestamolab_ctma.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitProvider{
 private val logging=HttpLoggingInterceptor().apply{level=HttpLoggingInterceptor.Level.BASIC}
 private val client=OkHttpClient.Builder().addInterceptor(logging).build()
 val api:PrestamoApi=Retrofit.Builder().baseUrl(if(BuildConfig.API_BASE_URL.endsWith('/'))BuildConfig.API_BASE_URL else "${BuildConfig.API_BASE_URL}/").client(client).addConverterFactory(GsonConverterFactory.create()).build().create(PrestamoApi::class.java)
}

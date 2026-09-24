package com.example.prestamoslabctma

import com.example.prestamoslabctma.data.remote.PrestamoApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PrestamoApiTest {
    @Test fun tc25_mapeaCatalogoHttp200() = runTest {
        val server = MockWebServer()
        server.enqueue(MockResponse().setResponseCode(200).setBody("[{\"id\":1,\"nombre\":\"Multímetro\",\"categoria\":\"MEDICION\",\"estado\":\"DISPONIBLE\"}]").addHeader("Content-Type", "application/json"))
        server.start()
        try {
            val api = Retrofit.Builder().baseUrl(server.url("/")).addConverterFactory(GsonConverterFactory.create()).build().create(PrestamoApi::class.java)
            assertEquals("Multímetro", api.equipos().single().nombre)
            assertEquals("/equipos", server.takeRequest().path)
        } finally { server.shutdown() }
    }

    @Test fun tc26_propagaErrorHttp500() = runTest {
        val server = MockWebServer(); server.enqueue(MockResponse().setResponseCode(500)); server.start()
        try {
            val api = Retrofit.Builder().baseUrl(server.url("/")).addConverterFactory(GsonConverterFactory.create()).build().create(PrestamoApi::class.java)
            val resultado = runCatching { api.equipos() }
            assertEquals(true, resultado.isFailure)
        } finally { server.shutdown() }
    }
}

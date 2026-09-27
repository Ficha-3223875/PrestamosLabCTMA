package com.example.prestamolab_ctma
import com.example.prestamolab_ctma.data.remote.PrestamoApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class ApiContractTest{private lateinit var server:MockWebServer;private lateinit var api:PrestamoApi
 @Before fun setup(){server=MockWebServer();server.start();api=Retrofit.Builder().baseUrl(server.url("/" )).addConverterFactory(GsonConverterFactory.create()).build().create(PrestamoApi::class.java)}
 @After fun close(){server.shutdown()}
 @Test fun getEquipos()=runTest{server.enqueue(MockResponse().setResponseCode(200).setBody("[{\"id\":1,\"nombre\":\"Multímetro\",\"categoria\":\"ELECTRONICA\",\"estado\":\"DISPONIBLE\",\"descripcion\":\"Prueba\"}]"));org.junit.Assert.assertEquals(1,api.obtenerEquipos().size)}
}

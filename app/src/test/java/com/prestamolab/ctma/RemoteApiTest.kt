package com.prestamolab.ctma

import com.prestamolab.ctma.data.remote.PrestamoApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteApiTest {
    private lateinit var server: MockWebServer
    private lateinit var api: PrestamoApi
    @Before fun setup() { server=MockWebServer(); server.start(); api=Retrofit.Builder().baseUrl(server.url("/")).addConverterFactory(GsonConverterFactory.create()).build().create(PrestamoApi::class.java) }
    @After fun tearDown(){ server.shutdown() }
    @Test fun equipment_endpoint_is_parsed()= runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody("""[{"id":7,"name":"Cámara","category":"Audiovisual","description":"Demo","state":"DISPONIBLE"}]""").addHeader("Content-Type","application/json"))
        val items=api.getEquipment(); assertEquals(1,items.size); assertEquals("Cámara",items.first().name); assertEquals("/equipment",server.takeRequest().path)
    }
}

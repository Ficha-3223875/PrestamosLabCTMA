package com.prestamolab.ctma.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class EquipmentDto(val id: Int, val name: String, val category: String, val description: String, val state: String)
data class LoanDto(val equipmentId: Int, val destination: String, val purpose: String, val durationHours: Int)
data class LoanResponseDto(val id: Int, val status: String)

interface PrestamoApi {
    @GET("equipment")
    suspend fun getEquipment(): List<EquipmentDto>

    @POST("loans")
    suspend fun createLoan(@Body body: LoanDto): LoanResponseDto
}

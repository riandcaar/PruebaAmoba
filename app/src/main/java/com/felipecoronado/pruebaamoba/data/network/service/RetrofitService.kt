package com.felipecoronado.pruebaamoba.data.network.service

import com.felipecoronado.pruebaamoba.data.network.models.TransactionRequest
import com.felipecoronado.pruebaamoba.data.network.models.TransactionResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitService {
    @POST("sale")
    suspend fun chargePatient(
        @Header("Authorization") bearerToken: String,
        @Body transactionRequest: TransactionRequest
    ): Response<TransactionResponse>

    @GET("sale")
    fun checkChargeStatus(
        @Header("Authorization") bearerToken: String,
        @Body transactionId: Int
    ): Flow<Response<TransactionResponse>>


}

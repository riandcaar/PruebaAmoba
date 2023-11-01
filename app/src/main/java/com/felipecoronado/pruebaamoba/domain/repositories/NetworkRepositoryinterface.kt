package com.felipecoronado.pruebaamoba.domain.repositories

import com.felipecoronado.pruebaamoba.ui.models.Patient
import kotlinx.coroutines.flow.Flow

interface NetworkRepositoryInterface {

    suspend fun signInUser(email: String, password: String): Result<String>

    suspend fun fetchUserData(userUid: String): Result<List<Patient>>

    suspend fun chargePatient(): Result<String>

    fun checkChargeStatus(transactionId: Int): Flow<Result<String>>

}

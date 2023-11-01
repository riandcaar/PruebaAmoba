package com.felipecoronado.pruebaamoba.data.network.repository

import com.felipecoronado.pruebaamoba.data.network.di.RetrofitModule.BEARER_TOKEN
import com.felipecoronado.pruebaamoba.data.network.models.TransactionRequest
import com.felipecoronado.pruebaamoba.data.network.service.RetrofitService
import com.felipecoronado.pruebaamoba.domain.mappers.documentToPatient
import com.felipecoronado.pruebaamoba.domain.repositories.NetworkRepositoryInterface
import com.felipecoronado.pruebaamoba.ui.models.Patient
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_1
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.PENDING_CODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.time.delay
import java.time.Duration
import javax.inject.Inject


class NetworkRepository @Inject constructor(
    private val service: RetrofitService
) : NetworkRepositoryInterface {
    val auth = Firebase.auth
    val db = Firebase.firestore

    override suspend fun signInUser(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user?.let { user ->
                Result.success(user.uid)
            } ?: Result.failure(Exception(ERROR_CODE_1))

        } catch (e: Exception) {
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun fetchUserData(userUid: String): Result<List<Patient>> {
        val collectionRef = db.collection(userUid)
        return try {
            val snapshot = collectionRef.get().await()
            val patients = snapshot.documents.mapNotNull { document ->
                documentToPatient(document)
            }
            Result.success(patients)
        } catch (e: Exception) {
            Result.failure(Exception(e.message))
        }
    }

    override suspend fun chargePatient(): Result<String> {
        var result = ""
        try {
            val transactionRequest = TransactionRequest(
                phoneNumber = "0939277927",
                countryCode = "593",
                amount = 100,
                amountWithoutTax = 100,
                clientTransactionId = "idunicoapi01"
            )

            val response = service.chargePatient(BEARER_TOKEN, transactionRequest)

            if (response.isSuccessful) {
                val transactionResponse = response.body()
                if (transactionResponse != null) {
                    transactionResponse.transactionId?.let { transactionId ->
                        result = transactionId.toString()
                    } ?: transactionResponse.message?.let { transactionMessage ->
                        result = transactionMessage
                    }
                }
            } else {
                return Result.failure(Exception(PENDING_CODE))
            }
        } catch (e: Exception) {
            return Result.failure(Exception(e.message))
        }
        return Result.success(result)
    }


    override fun checkChargeStatus(transactionId: Int): Flow<Result<String>> = flow {
       // val response = service.checkChargeStatus(BEARER_TOKEN, transactionId)
        delay(Duration.ofSeconds(10))
        emit(Result.success(""))
    }
}

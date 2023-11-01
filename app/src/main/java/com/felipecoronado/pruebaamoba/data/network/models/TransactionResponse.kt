package com.felipecoronado.pruebaamoba.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(
   val transactionId: Int?,
   val message: String?,
   val errorCode: String?
)

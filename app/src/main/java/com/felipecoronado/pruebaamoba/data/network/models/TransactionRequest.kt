package com.felipecoronado.pruebaamoba.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class TransactionRequest(
    val amount: Int,
    val amountWithoutTax: Int,
    val clientTransactionId: String,
    val countryCode: String,
    val phoneNumber: String
)

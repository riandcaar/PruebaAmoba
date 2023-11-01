package com.felipecoronado.pruebaamoba.ui.states

import com.felipecoronado.pruebaamoba.ui.models.Patient

data class LoginState(
    val signedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isConnected: Boolean = true,
    val transactionInProgress: Boolean = false,
    val transactionCompleted: Boolean = false,
    val userPatients: List<Patient> = listOf(),
    val transactionId: String? = "000",
    val errorMessage: String? = null,
)

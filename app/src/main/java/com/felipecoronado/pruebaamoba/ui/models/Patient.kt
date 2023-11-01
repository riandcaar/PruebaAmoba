package com.felipecoronado.pruebaamoba.ui.models

data class Patient(
    val name: String,
    val activeStatus: Boolean,
    val patientId: String,
    val email: String,
    val ageYears: Int,
    val ageMonths: Int,
    val sex: String,
    val address: String,
    val cellphone: String
)

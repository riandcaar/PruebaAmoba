package com.felipecoronado.pruebaamoba.domain.repositories

import com.felipecoronado.pruebaamoba.ui.models.Patient

interface LocalRepositoryInterface {
    suspend fun savePatientsList(patientsList: List<Patient>)
    suspend fun fetchPatientList(): List<Patient>
}

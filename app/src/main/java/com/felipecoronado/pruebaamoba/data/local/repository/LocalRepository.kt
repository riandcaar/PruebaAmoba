package com.felipecoronado.pruebaamoba.data.local.repository

import com.felipecoronado.pruebaamoba.data.local.dao.PatientsDao
import com.felipecoronado.pruebaamoba.domain.mappers.ToPatientEntity
import com.felipecoronado.pruebaamoba.domain.mappers.toPatient
import com.felipecoronado.pruebaamoba.domain.repositories.LocalRepositoryInterface
import com.felipecoronado.pruebaamoba.ui.models.Patient
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private val patientsDao: PatientsDao
) : LocalRepositoryInterface {

    override suspend fun savePatientsList(patientsList: List<Patient>) {
        if (patientsList.isNotEmpty()) {
            patientsDao.clearAllPatients()
            for (patient in patientsList) {
                val patientEntity = patient.ToPatientEntity()
                patientsDao.insertPatient(patientEntity)
            }
        }
    }

    override suspend fun fetchPatientList(): List<Patient> {
        val patients = mutableListOf<Patient>()
        val patientsEntity = patientsDao.getAllPatients()
        for (patientEntity in patientsEntity) {
            val patient = patientEntity.toPatient()
            patients.add(patient)
        }
        return patients
    }
}

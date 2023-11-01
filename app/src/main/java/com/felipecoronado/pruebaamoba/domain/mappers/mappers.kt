package com.felipecoronado.pruebaamoba.domain.mappers

import com.felipecoronado.pruebaamoba.data.local.entities.PatientEntity
import com.felipecoronado.pruebaamoba.ui.models.Patient
import com.google.firebase.firestore.DocumentSnapshot

fun documentToPatient(document: DocumentSnapshot): Patient {
    return Patient(
        name = document.getString("name") ?: "",
        activeStatus = document.getBoolean("activeStatus") ?: false,
        patientId = document.getString("patientId") ?: "",
        email = document.getString("email") ?: "",
        ageYears = document.getLong("ageYears")?.toInt() ?: 0,
        ageMonths = document.getLong("ageMonths")?.toInt() ?: 0,
        sex = document.getString("sex") ?: "",
        address = document.getString("address") ?: "",
        cellphone = document.getString("cellphone") ?: ""
    )
}

fun Patient.ToPatientEntity(): PatientEntity {
    return PatientEntity(
        name = this.name,
        activeStatus = this.activeStatus,
        patientId = this.patientId,
        email = this.email,
        ageYears = this.ageYears,
        ageMonths = this.ageMonths,
        sex = this.sex,
        address = this.address,
        cellphone = this.cellphone
    )
}

fun PatientEntity.toPatient(): Patient {
    return Patient(
        name = this.name,
        activeStatus = this.activeStatus,
        patientId = this.patientId,
        email = this.email,
        ageYears = this.ageYears,
        ageMonths = this.ageMonths,
        sex = this.sex,
        address = this.address,
        cellphone = this.cellphone
    )
}

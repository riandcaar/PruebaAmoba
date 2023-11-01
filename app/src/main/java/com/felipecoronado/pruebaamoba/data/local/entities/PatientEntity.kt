package com.felipecoronado.pruebaamoba.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_list")
data class PatientEntity(
    val name :String ,
    val activeStatus: Boolean ,
    val patientId :String,
    val email :String ,
    val ageYears :Int,
    val ageMonths:Int ,
    val sex :String,
    val address:String,
    val cellphone:String,
    @PrimaryKey
    val id: Long? = null
)

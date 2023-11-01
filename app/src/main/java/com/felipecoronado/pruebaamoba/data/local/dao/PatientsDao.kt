package com.felipecoronado.pruebaamoba.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felipecoronado.pruebaamoba.data.local.entities.PatientEntity

@Dao
interface PatientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientEntity): Long

    @Query("SELECT * FROM patient_list WHERE id = :id")
    suspend fun getPatient(id: String): PatientEntity

    @Query("SELECT * FROM patient_list")
    suspend fun getAllPatients(): List<PatientEntity>

    @Query("DELETE FROM patient_list")
    suspend fun clearAllPatients()

}

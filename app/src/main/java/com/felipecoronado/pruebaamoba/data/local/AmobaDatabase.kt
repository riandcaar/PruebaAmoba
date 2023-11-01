package com.felipecoronado.pruebaamoba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.felipecoronado.pruebaamoba.data.local.dao.PatientsDao
import com.felipecoronado.pruebaamoba.data.local.entities.PatientEntity

@Database(entities = [PatientEntity::class], version = 1, exportSchema = false)

//@TypeConverters(DataConverter::class)
abstract class AmobaDatabase : RoomDatabase() {

    abstract fun patientsDao(): PatientsDao
}

package com.felipecoronado.pruebaamoba.data.local.di

import android.content.Context
import androidx.room.Room
import com.felipecoronado.pruebaamoba.data.local.AmobaDatabase
import com.felipecoronado.pruebaamoba.data.local.dao.PatientsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalStorageModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AmobaDatabase::class.java,
        "database"
    ).build()

    @Singleton
    @Provides
    fun provideRecognizedUsersDao(dataBase: AmobaDatabase): PatientsDao =
        dataBase.patientsDao()

}

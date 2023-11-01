package com.felipecoronado.pruebaamoba

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class AmobaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}

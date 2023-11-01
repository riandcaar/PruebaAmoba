package com.felipecoronado.pruebaamoba.ui.navigation

const val PATIENT_DETAIL_INDEX_KEY = "index"

sealed class Screens(val route: String)
object Login : Screens(route = "login_screen")
object PatientsList : Screens(route = "patients_list_screen")
object PatientDetail : Screens(route = "patient_detail_screen/{$PATIENT_DETAIL_INDEX_KEY}")
object PatientCharge : Screens(route = "patient_charge_screen/{$PATIENT_DETAIL_INDEX_KEY}")
object PatientLocation : Screens(route = "patients_location_screen/{$PATIENT_DETAIL_INDEX_KEY}")

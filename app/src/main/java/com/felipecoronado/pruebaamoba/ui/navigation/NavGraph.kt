package com.felipecoronado.pruebaamoba.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.felipecoronado.pruebaamoba.ui.screens.LoginScreen
import com.felipecoronado.pruebaamoba.ui.screens.PatientChargeScreen
import com.felipecoronado.pruebaamoba.ui.screens.PatientDetailScreen
import com.felipecoronado.pruebaamoba.ui.screens.PatientLocationScreen
import com.felipecoronado.pruebaamoba.ui.screens.PatientsListScreen
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel

@Composable
fun NavGraph(navController: NavHostController) {

    val viewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Login.route) {

        composable(route = Login.route) {
            LoginScreen(viewModel, navigateToNextScreen = {
                navController.navigate(PatientsList.route) {
                    launchSingleTop = true
                }
            })
        }
        composable(route = PatientsList.route) {
            PatientsListScreen(viewModel, navigateBack = {
                navController.navigate(Login.route)
            }, navigateToNextScreen = { index ->
                navController.navigate(
                    PatientDetail.route.replace(
                        "{$PATIENT_DETAIL_INDEX_KEY}",
                        index.toString()
                    )
                )
            })
        }
        composable(
            route = PatientDetail.route,
            arguments = listOf(navArgument(PATIENT_DETAIL_INDEX_KEY) {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt(PATIENT_DETAIL_INDEX_KEY)
            PatientDetailScreen(
                viewModel,
                patientIndex = index ?: -1,
                navigateBack = {
                    navController.navigate(PatientsList.route)
                },
                navigateToLocation = {
                    navController.navigate(
                        PatientLocation.route.replace(
                            "{$PATIENT_DETAIL_INDEX_KEY}",
                            index.toString()
                        )
                    )
                },
                navigateToNextScreen = {
                    navController.navigate(
                        PatientCharge.route.replace(
                            "{$PATIENT_DETAIL_INDEX_KEY}",
                            index.toString()
                        )
                    )
                }
            )
        }
        composable(
            route = PatientCharge.route,
            arguments = listOf(navArgument(PATIENT_DETAIL_INDEX_KEY) {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt(PATIENT_DETAIL_INDEX_KEY)
            PatientChargeScreen(
                viewModel,
                patientIndex = index ?: -1,
                navigateBack = {
                    navController.navigate(PatientsList.route)
                }
            )
        }
        composable(
            route = PatientLocation.route,
            arguments = listOf(navArgument(PATIENT_DETAIL_INDEX_KEY) {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt(PATIENT_DETAIL_INDEX_KEY)
            PatientLocationScreen(
                viewModel,
                patientIndex = index ?: -1,
                navigateToNextScreen = {
                    navController.navigate(
                        PatientCharge.route.replace(
                            "{$PATIENT_DETAIL_INDEX_KEY}",
                            index.toString()
                        )
                    )
                }
            )
        }

    }
}

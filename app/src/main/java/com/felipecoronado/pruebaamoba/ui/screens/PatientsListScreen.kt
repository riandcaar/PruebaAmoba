package com.felipecoronado.pruebaamoba.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.components.AmobaDialog
import com.felipecoronado.pruebaamoba.ui.components.AmobaPatientsItem
import com.felipecoronado.pruebaamoba.ui.components.AmobaTitleText
import com.felipecoronado.pruebaamoba.ui.models.Patient
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel


@Composable
fun PatientsListScreen(
    viewModel: MainViewModel,
    navigateBack: () -> Unit,
    navigateToNextScreen: (patientId: Int) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (!state.isConnected) {
        Toast.makeText(
            context,
            stringResource(R.string.no_internet_outdated_patient_list_warning_text),
            Toast.LENGTH_SHORT
        ).show()
    }

    BackHandler { showDialog = true }

    AmobaDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        dialogTitle = stringResource(R.string.close_session_text),
        onConfirm = {
            viewModel.signOutUser()
            showDialog = false
            navigateBack()
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(id = R.drawable.ic_amoba_logo),
            contentDescription = null,
            modifier = Modifier.padding(top =24.dp , bottom = 48.dp)
        )

        AmobaTitleText(
            text = stringResource(R.string.patient_list_screen_title_text),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        if (state.userPatients.isNotEmpty()) {
            val patientsList = state.userPatients
            val repeatedPatientsList = mutableListOf<Patient>()
            repeat(2) {
                repeatedPatientsList.addAll(patientsList)
            }
            LazyColumn {
                itemsIndexed(repeatedPatientsList) { index, patient ->
                    AmobaPatientsItem(
                        patientName = patient.name,
                        patientStatus = patient.activeStatus,
                        icon = R.drawable.ic_amoba_dots
                    ) { navigateToNextScreen(index % patientsList.size) }
                }
            }
        } else {
            Toast.makeText(
                context,
                stringResource(R.string.no_patient_list_warning_text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

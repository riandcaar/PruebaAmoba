package com.felipecoronado.pruebaamoba.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.components.AmobaButton
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun PatientLocationScreen(
    viewModel: MainViewModel,
    patientIndex:Int,
    navigateToNextScreen: (patientId: Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val patient = state.userPatients[patientIndex]
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.ic_amoba_logo),
            contentDescription = null,
            modifier = Modifier.padding(top = 24.dp, bottom = 28.dp)
        )

        val patientLocation = LatLng(-2.897837, -79.004349)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(patientLocation, 15f)
        }

        GoogleMap(
            modifier = Modifier.height(550.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = patientLocation),
                title = patient.address,
                snippet = stringResource(R.string.patient_location_prefix, patient.name)

            )
        }
        Spacer(Modifier.weight(1f))

        AmobaButton(
            text = stringResource(R.string.buy_ticket_button_text),
            modifier = Modifier.padding(bottom = 65.dp)
        ) {
            viewModel.newTransaction()
            navigateToNextScreen(patientIndex)
        }
    }
}

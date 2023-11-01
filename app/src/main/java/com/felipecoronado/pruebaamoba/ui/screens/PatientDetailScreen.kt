package com.felipecoronado.pruebaamoba.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.components.AmobaButton
import com.felipecoronado.pruebaamoba.ui.components.AmobaCommonTextSubtitle
import com.felipecoronado.pruebaamoba.ui.components.AmobaPatientsItem
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlue80
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel


@Composable
fun PatientDetailScreen(
    viewModel: MainViewModel,
    patientIndex: Int,
    navigateBack: () -> Unit,
    navigateToNextScreen: (patientId: Int) -> Unit,
    navigateToLocation: () -> Unit
) {
    val alignment = Alignment.CenterHorizontally
    val state by viewModel.state.collectAsStateWithLifecycle()
    val patient = state.userPatients[patientIndex]


    BackHandler { navigateBack() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painterResource(id = R.drawable.ic_amoba_logo),
            contentDescription = null,
            modifier = Modifier.padding(top = 10.dp, bottom = 32.dp)
        )
        Divider(modifier = Modifier.width(300.dp))
        Row() {
            AmobaPatientsItem(
                patientName = patient.name,
                patientStatus = patient.activeStatus,
                icon = R.drawable.ic_amoba_cancel
            ) {
                navigateBack()
            }
        }
        Text(stringResource(R.string.patient_id_prefix) + patient.patientId, fontSize = 18.sp)
        Text(patient.email, fontSize = 18.sp)

        Spacer(modifier = Modifier.padding(top = 6.dp))
        Divider(modifier = Modifier.width(200.dp))

        Spacer(modifier = Modifier.padding(top = 6.dp))
        Row() {
            Column(horizontalAlignment = alignment) {
                Text(patient.ageYears.toString() + stringResource(R.string.patient_year_sufix))
                Text(patient.ageMonths.toString() + stringResource(R.string.patient_month_sufix))
                AmobaCommonTextSubtitle(text = stringResource(R.string.age_subtitle))
            }
            Spacer(modifier = Modifier.padding(start = 48.dp))
            Column(horizontalAlignment = alignment) {
                Text(patient.sex)
                AmobaCommonTextSubtitle(text = stringResource(R.string.sex_subtitle))
            }
        }

        Divider(modifier = Modifier.width(150.dp))
        Spacer(modifier = Modifier.padding(top = 6.dp))
        Column(horizontalAlignment = alignment) {
            Text(patient.address)
            AmobaCommonTextSubtitle(text = stringResource(R.string.address_subtitle))
        }

        Divider(modifier = Modifier.width(150.dp))
        Spacer(modifier = Modifier.padding(top = 6.dp))
        Column(horizontalAlignment = alignment) {
            Text(
                stringResource(R.string.patient_map_location_link_text),
                color = AmobaBlue80,
                modifier = Modifier.clickable {
                    navigateToLocation()
                })

            AmobaCommonTextSubtitle(text = stringResource(R.string.go_to_map_subtitle))
        }

        Spacer(modifier = Modifier.padding(top = 6.dp))
        Column(horizontalAlignment = alignment) {
            Text("+" + patient.cellphone)
            AmobaCommonTextSubtitle(text = stringResource(R.string.mobile_subtitle))
        }


        AmobaButton(
            text = stringResource(R.string.buy_ticket_button_text),
            modifier = Modifier.padding(top = 12.dp, bottom = 52.dp)
        ) {
            viewModel.newTransaction()
            navigateToNextScreen(patientIndex)
        }
    }
}

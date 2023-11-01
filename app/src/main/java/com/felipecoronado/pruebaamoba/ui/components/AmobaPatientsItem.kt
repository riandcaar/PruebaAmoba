package com.felipecoronado.pruebaamoba.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlue60


@Composable
fun AmobaPatientsItem(patientName: String, patientStatus: Boolean, icon: Int, onCLick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.White)
            .width(300.dp)
            .height(90.dp)
            .fillMaxSize()
    ) {
        Image(
            painterResource(id = R.drawable.ic_amoba_image_placeholder),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(start = 16.dp))
        Column() {
            Text(patientName, fontSize = 18.sp, color = Color.Black)
            Text(
                if (patientStatus) stringResource(R.string.active_patient_status_text) else stringResource(
                    R.string.inactive_patient_status_text
                ), fontSize = 14.sp, color = AmobaBlue60
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.clickable { onCLick() }
        )
    }
}

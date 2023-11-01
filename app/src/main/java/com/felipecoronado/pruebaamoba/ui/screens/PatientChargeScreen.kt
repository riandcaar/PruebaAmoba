package com.felipecoronado.pruebaamoba.ui.screens

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.components.AmobaButton
import com.felipecoronado.pruebaamoba.ui.components.AmobaCountryCodeSelectableMock
import com.felipecoronado.pruebaamoba.ui.components.AmobaPatientsItem
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlack
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_3
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel

@Composable
fun PatientChargeScreen(
    viewModel: MainViewModel,
    patientIndex: Int,
    navigateBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val patient = state.userPatients[patientIndex]

    val context = LocalContext.current

    BackHandler {
        if (state.transactionInProgress) {
            Toast.makeText(
                context,
                "transacción en progreso, permanezca en la página",
                Toast.LENGTH_SHORT
            ).show()
        } else
            navigateBack()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(id = R.drawable.ic_amoba_logo),
            contentDescription = null,
            modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
        )
        Divider(modifier = Modifier.width(300.dp))
        Row() {
            AmobaPatientsItem(
                patientName = patient.name,
                patientStatus = patient.activeStatus,
                icon = R.drawable.ic_amoba_cancel
            ) {
                if (state.transactionInProgress) {
                    Toast.makeText(
                        context,
                        "transacción en progreso, permanezca en la página",
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    navigateBack()
            }
        }
        Text(stringResource(R.string.patient_id_prefix) + patient.patientId, fontSize = 18.sp)
        Spacer(modifier = Modifier.padding(top = 12.dp))
        Text(patient.email, fontSize = 18.sp)

        if (state.transactionInProgress || state.transactionCompleted) {
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Text(
                "2.00 USD",
                color = AmobaBlack,
                fontSize = 22.sp,
                fontWeight = FontWeight(800),
                fontFamily = FontFamily(
                    Font(R.font.bebas_neue)
                ),
                letterSpacing = 3.sp
            )
        }

        Spacer(modifier = Modifier.padding(top = 32.dp))
        if (!state.transactionInProgress && !state.transactionCompleted) {
            AmobaCountryCodeSelectableMock()
        }

        Spacer(modifier = Modifier.padding(top = 40.dp))
        Text(
            if (!state.transactionInProgress && !state.transactionCompleted) "2.00 USD"
            else if (state.transactionInProgress) stringResource(R.string.transaction_pending_text)
            else stringResource(R.string.transaction_complete_text), fontSize = 64.sp
        )

        Divider(modifier = Modifier.width(300.dp))
        Text(
            if (state.transactionInProgress || state.transactionCompleted) {
                stringResource(R.string.payment_status_subtitle_text)
            } else (stringResource(R.string.payment_amount_subtitle_text)), fontSize = 18.sp
        )

        AmobaButton(
            text = if (!state.transactionInProgress) stringResource(R.string.buy_ticket_button_text) else stringResource(
                R.string.check_transaction_status_button_text
            ),
            modifier = Modifier.padding(top = 72.dp)
        ) {
            if (!state.transactionInProgress && !state.transactionCompleted)
                viewModel.chargePatient()
            else if (state.transactionCompleted) {
                viewModel.newTransaction()
            } else
                viewModel.checkTransactionStatus()
        }

        if (!state.errorMessage.isNullOrEmpty()) {
            if (state.errorMessage == ERROR_CODE_3) {
                val errorMessage = stringResource(R.string.transaction_error)
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        if (state.transactionInProgress) {
            viewModel.checkTransactionStatus()
        }

    }
}

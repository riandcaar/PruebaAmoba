package com.felipecoronado.pruebaamoba.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.components.AmobaButton
import com.felipecoronado.pruebaamoba.ui.components.AmobaInputTextField
import com.felipecoronado.pruebaamoba.ui.components.AmobaTitleText
import com.felipecoronado.pruebaamoba.ui.components.ConnectivityCheck
import com.felipecoronado.pruebaamoba.ui.theme.AmobaGrey
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_1
import com.felipecoronado.pruebaamoba.ui.utils.ErrorCodes.ERROR_CODE_2
import com.felipecoronado.pruebaamoba.ui.viewmodels.MainViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(
    viewModel: MainViewModel,
    navigateToNextScreen: () -> Unit
) {

    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isConnected = remember { mutableStateOf(true) }

    val user = FirebaseAuth.getInstance().currentUser

    ConnectivityCheck(isConnected)
    viewModel.setConnectedState(isConnected.value)


    if (user != null) {
        viewModel.fetchDocument(user.uid)
        if (state.userPatients.isNotEmpty()) {
            navigateToNextScreen()
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.ic_amoba_logo),
                contentDescription = null,
                modifier = Modifier.padding(top= 24.dp, bottom = 48.dp)
            )

            Image(
                painterResource(id = R.drawable.ic_amoba_login_screen_image),
                contentDescription = null, modifier = Modifier.padding(bottom = 60.dp)

            )

            AmobaTitleText(
                text = stringResource(R.string.login_screen_title_text),
                modifier = Modifier.padding(bottom = 32.dp)

            )

            AmobaInputTextField(
                label = stringResource(R.string.login_screen_email_hint),
                icon = R.drawable.ic_amoba_user_text,
                isEmail = true,
                onValueChange = { email = it }
            )
            Spacer(modifier = Modifier.height(24.dp))

            AmobaInputTextField(
                label = stringResource(R.string.login_screen_password_hint),
                icon = R.drawable.ic_amoba_password_text,
                isPassword = true,
                onValueChange = { password = it }
            )

            AmobaButton(
                stringResource(R.string.login_button_text),
                modifier = Modifier.padding(top = 52.dp)
            ) {

                if (email.isNotBlank() && password.isNotBlank()) {
                    viewModel.signIn(email, password)
                } else {
                    Toast.makeText(
                        context,
                        R.string.no_password_or_no_user_error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            Text(
                text = stringResource(R.string.forgot_password_link_text),
                color = AmobaGrey,
                fontSize = 18.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(top = 24.dp)
            )

            if (state.signedIn) {
                navigateToNextScreen()
            }

            if (!state.errorMessage.isNullOrEmpty()) {
                val errorMessage = when (state.errorMessage) {
                    ERROR_CODE_1 -> stringResource(R.string.credentials_validation_error)
                    ERROR_CODE_2 -> stringResource(R.string.document_fetch_error)
                    else -> stringResource(R.string.generic_error_messsage)
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}


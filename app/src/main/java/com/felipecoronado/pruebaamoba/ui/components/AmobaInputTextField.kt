package com.felipecoronado.pruebaamoba.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlack
import com.felipecoronado.pruebaamoba.ui.theme.AmobaGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmobaInputTextField(
    label: String,
    icon: Int,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onValueChange(newText)
        },
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(size = 50.dp),
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = AmobaBlack
        ),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            placeholderColor = AmobaGrey,
            focusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .shadow(
                elevation = 3.dp,
                shape = RoundedCornerShape(size = 50.dp)
            )
            .width(300.dp)
            .height(60.dp),
        leadingIcon = {
            Image(
                painterResource(id = icon),
                contentDescription = null
            )
        },
        visualTransformation =
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions =
        if (isEmail) KeyboardOptions(keyboardType = KeyboardType.Email) else KeyboardOptions(),
        //   isError = if (isEmail) !android.util.Patterns.EMAIL_ADDRESS.matcher(text)
        //  .matches() else false
    )
}

package com.felipecoronado.pruebaamoba.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.felipecoronado.pruebaamoba.R
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlue20

@Composable
fun AmobaTitleText(text: String, modifier: Modifier) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = 36.sp,
            fontFamily = FontFamily(Font(R.font.bebas_neue)),
            color = AmobaBlue20,
            textAlign = TextAlign.Center,
            letterSpacing = 0.72.sp,
        ),
    )
}

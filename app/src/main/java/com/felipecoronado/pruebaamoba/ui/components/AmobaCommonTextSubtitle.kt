package com.felipecoronado.pruebaamoba.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.felipecoronado.pruebaamoba.ui.theme.AmobaBlue40

@Composable
fun AmobaCommonTextSubtitle(text: String) {
    Text(text, color = AmobaBlue40, fontSize = 14.sp)
}

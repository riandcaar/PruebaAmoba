package com.felipecoronado.pruebaamoba.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AmobaCountryCodeSelectableMock() {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val countryCodes = listOf("+1", "+91", "+86", "+44", "+61")
    var selectedCountryCode by remember { mutableStateOf(countryCodes[0]) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Phone number") },
        leadingIcon = {
            Box(Modifier.clickable { expanded = true }) {
                Text(selectedCountryCode)
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    countryCodes.forEach { countryCode ->
                        DropdownMenuItem(
                            {
                                Text(countryCode)
                            },
                            onClick = {
                            selectedCountryCode = countryCode
                            expanded = false
                        })
                    }
                }
            }
        }
    )
}

package com.felipecoronado.pruebaamoba.ui.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.felipecoronado.pruebaamoba.R

@Composable
fun AmobaDialog(
    showDialog: Boolean,
    dialogTitle: String,
    dialogContent: String? = null,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text(text = dialogTitle) },
            text = {
                if (dialogContent != null) {
                    Text(dialogContent)
                }
            },
            confirmButton = {
                Button(modifier = Modifier.width(80.dp), onClick = { onConfirm() }) {
                    Text(stringResource(R.string.yes_text))
                }
            },
            dismissButton = {
                Button(modifier = Modifier.width(80.dp), onClick = { onDismiss() }) {
                    Text(stringResource(R.string.no_text))
                }
            }
        )
    }
}

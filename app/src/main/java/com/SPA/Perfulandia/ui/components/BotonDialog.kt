package com.SPA.Perfulandia.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BotonDialog(modifier: Modifier = Modifier) {
    // Componente botón con un dialog
    val showDialog = remember { mutableStateOf(false) }

    Button(onClick = { showDialog.value = true }) {
        Text("Pincha Aquí")
    }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("¡Hola!") },
            text = { Text("Este es un diálogo en Jetpack Compose") },
            confirmButton = {
                Box(
                    modifier = modifier,
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Cerrar")
                    }
                }
            }
        )
    }
}
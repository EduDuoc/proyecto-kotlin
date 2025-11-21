package com.SPA.Perfulandia.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior(text: String, modifier: Modifier = Modifier) {
    // componente barra superior
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Home, // Ícono de Material Design
                    contentDescription = "Icono Home (casa)",
                    modifier = Modifier.padding(end = 8.dp).size(24.dp)
                )
                Text(text)
            }
        }
    )
}
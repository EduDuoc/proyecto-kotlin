package com.SPA.Perfulandia.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable //define una funcion composable que puede ser usada en la UI
fun BarraInferior(
    onAgregarClick: () -> Unit,
    onHomeClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    BottomAppBar(//Crea la barra inferior con estilo Material 3
        containerColor = MaterialTheme.colorScheme.primary,//color de fondo de la barra
        contentColor = MaterialTheme.colorScheme.onPrimary,//color de os iconos
        modifier = Modifier
            .fillMaxWidth()//indica que la barra ocupe todo el ancho disponible
            .height(70.dp)//altura de la barra
    ) {
        Row( // Organiza los 3 botones en una FILA HORIZONTAL (uno al lado del otro)
            modifier = Modifier
                .fillMaxWidth(), // Ocupa todo el ancho disponible
            horizontalArrangement = Arrangement.SpaceEvenly, // Espacios iguales entre botones
            verticalAlignment = Alignment.CenterVertically // Centra los botones verticalmente
        ) {
            IconButton(
                onClick = onHomeClick,
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = onAgregarClick,
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar producto",
                    modifier = Modifier.size(35.dp)
                )
            }

            IconButton(
                onClick = onSearchClick,
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar perfumes",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}


package com.SPA.Perfulandia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.SPA.Perfulandia.R
import com.SPA.Perfulandia.viewmodel.HomeViewModel

@Composable
fun DetailScreen(
    id: Int,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = viewModel()
) {
    val productos by vm.productos.collectAsState()
    val producto = productos.find { it.id == id }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Encabezado
        Text(
            text = "Detalles del Producto",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Contenido
        if (producto != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 16.dp)
                )
                Text(
                    text = producto.nombre,
                    fontSize = 22.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Text(
                    text = "Precio: ${producto.precio}€",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "ID: ${producto.id}",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        } else {
            Text(
                text = "Producto no encontrado",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        // Botón
        Button(
            onClick = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Volver a Home")
        }
    }
}

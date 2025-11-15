package com.SPA.Perfulandia.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.SPA.Perfulandia.R
import com.SPA.Perfulandia.ui.components.BarraSuperior
import com.SPA.Perfulandia.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onGoToDetail: (Int) -> Unit,
    onGoToProfile: () -> Unit,
    modifier: Modifier = Modifier,
    vm: HomeViewModel = viewModel()
) {
    val productos by vm.productos.collectAsState()
    val isLoading by vm.isLoading.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        // Barra superior
        BarraSuperior(
            text = "Perfulandia",
            modifier = Modifier.fillMaxWidth()
        )

        // Botón de perfil en la esquina
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = onGoToProfile) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Ir a Perfil",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Lista de productos
        if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Cargando productos...", modifier = Modifier.padding(top = 8.dp))
            }
        } else if (productos.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No hay productos disponibles")
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(productos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onGoToDetail(producto.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Imagen del producto
                            Image(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = producto.nombre,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )

                            // Información del producto
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = producto.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "${producto.precio}€",
                                    fontSize = 12.sp
                                )
                            }

                            // Botón de ver detalles
                            Button(
                                onClick = { onGoToDetail(producto.id) },
                                modifier = Modifier.size(width = 70.dp, height = 36.dp),
                                contentPadding = PaddingValues(2.dp)
                            ) {
                                Text("Ver", fontSize = 10.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}


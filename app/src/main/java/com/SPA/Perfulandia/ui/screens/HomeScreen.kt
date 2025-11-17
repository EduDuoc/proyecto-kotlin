package com.SPA.Perfulandia.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.SPA.Perfulandia.ui.components.BarraSuperior
import com.SPA.Perfulandia.ui.components.Logo
import com.SPA.Perfulandia.ui.components.ProductoCard
import com.SPA.Perfulandia.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    onNavigateAdd: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val productos by viewModel.productos.collectAsState()

    Scaffold(
        topBar = {
            BarraSuperior("Perfulandia SPA")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateAdd) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Logo
            Logo(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isLandscape) 120.dp else 180.dp)
            )

            // Lista de productos
            if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay perfumes registrados.\nPresiona + para agregar uno.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(productos) { producto ->
                        ProductoCard(
                            producto = producto,
                            onDelete = {
                                viewModel.eliminarProducto(producto)
                            },
                            onEdit = {
                                navController.navigate("edit_product/${producto.id}")
                            },
                            onInfo = {
                                navController.navigate("detail/${producto.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}


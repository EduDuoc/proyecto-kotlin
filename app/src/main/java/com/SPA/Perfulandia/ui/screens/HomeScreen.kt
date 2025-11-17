package com.SPA.Perfulandia.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.SPA.Perfulandia.NavRoutes
import com.SPA.Perfulandia.model.Producto
import com.SPA.Perfulandia.ui.components.BarraSuperior
import com.SPA.Perfulandia.ui.components.Logo
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
            BarraSuperior("Marketplace")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateAdd) {
                Icon(Icons.Default.Add, contentDescription = "Agregar producto")
            }
        }
    ) { innerPadding ->

        if (isLandscape) {
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Logo(Modifier.height(150.dp))

                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    if (productos.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No hay productos registrados.")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(productos) { producto ->
                                ProductoItem(
                                    producto = producto,
                                    onDelete = { viewModel.eliminarProducto(producto) }
                                )
                            }
                        }
                    }
                }

                Button(onClick = { navController.navigate(NavRoutes.DETAIL) }) {
                    Text("Ver Detalle")
                }

                Button(onClick = { navController.navigate(NavRoutes.FORM) }) {
                    Text("Ir al Formulario")
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Logo(Modifier.height(180.dp))

                val lista = viewModel.productos.collectAsState().value

                Column {
                    lista.forEach {
                        Text("Producto: ${it.nombre} - ${it.precio}")
                    }
                }

                // CameraPreviewCaptureDemo()

                Button(onClick = { navController.navigate(NavRoutes.DETAIL) }) {
                    Text("Ver Detalle")
                }

                Button(onClick = { navController.navigate(NavRoutes.FORM) }) {
                    Text("Ir al Formulario")
                }
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Precio: ${producto.precio}")
                if (producto.descripcion.isNotBlank()) {
                    Text(
                        producto.descripcion,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}
package com.SPA.Perfulandia.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.SPA.Perfulandia.ui.components.*
import com.SPA.Perfulandia.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    onNavigateAdd: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    // Observar la lista de productos desde el ViewModel
    val productos by viewModel.productos.collectAsState()

    // Estado para el texto de búsqueda
    var searchText by remember { mutableStateOf("") }

    // FILTRADO REACTIVO: Filtra los productos en tiempo real según el texto de búsqueda
    // Se ejecuta cada vez que cambian "productos" o "searchText"
    val productosFiltrados = productos.filter { producto ->
        // Búsqueda case-insensitive (ignora mayúsculas/minúsculas)
        producto.nombre.contains(searchText, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            BarraSuperior("Vista Administrador - Perfulandia SPA")
        },
        bottomBar = {
            BarraInferior(
                onAgregarClick = onNavigateAdd,
                onHomeClick = { /* Ya estamos en home, no hace nada */ },
                onSearchClick = { /* La búsqueda ya está en la pantalla */ }
            )
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
                    .height(if (isLandscape) 100.dp else 140.dp)
            )

            // Buscador
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                placeholder = { Text("Buscar perfumes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "icono buscar") },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))
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
            } else if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No se encontraron perfumes con \"$searchText\"",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productosFiltrados) { producto ->
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
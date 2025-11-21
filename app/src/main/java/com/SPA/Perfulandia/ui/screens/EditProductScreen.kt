package com.SPA.Perfulandia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.SPA.Perfulandia.model.Producto
import com.SPA.Perfulandia.ui.components.BarraInferior
import com.SPA.Perfulandia.ui.components.ImageCapture
import com.SPA.Perfulandia.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navController: NavController,
    homeViewModel: HomeViewModel,
    productoId: Int? = null
) {
    var productoOriginal by remember { mutableStateOf<Producto?>(null) }
    var nombre by rememberSaveable { mutableStateOf("") }
    var precioText by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var imagenUri by rememberSaveable { mutableStateOf<String?>(null) }

    var showError by rememberSaveable { mutableStateOf(false) }
    var mensajeError by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(productoId) {
        scope.launch {
            productoId?.let {
                val producto = homeViewModel.obtenerProductoPorId(it)
                producto?.let { p ->
                    productoOriginal = p
                    nombre = p.nombre
                    precioText = p.precio.toString()
                    descripcion = p.descripcion
                    imagenUri = p.imagen
                }
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(
                onAgregarClick = { navController.navigate("add_product") },
                onHomeClick = { navController.popBackStack() },
                onSearchClick = { /* La búsqueda está en home */ }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (productoOriginal != null) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {

                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre del Producto") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = nombre.isNotBlank() && nombre.length < 3
                    )
                    Text(
                        text = when {
                            nombre.isEmpty() -> "✓ Campo vacío (mínimo 3 caracteres)"
                            nombre.length < 3 -> "❌ Mínimo 3 caracteres (${nombre.length}/3)"
                            else -> "✓ Válido (${nombre.length} caracteres)"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            nombre.isEmpty() -> MaterialTheme.colorScheme.onSurface
                            nombre.length < 3 -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = precioText,
                        onValueChange = {
                            precioText = it
                            showError = false
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = showError
                    )
                    Text(
                        text = when {
                            precioText.isEmpty() -> "✓ Campo vacío (rango: 1000-999999)"
                            precioText.toIntOrNull() == null -> "❌ Solo números permitidos"
                            precioText.toInt() < 1000 -> "❌ Mínimo 1000 (ingresó ${precioText})"
                            precioText.toInt() > 999999 -> "❌ Máximo 999999 (ingresó ${precioText})"
                            else -> "✓ Válido (\$${precioText})"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            precioText.isEmpty() -> MaterialTheme.colorScheme.onSurface
                            precioText.toIntOrNull() == null -> MaterialTheme.colorScheme.error
                            precioText.toInt() < 1000 || precioText.toInt() > 999999 -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción (Opcional)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 80.dp),
                        maxLines = 4
                    )
                    Text(
                        text = "Campo opcional - Máximo 500 caracteres (${descripcion.length}/500)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    ImageCapture(
                        onImageCaptured = { uri ->
                            imagenUri = uri
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val precio = precioText.toIntOrNull()

                            when {
                                nombre.isBlank() -> {
                                    mensajeError = "⚠️ El nombre es obligatorio"
                                    showError = true
                                }
                                nombre.length < 3 -> {
                                    mensajeError = "⚠️ El nombre debe tener mínimo 3 caracteres (${nombre.length}/3)"
                                    showError = true
                                }
                                precioText.isBlank() -> {
                                    mensajeError = "⚠️ El precio es obligatorio"
                                    showError = true
                                }
                                precio == null -> {
                                    mensajeError = "⚠️ El precio debe ser un número válido"
                                    showError = true
                                }
                                precio < 1000 -> {
                                    mensajeError = "⚠️ Precio mínimo \$1.000 (ingresó: \$${precio})"
                                    showError = true
                                }
                                precio > 999999 -> {
                                    mensajeError = "⚠️ Precio máximo \$999.999 (ingresó: \$${precio})"
                                    showError = true
                                }
                                else -> {
                                    val productoActualizado = productoOriginal!!.copy(
                                        nombre = nombre,
                                        precio = precio,
                                        descripcion = descripcion,
                                        imagen = imagenUri
                                    )
                                    homeViewModel.actualizarProducto(productoActualizado)
                                    navController.popBackStack()
                                }
                            }

                            if (showError && mensajeError.isNotEmpty()) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = mensajeError,
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar Cambios")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onError
                        )
                    ) {
                        Text("Cancelar")
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "Producto no encontrado",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


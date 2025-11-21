package com.SPA.Perfulandia.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.SPA.Perfulandia.ui.components.ImageCapture
import com.SPA.Perfulandia.ui.components.BarraInferior
import com.SPA.Perfulandia.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    homeViewModel: HomeViewModel,
    onBack: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var precioText by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var imagenUri by rememberSaveable { mutableStateOf<String?>(null) }

    var showError by rememberSaveable { mutableStateOf(false) }

    var mensajeError by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(
                onAgregarClick = { /* Ya estamos agregando */ },
                onHomeClick = onBack,
                onSearchClick = { /* La búsqueda está en home */ }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
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
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 80.dp),
                    maxLines = 4
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
                                homeViewModel.agregarProducto(
                                    nombre = nombre,
                                    precio = precio,
                                    descripcion = descripcion,
                                    imagen = imagenUri
                                )
                                onBack()
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
                    Text("Guardar Producto")
                }
            }
        }
    }
}

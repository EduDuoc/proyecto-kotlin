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
import com.SPA.Perfulandia.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    homeViewModel: HomeViewModel,
    onBack: () -> Unit
) {
    // Variables de estado para los campos del formulario
    // rememberSaveable preserva los valores si la pantalla se recompone
    var nombre by rememberSaveable { mutableStateOf("") }
    var precioText by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var imagenUri by rememberSaveable { mutableStateOf<String?>(null) }

    // Flag para mostrar/ocultar mensajes de error
    var showError by rememberSaveable { mutableStateOf(false) }

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
        }
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

                // Componente de captura de imagen
                ImageCapture(
                    onImageCaptured = { uri ->
                        imagenUri = uri
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        // Convertir el texto del precio a entero
                        // toIntOrNull() retorna null si la conversión falla
                        val precio = precioText.toIntOrNull()

                        // VALIDACIÓN: Verificar que el precio sea válido y nombre no esté vacío
                        if (precio == null || nombre.isBlank()) {
                            // Si falla la validación, mostrar error
                            showError = true
                        } else {
                            // Validación exitosa: insertar producto con todos los datos
                            homeViewModel.agregarProducto(
                                nombre = nombre,
                                precio = precio,
                                descripcion = descripcion,
                                imagen = imagenUri  // Puede ser null, es opcional
                            )
                            // Volver a la pantalla anterior después de guardar
                            onBack()
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

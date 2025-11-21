package com.SPA.Perfulandia.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File
import java.io.FileOutputStream

@Composable
fun ImageCapture(
    onImageCaptured: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    // Estado para guardar el URI de la imagen seleccionada de galería
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // Estado para guardar el Bitmap capturado con la cámara
    var capturedPhotoBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Launcher para capturar foto con la cámara del dispositivo
    // TakePicturePreview devuelve un Bitmap directamente
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        try {
            if (bitmap != null) {
                capturedPhotoBitmap = bitmap
                // Guardar el bitmap como archivo JPEG y obtener su URI
                val savedUri = saveBitmapToFile(context, bitmap)
                Log.d("ImageCapture", "Foto guardada: $savedUri")
                // Notificar al componente padre con la URI del archivo guardado
                onImageCaptured(savedUri)
            }
        } catch (e: Exception) {
            Log.e("ImageCapture", "Error captura:", e)
        }
    }

    // Launcher para seleccionar imagen de la galería
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        try {
            if (uri != null) {
                selectedImageUri = uri
                Log.d("ImageCapture", "Imagen seleccionada: $uri")
                onImageCaptured(uri.toString())
            }
        } catch (e: Exception) {
            Log.e("ImageCapture", "Error galería:", e)
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Imagen del Producto",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Previa de imagen capturada
        if (capturedPhotoBitmap != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Image(
                    bitmap = capturedPhotoBitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
        // Previa de imagen de galería
        else if (selectedImageUri != null) {
            Card(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { cameraLauncher.launch(null) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cámara")
            }

            Button(
                onClick = {
                    photoPicker.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Galería")
            }
        }

        if (capturedPhotoBitmap != null || selectedImageUri != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    capturedPhotoBitmap = null
                    selectedImageUri = null
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Text("Limpiar")
            }
        }
    }
}

// Función privada que guarda un Bitmap como archivo JPEG en el almacenamiento del dispositivo
private fun saveBitmapToFile(context: Context, bitmap: Bitmap): String {
    return try {
        // Crear directorio "product_images" en el almacenamiento privado de la app
        val dir = File(context.filesDir, "product_images")
        if (!dir.exists()) dir.mkdirs()

        // Crear archivo con nombre único usando timestamp: IMG_1234567890.jpg
        val file = File(dir, "IMG_${System.currentTimeMillis()}.jpg")

        // Guardar el bitmap como JPEG comprimido al 85% de calidad
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, it)
        }

        // Retornar la URI del archivo guardado en formato file://
        file.toURI().toString()
    } catch (e: Exception) {
        Log.e("ImageCapture", "Error guardar:", e)
        "" // Retornar string vacío si hay error
    }
}


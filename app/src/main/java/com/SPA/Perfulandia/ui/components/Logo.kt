package com.SPA.Perfulandia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.SPA.Perfulandia.R

@Composable
fun Logo(modifier: Modifier = Modifier) {

    Image(
        painter = painterResource(id = R.drawable.image),
        contentDescription = "Imagen de ejemplo",// Descripción para accesibilidad
        modifier = modifier,// Aplica el modificador pasado como parámetro
        contentScale = ContentScale.Inside // Ajusta la imagen para que quepa dentro del contenedor sin recortarla
    )
}
package com.SPA.Perfulandia.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.SPA.Perfulandia.R

@Composable
fun Logo(modifier: Modifier = Modifier) {
    // compnente Logo
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Imagen de ejemplo",
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}



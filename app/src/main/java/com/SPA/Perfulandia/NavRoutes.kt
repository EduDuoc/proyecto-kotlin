package com.SPA.Perfulandia


object NavRoutes {
    // Ruta principal - Pantalla de inicio
    const val HOME = "home"
    
    // Rutas de productos
    const val ADD_PRODUCT = "add_product"
    const val EDIT_PRODUCT = "edit_product"

    // Rutas con parámetros dinámicos
    fun detailWithId(id: Int) = "detail/$id"
    fun editWithId(id: Int) = "edit_product/$id"
}
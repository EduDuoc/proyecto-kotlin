package com.SPA.Perfulandia


object NavRoutes {
    const val HOME = "home"
    const val ADD_PRODUCT = "add_product"

    // Rutas con parámetros
    fun detailWithId(id: Int) = "detail/$id"
    fun editWithId(id: Int) = "edit_product/$id"
}
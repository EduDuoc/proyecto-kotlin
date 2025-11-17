package com.SPA.Perfulandia


object NavRoutes {
    const val HOME = "home"
    const val DETAIL = "detail"
    const val PROFILE = "profile"
    const val FORM = "form"
    const val CONFIRM = "confirm"

    // Ruta padre, toma el mismo ViewModel usando el BackStackEntry del navGraph
    const val FORM_GRAPH = "form_graph"

    const val ADD_PRODUCT = "add_product"
    const val EDIT_PRODUCT = "edit_product"

    // Rutas con parámetros
    fun detailWithId(id: Int) = "detail/$id"
    fun editWithId(id: Int) = "edit_product/$id"
}
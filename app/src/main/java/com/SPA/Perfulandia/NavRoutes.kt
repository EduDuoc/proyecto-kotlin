package com.SPA.Perfulandia


object NavRoutes {
    const val HOME = "home"
    const val ADD_PRODUCT = "add_product"
    const val EDIT_PRODUCT = "edit_product"

    fun detailWithId(id: Int) = "detail/$id"
    fun editWithId(id: Int) = "edit_product/$id"
}
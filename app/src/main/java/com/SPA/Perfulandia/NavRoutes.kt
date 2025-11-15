package com.SPA.Perfulandia

object NavRoutes {
    const val HOME = "home"
    const val DETAIL_WITH_ARG = "detail/{id}"
    const val PROFILE = "profile"

    fun detailWithId(id: Int) = "detail/$id"
}

package com.SPA.Perfulandia.model

import androidx.room.Entity
import androidx.room.PrimaryKey


// Anotación @Entity: Define que esta clase es una tabla en la BD de Room
// tableName: Nombre de la tabla en la BD (en este caso "producto")
@Entity(tableName = "producto")
data class Producto(
    // @PrimaryKey: Define esta columna como clave primaria de la tabla
    // autoGenerate = true: La BD genera automáticamente el ID único para cada producto
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,      // Nombre del perfume
    val precio: Int,         // Precio del perfume en formato entero
    val descripcion: String, // Descripción detallada del producto
    val imagen: String? = null  // URI de la imagen del producto (nullable = opcional)
)
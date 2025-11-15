package com.SPA.Perfulandia.repository

import com.SPA.Perfulandia.model.Producto

class ProductoRepository {


    private val productos = mutableListOf(
        Producto(id = 1, nombre = "Perfume Aurora", precio = 59.99, imagen = "image"),
        Producto(id = 2, nombre = "Colonia Brisa", precio = 29.50, imagen = "image"),
        Producto(id = 3, nombre = "Eau de Nuit", precio = 74.20, imagen = "image"),
        Producto(id = 4, nombre = "Citrus Fresh", precio = 39.90, imagen = "image"),
        Producto(id = 5, nombre = "Velvet Rose", precio = 89.00, imagen = "image"),
        Producto(id = 6, nombre = "Ocean Blue", precio = 45.75, imagen = "image"),
        Producto(id = 7, nombre = "Wood & Spice", precio = 69.30, imagen = "image"),
        Producto(id = 8, nombre = "Vanilla Dream", precio = 34.99, imagen = "image"),
        Producto(id = 9, nombre = "Golden Sand", precio = 64.50, imagen = "image"),
        Producto(id = 10, nombre = "Floral Mist", precio = 52.40, imagen = "image")
    )

    // Obtener todos los productos
    fun obtenerProductos(): List<Producto> {
        return productos
    }

    // Obtener un producto por ID
    fun obtenerProductoPorId(id: Int): Producto? {
        return productos.find { it.id == id }
    }

    // Agregar un producto
    fun agregarProducto(producto: Producto) {
        productos.add(producto)
    }

    // Eliminar un producto
    fun eliminarProducto(id: Int) {
        productos.removeAll { it.id == id }
    }
}

package com.SPA.Perfulandia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SPA.Perfulandia.model.Producto
import com.SPA.Perfulandia.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    // StateFlow que emite la lista de productos desde el repositorio
    // Se convierte a State para que la UI se recomponga automáticamente cuando cambia
    // WhileSubscribed(5000) mantiene la suscripción activa por 5 segundos tras el último observador
    val productos = repository.productos.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    // Inserta un nuevo producto en la base de datos
    // Usa viewModelScope para ejecutar la corrutina de forma asincrónica sin bloquear la UI
    fun agregarProducto(nombre: String, precio: Int, descripcion: String, imagen: String? = null) {
        viewModelScope.launch {
            repository.insertar(
                Producto(
                    nombre = nombre,
                    precio = precio,
                    descripcion = descripcion,
                    imagen = imagen
                )
            )
        }
    }

    // Elimina un producto de la base de datos
    fun eliminarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.eliminar(producto)
        }
    }

    // Actualiza un producto existente en la base de datos
    fun actualizarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.actualizar(producto)
        }
    }

    // Obtiene un producto por su ID de forma asincrónica (suspend function)
    suspend fun obtenerProductoPorId(id: Int): Producto? {
        return repository.obtenerPorId(id)
    }
}

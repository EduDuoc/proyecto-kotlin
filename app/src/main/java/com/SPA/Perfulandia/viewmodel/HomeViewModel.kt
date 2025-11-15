package com.SPA.Perfulandia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.SPA.Perfulandia.model.Producto
import com.SPA.Perfulandia.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = ProductoRepository()

    // Estado de la lista de productos
    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        cargarProductos()
    }

    // Cargar productos desde el repositorio
    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            _productos.value = repository.obtenerProductos()
            _isLoading.value = false
        }
    }

    // Obtener un producto por ID
    fun obtenerProductoPorId(id: Int): Producto? {
        return repository.obtenerProductoPorId(id)
    }

    // Agregar un producto
    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.agregarProducto(producto)
            cargarProductos()
        }
    }
}


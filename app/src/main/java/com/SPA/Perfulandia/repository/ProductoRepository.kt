package com.SPA.Perfulandia.repository

import com.SPA.Perfulandia.data.database.ProductoDao
import com.SPA.Perfulandia.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(
    private val dao: ProductoDao
){
    // Expone el Stream de productos desde la BD
    // Flow emite automáticamente cuando la BD cambia
    // ViewModel y UI observan este Flow y se actualizan automáticamente
    val productos: Flow<List<Producto>> = dao.getAll()

    // INSERT: Agrega un nuevo producto a la BD
    // suspend indica que es asincrónico y debe llamarse desde una corrutina
    suspend fun insertar(producto: Producto) {
        dao.insert(producto)
    }

    // DELETE: Elimina un producto existente
    // El producto debe tener un ID válido que exista en la BD
    suspend fun eliminar(producto: Producto) {
        dao.delete(producto)
    }

    // UPDATE: Actualiza un producto existente
    // El producto debe tener un ID válido
    suspend fun actualizar(producto: Producto) {
        dao.update(producto)
    }

    // SELECT by ID: Obtiene un producto específico
    // Retorna null si el ID no existe
    suspend fun obtenerPorId(id: Int): Producto? {
        return dao.getById(id)
    }
}
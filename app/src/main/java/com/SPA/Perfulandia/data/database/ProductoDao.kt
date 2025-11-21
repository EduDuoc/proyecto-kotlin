package com.SPA.Perfulandia.data.database

import androidx.room.*
import com.SPA.Perfulandia.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    // INSERT: Inserta un nuevo producto en la tabla "producto"
    // OnConflictStrategy.REPLACE: Si el ID ya existe, lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    // DELETE: Elimina un producto existente de la tabla
    @Delete
    suspend fun delete(producto: Producto)

    // UPDATE: Actualiza un producto existente (debe tener un ID válido)
    @Update
    suspend fun update(producto: Producto)

    // SELECT * FROM producto: Obtiene todos los productos
    // Retorna un Flow para que la UI se recomponga automáticamente cuando haya cambios
    @Query("SELECT * FROM producto")
    fun getAll(): Flow<List<Producto>>

    // SELECT * FROM producto WHERE id = :id: Obtiene un producto específico por su ID
    // Retorna null si no encuentra coincidencias
    @Query("SELECT * FROM producto WHERE id = :id")
    suspend fun getById(id: Int): Producto?
}
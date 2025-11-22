package com.SPA.Perfulandia.data.database

import androidx.room.*
import com.SPA.Perfulandia.model.Producto
import kotlinx.coroutines.flow.Flow
//este archivo define las operaciones de la base de datos para la entidad Producto
//
@Dao//marca el archivo como operaciones de la base de datos
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Delete
    suspend fun delete(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Query("SELECT * FROM producto")
    fun getAll(): Flow<List<Producto>>

    @Query("SELECT * FROM producto WHERE id = :id")
    suspend fun getById(id: Int): Producto?
}
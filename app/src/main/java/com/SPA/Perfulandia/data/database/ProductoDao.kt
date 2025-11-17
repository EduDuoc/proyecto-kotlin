package com.SPA.Perfulandia.data.database

import androidx.room.*
import com.SPA.Perfulandia.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
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
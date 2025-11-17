package com.SPA.Perfulandia.data.database
import com.SPA.Perfulandia.model.Producto
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Producto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
}

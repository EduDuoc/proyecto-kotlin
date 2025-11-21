package com.SPA.Perfulandia.data.database
import com.SPA.Perfulandia.model.Producto
import androidx.room.Database
import androidx.room.RoomDatabase

// Anotación que define la base de datos Room
// entities: Las tablas que contiene la BD (en este caso, la tabla "producto")
// version: Versión actual de la BD (importante para migraciones)
// exportSchema: false para no generar archivo de esquema (false recomendado en desarrollo)
@Database(
    entities = [Producto::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Método abstracto que retorna el DAO
    // Este DAO proporciona acceso a las operaciones CRUD
    abstract fun productoDao(): ProductoDao
}

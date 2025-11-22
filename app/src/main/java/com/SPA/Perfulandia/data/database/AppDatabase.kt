package com.SPA.Perfulandia.data.database
import com.SPA.Perfulandia.model.Producto
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Producto::class],//tablas que tendra la base de datos  //[Producto::class, Cliente::class, Pedido::class]
    version = 2,//version de la base de datos
    exportSchema = false//para no migrar esquemas()
    //Si fuera true: Room generaría archivos JSON con la estructura de la BD en cada versión
)
abstract class AppDatabase : RoomDatabase() { //appdatabase: nombre de la base de datos
    abstract fun productoDao(): ProductoDao //funcion que da acceso al ProductoDao(donde defino operaciones)
}
//Resumen: crea la base de datos con las tablas  y da acceso a las operaciones de ProductoDao
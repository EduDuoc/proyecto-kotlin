package com.SPA.Perfulandia.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room

// PATRÓN SINGLETON: Garantiza que solo exista UNA instancia de la BD en toda la app
object DatabaseProvider {

    // Variable que almacena la única instancia de la BD
    private var INSTANCE: AppDatabase? = null

    // Función que retorna la instancia única de la BD
    // Si no existe, la crea; si existe, la retorna
    fun getDatabase(context: Context): AppDatabase {
        // Elvis operator (?: ) retorna INSTANCE si no es null, si no ejecuta el bloque
        return INSTANCE ?: synchronized(this) {  // synchronized previene race conditions
            try {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_local_db"  // Nombre del archivo de BD SQLite
                )
                    // fallbackToDestructiveMigration: Recrea la BD si hay cambios de versión
                    // (Usa solo en desarrollo, no en producción)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            } catch (e: Exception) {
                Log.e("DatabaseProvider", "Error creating database", e)
                throw e
            }
        }
    }
}
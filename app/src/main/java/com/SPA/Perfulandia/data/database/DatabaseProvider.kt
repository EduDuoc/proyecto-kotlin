package com.SPA.Perfulandia.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room
//crea y guarda una sola instancia de la base de datos para toda la app
object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this)/*evita que se creen bases de datos multiples*/{
            try {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_local_db"//nombre de la base de datos
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance //guarda la base de datos creada
                instance
            } catch (e: Exception) {
                Log.e("DatabaseProvider", "Error creating database", e)
                throw e
            }
        }
    }
}
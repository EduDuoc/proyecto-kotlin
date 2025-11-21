package com.SPA.Perfulandia.data.database

import android.content.Context
import android.util.Log
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null
    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            try {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_local_db"
                )
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
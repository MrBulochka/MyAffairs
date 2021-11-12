package com.notacompany.myaffairs.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task

@Database(entities = arrayOf(Project::class, Task::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "MyAffairs_database"

                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
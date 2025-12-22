package com.example.testreactions.database

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room.databaseBuilder
import com.example.testreactions.data.PlayerDao
import com.example.testreactions.data.PlayerEntity


@Database(
    entities = [PlayerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recordsDao(): PlayerDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build().also {
                instance = it
            }
        }
    }
}
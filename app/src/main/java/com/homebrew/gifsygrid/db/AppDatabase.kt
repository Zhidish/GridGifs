package com.homebrew.gifsygrid.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GifEntity::class,RemoteKeys::class], version = 3,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gifDao(): GifDao
    abstract fun gifRemoteKeysDao():RemoteKeysDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gif_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}

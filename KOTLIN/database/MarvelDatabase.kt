package com.example.marvel.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCharacter::class], version = 1)
abstract class MarvelDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: MarvelDatabase? = null

        fun getInstance(context: Context): MarvelDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MarvelDatabase::class.java,
                    "marvel_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

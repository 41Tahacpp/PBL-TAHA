package com.example.marvel.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_characters")
data class FavoriteCharacter(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)

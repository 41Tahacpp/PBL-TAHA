package com.example.marvel.database

import androidx.room.*

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FavoriteCharacter)

    @Delete
    suspend fun delete(character: FavoriteCharacter)

    @Query("SELECT * FROM favorite_characters")
    suspend fun getAllFavorites(): List<FavoriteCharacter>

    @Query("SELECT EXISTS(SELECT * FROM favorite_characters WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}

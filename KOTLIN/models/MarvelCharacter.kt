package com.example.marvel.models
import com.example.marvel.database.FavoriteCharacter

data class CharacterResponse(
    val data: CharacterData
)

data class CharacterData(
    val results: List<MarvelCharacter>
)

data class MarvelCharacter(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun getFullUrl(): String = "$path.$extension".replace("http://", "https://")
}
fun MarvelCharacter.toFavorite(): FavoriteCharacter {
    return FavoriteCharacter(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.thumbnail.getFullUrl()
    )
}
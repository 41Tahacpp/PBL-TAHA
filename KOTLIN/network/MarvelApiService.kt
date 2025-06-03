package com.example.marvel.network

import com.example.marvel.models.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiService {
    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): Response<CharacterResponse>
}

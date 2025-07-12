package com.example.animebox.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

data class AnimeResponse(
    val data: List<AnimeItem>
)

data class AnimeItem(
    val mal_id: Int,
    val title: String,
    val images: Images
)

data class Images(
    val jpg: Jpg
)

data class Jpg(
    val image_url: String
)

data class AnimeDetailsResponse(
    val data: AnimeDetailsItem
)

data class AnimeDetailsItem(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val images: Images,
    val score: Float?,
    val genres: List<Genre>?
)

data class Genre(
    val name: String
)

interface AnimeApi {
    @GET("anime")
    suspend fun getAnime(
        @Query("q") query: String = "",
        @Query("limit") limit: Int = 10
    ): AnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetailsResponse
} 
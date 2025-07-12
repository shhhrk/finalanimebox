package com.example.animebox.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val imageUrl: String?
)

data class AnimeDetailsItem(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val imageUrl: String?,
    val score: Float?,
    val genres: List<Genre>?
)

data class Genre(
    val name: String
) 
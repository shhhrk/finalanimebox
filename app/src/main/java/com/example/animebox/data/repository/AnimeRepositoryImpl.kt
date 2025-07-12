package com.example.animebox.data.repository

import com.example.animebox.data.api.AnimeApi
import com.example.animebox.domain.model.Anime
import com.example.animebox.domain.model.AnimeDetailsItem
import com.example.animebox.domain.model.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepositoryImpl(private val api: AnimeApi) : AnimeRepository {
    override suspend fun getAnimeList(query: String, limit: Int): List<Anime> = withContext(Dispatchers.IO) {
        val response = api.getAnime(query, limit)
        response.data.map {
            Anime(
                id = it.mal_id,
                title = it.title,
                imageUrl = it.images.jpg.image_url
            )
        }
    }

    override suspend fun getAnimeDetails(id: Int): AnimeDetailsItem = withContext(Dispatchers.IO) {
        val details = api.getAnimeDetails(id).data
        AnimeDetailsItem(
            mal_id = details.mal_id,
            title = details.title ?: "Без названия",
            synopsis = details.synopsis ?: "Описание отсутствует",
            imageUrl = details.images?.jpg?.image_url,
            score = details.score,
            genres = details.genres?.mapNotNull { it?.name?.let { name -> Genre(name) } }
        )
    }
} 
package com.example.animebox.data.repository

import com.example.animebox.domain.model.Anime
import com.example.animebox.domain.model.AnimeDetailsItem

interface AnimeRepository {
    suspend fun getAnimeList(query: String = "", limit: Int = 10): List<Anime>
    suspend fun getAnimeDetails(id: Int): AnimeDetailsItem
} 
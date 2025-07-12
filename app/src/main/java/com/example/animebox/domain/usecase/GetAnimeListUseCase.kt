package com.example.animebox.domain.usecase

import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.domain.model.Anime

class GetAnimeListUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String = "", limit: Int = 10): List<Anime> {
        return repository.getAnimeList(query, limit)
    }
}

class GetAnimeDetailsUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int) = repository.getAnimeDetails(id)
} 
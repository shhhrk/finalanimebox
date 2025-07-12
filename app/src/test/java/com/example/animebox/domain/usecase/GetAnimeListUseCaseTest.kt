package com.example.animebox.domain.usecase

import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.domain.model.Anime
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAnimeListUseCaseTest {

    private lateinit var getAnimeListUseCase: GetAnimeListUseCase
    private val animeRepository: AnimeRepository = mock()

    @Before
    fun setUp() {
        getAnimeListUseCase = GetAnimeListUseCase(animeRepository)
    }

    @Test
    fun `invoke returns anime list from repository`() = runBlocking {
        val query = "test"
        val limit = 5
        val expectedAnimeList = listOf(
            Anime(id = 1, title = "Anime 1", imageUrl = ""),
            Anime(id = 2, title = "Anime 2", imageUrl = "")
        )
        whenever(animeRepository.getAnimeList(query, limit)).thenReturn(expectedAnimeList)
        val result = getAnimeListUseCase(query, limit)
        assertEquals(expectedAnimeList, result)
    }
} 
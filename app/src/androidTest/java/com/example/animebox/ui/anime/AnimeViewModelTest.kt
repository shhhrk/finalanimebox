package com.example.animebox.ui.anime

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.domain.model.Anime
import com.example.animebox.domain.model.AnimeDetailsItem
import com.example.animebox.domain.usecase.GetAnimeDetailsUseCase
import com.example.animebox.domain.usecase.GetAnimeListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(com.example.animebox.di.RepositoryModule::class)
class AnimeViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: AnimeRepository

    private lateinit var getAnimeListUseCase: GetAnimeListUseCase
    private lateinit var getAnimeDetailsUseCase: GetAnimeDetailsUseCase
    private lateinit var viewModel: AnimeViewModel
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun setUp() {
        hiltRule.inject()
        getAnimeListUseCase = GetAnimeListUseCase(repository)
        getAnimeDetailsUseCase = GetAnimeDetailsUseCase(repository)
        Dispatchers.setMain(testDispatcher)
        viewModel = AnimeViewModel(getAnimeListUseCase, getAnimeDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadAnime_whenCalled_updatesAnimeListWithDataFromRepository() = runTest {
        viewModel.loadAnime()
        advanceUntilIdle()
        val animeList = viewModel.animeList.value
        assertEquals(2, animeList.size)
        assertEquals("Naruto", animeList[0].title)
    }
    @Module
    @InstallIn(SingletonComponent::class)
    object TestRepositoryModule {

        @Singleton
        @Provides
        fun provideAnimeRepository(): AnimeRepository =
            object : AnimeRepository {
                override suspend fun getAnimeList(query: String, limit: Int): List<Anime> {
                    return listOf(
                        Anime(1, "Naruto", ""),
                        Anime(2, "Bleach", "")
                    )
                }
                override suspend fun getAnimeDetails(id: Int): AnimeDetailsItem {
                    return AnimeDetailsItem(
                        mal_id = id,
                        title = "Test Details",
                        synopsis = "Synopsis",
                        imageUrl = "",
                        score = 8.0f,
                        genres = emptyList()
                    )
                }
            }
    }
} 
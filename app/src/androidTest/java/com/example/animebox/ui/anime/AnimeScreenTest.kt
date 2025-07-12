package com.example.animebox.ui.anime
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.animebox.MainActivity
import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.domain.model.Anime
import com.example.animebox.domain.model.AnimeDetailsItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(com.example.animebox.di.RepositoryModule::class)
class AnimeScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun givenOnAnimeScreen_whenSearchIsClicked_thenListIsDisplayed() {
        composeTestRule.onNodeWithTag("api_button").performClick()
        composeTestRule.onNodeWithText("Поиск").performClick()
        Thread.sleep(2500)
        composeTestRule.onNodeWithText("Naruto").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bleach").assertIsDisplayed()
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
package com.example.animebox.ui.anime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animebox.domain.model.Anime
import com.example.animebox.domain.model.AnimeDetailsItem
import com.example.animebox.domain.usecase.GetAnimeListUseCase
import com.example.animebox.domain.usecase.GetAnimeDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val getAnimeListUseCase: GetAnimeListUseCase,
    private val getAnimeDetailsUseCase: GetAnimeDetailsUseCase
) : ViewModel() {
    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList

    private val _animeDetails = MutableStateFlow<AnimeDetailsItem?>(null)
    val animeDetails: StateFlow<AnimeDetailsItem?> = _animeDetails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadAnime(query: String = "", limit: Int = 10) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                delay(2000)
                _animeList.value = getAnimeListUseCase(query, limit)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Ошибка загрузки аниме"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAnimeDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                delay(2000)
                _animeDetails.value = getAnimeDetailsUseCase(id)
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Ошибка загрузки деталей"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearAnimeDetails() {
        _animeDetails.value = null
    }

    fun clearError() {
        _errorMessage.value = null
    }
} 
package com.example.animebox.di

import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.domain.usecase.GetAnimeDetailsUseCase
import com.example.animebox.domain.usecase.GetAnimeListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetAnimeListUseCase(repository: AnimeRepository): GetAnimeListUseCase {
        return GetAnimeListUseCase(repository)
    }

    @Provides
    fun provideGetAnimeDetailsUseCase(repository: AnimeRepository): GetAnimeDetailsUseCase {
        return GetAnimeDetailsUseCase(repository)
    }
} 
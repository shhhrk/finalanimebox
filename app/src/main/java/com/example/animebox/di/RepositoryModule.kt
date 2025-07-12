package com.example.animebox.di

import com.example.animebox.data.api.AnimeApi
import com.example.animebox.data.repository.AnimeRepository
import com.example.animebox.data.repository.AnimeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(api: AnimeApi): AnimeRepository {
        return AnimeRepositoryImpl(api)
    }
} 
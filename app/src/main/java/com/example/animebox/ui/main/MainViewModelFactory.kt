package com.example.animebox.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.animebox.domain.usecase.GetCharactersUseCase
import com.example.animebox.domain.usecase.ManageCrystalsUseCase

class MainViewModelFactory(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val manageCrystalsUseCase: ManageCrystalsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(getCharactersUseCase, manageCrystalsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 
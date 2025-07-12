package com.example.animebox.domain.model

import androidx.annotation.DrawableRes

data class Character(
    val name: String,
    val rarity: String,
    @DrawableRes val imageRes: Int
) 
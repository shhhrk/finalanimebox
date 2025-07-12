package com.example.animebox

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimeBoxApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("DARK_THEME", false)
        
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
} 
package com.example.animebox.domain.usecase

import android.content.Context
import com.example.animebox.data.AppDatabase
import com.example.animebox.data.CrystalsDao
import com.example.animebox.data.CrystalsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ManageCrystalsUseCase(context: Context) {
    private val crystalsDao: CrystalsDao = AppDatabase.getInstance(context).crystalsDao()

    suspend fun getCrystals(): Int = withContext(Dispatchers.IO) {
        crystalsDao.getCrystals()?.amount ?: 0
    }

    suspend fun addAdvertisementReward() = withContext(Dispatchers.IO) {
        val current = getCrystals()
        crystalsDao.setCrystals(CrystalsEntity(amount = current + 200))
    }

    suspend fun tryOpenLootbox(): Boolean = withContext(Dispatchers.IO) {
        val current = getCrystals()
        if (current >= 50) {
            crystalsDao.setCrystals(CrystalsEntity(amount = current - 50))
            true
        } else {
            false
        }
    }
} 
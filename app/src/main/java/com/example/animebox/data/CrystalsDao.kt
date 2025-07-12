package com.example.animebox.data

import androidx.room.*

@Dao
interface CrystalsDao {
    @Query("SELECT * FROM crystals WHERE id = 0")
    suspend fun getCrystals(): CrystalsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCrystals(crystals: CrystalsEntity)
} 
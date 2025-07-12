package com.example.animebox.data

import androidx.room.*

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE name = :name AND rarity = :rarity LIMIT 1")
    suspend fun getByNameAndRarity(name: String, rarity: String): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity)

    @Update
    suspend fun update(character: CharacterEntity)
} 
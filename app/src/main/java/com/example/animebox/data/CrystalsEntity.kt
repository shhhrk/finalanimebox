package com.example.animebox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crystals")
data class CrystalsEntity(
    @PrimaryKey val id: Int = 0,
    val amount: Int
) 
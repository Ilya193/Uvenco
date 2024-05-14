package ru.ikom.database.drinks

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drinks")
data class DrinkDBO(
    @PrimaryKey
    val id: Int,
    val name: String,
    val percent: Float,
    val price: Int?
)
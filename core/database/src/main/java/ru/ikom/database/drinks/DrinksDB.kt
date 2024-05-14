package ru.ikom.database.drinks

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DrinkDBO::class],
    version = 1
)
abstract class DrinksDB : RoomDatabase() {
    abstract fun drinksDao(): DrinksDao
}
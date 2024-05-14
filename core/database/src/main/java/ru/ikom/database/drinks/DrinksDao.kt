package ru.ikom.database.drinks

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DrinksDao {
    @Query("SELECT * FROM drinks")
    fun fetchDrinks(): Flow<List<DrinkDBO>>

    @Query("SELECT * FROM DRINKS WHERE id = :drinkId")
    suspend fun fetchDrinkById(drinkId: Int): DrinkDBO

    @Update
    suspend fun updateDrink(drink: DrinkDBO)
}
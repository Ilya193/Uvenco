package ru.ikom.drinks

import kotlinx.coroutines.flow.Flow

interface DrinksCacheDataSource {
    suspend fun fetchDrinks(): Flow<List<DrinkData>>
    suspend fun fetchDrinkById(drinkId: Int): DrinkData
    suspend fun updateDrink(drink: DrinkData)
}
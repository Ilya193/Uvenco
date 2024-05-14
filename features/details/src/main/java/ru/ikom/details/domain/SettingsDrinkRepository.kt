package ru.ikom.details.domain

interface SettingsDrinkRepository {
    suspend fun fetchDrink(drinkId: Int): DrinkDomain
    suspend fun updateDrink(drink: DrinkDomain)
}
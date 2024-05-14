package ru.ikom.details.data

import ru.ikom.details.domain.DrinkDomain
import ru.ikom.details.domain.SettingsDrinkRepository
import ru.ikom.drinks.DrinksCacheDataSource

class SettingsDrinkRepositoryImpl(
    private val cacheDataSource: DrinksCacheDataSource
) : SettingsDrinkRepository {
    override suspend fun fetchDrink(drinkId: Int): DrinkDomain {
        return cacheDataSource.fetchDrinkById(drinkId).toDrinkDomain()
    }

    override suspend fun updateDrink(drink: DrinkDomain) {
        return cacheDataSource.updateDrink(drink.toDrinkData())
    }
}
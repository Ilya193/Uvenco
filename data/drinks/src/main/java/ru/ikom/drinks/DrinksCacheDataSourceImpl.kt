package ru.ikom.drinks

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ikom.database.drinks.DrinksDao

class DrinksCacheDataSourceImpl(
    private val dao: DrinksDao
) : DrinksCacheDataSource {
    override suspend fun fetchDrinks(): Flow<List<DrinkData>> {
        return dao.fetchDrinks().map { it.map { it.toDrinkData() } }
    }

    override suspend fun fetchDrinkById(drinkId: Int): DrinkData {
        return dao.fetchDrinkById(drinkId).toDrinkData()
    }

    override suspend fun updateDrink(drink: DrinkData) {
        dao.updateDrink(drink.toDrinkDBO())
    }
}
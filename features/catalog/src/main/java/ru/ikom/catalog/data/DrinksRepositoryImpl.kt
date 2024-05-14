package ru.ikom.catalog.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ikom.catalog.domain.DrinkDomain
import ru.ikom.catalog.domain.DrinksRepository
import ru.ikom.drinks.DrinksCacheDataSource

class DrinksRepositoryImpl(
    private val cacheDataSource: DrinksCacheDataSource
) : DrinksRepository {
    override suspend fun fetchDrinks(): Flow<List<DrinkDomain>> {
        return cacheDataSource.fetchDrinks().map { it.map { it.toDrinkDomain() } }
    }
}
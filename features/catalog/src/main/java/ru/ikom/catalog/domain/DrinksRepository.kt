package ru.ikom.catalog.domain

import kotlinx.coroutines.flow.Flow

interface DrinksRepository {
    suspend fun fetchDrinks(): Flow<List<DrinkDomain>>
}
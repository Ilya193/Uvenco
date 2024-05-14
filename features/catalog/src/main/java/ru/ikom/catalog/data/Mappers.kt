package ru.ikom.catalog.data

import ru.ikom.catalog.domain.DrinkDomain
import ru.ikom.drinks.DrinkData

fun DrinkData.toDrinkDomain(): DrinkDomain = DrinkDomain(id, name, percent, price)

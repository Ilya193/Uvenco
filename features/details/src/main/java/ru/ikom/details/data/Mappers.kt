package ru.ikom.details.data

import ru.ikom.details.domain.DrinkDomain
import ru.ikom.drinks.DrinkData

fun DrinkData.toDrinkDomain(): DrinkDomain = DrinkDomain(id, name, percent, price)
fun DrinkDomain.toDrinkData(): DrinkData = DrinkData(id, name, percent, price)
package ru.ikom.drinks

import ru.ikom.database.drinks.DrinkDBO

fun DrinkDBO.toDrinkData(): DrinkData = DrinkData(id, name, percent, price)
fun DrinkData.toDrinkDBO(): DrinkDBO = DrinkDBO(id, name, percent, price)
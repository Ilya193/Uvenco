package ru.ikom.details.presentation

import ru.ikom.details.domain.DrinkDomain

fun DrinkDomain.toDrinkUi(): DrinkUi = DrinkUi(id, name, percent, price)
fun DrinkUi.toDrinkDomain(): DrinkDomain = DrinkDomain(id, name, percent, price)
package ru.ikom.catalog.presentation

import ru.ikom.catalog.domain.DrinkDomain

fun DrinkDomain.toDrinkUi(): DrinkUi = DrinkUi(id, name, percent, price)
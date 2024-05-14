package ru.ikom.uvenco

object Screens {
    const val CATALOG = "Catalog"
    const val SETTINGS = "Settings?${Arguments.DRINK_ID}={${Arguments.DRINK_ID}}?${Arguments.DRINK_NAME}={${Arguments.DRINK_NAME}}?${Arguments.DRINK_PRICE}={${Arguments.DRINK_PRICE}}"
}

object Arguments {
    const val DRINK_ID = "drinkId"
    const val DRINK_NAME = "drinkName"
    const val DRINK_PRICE = "drinkPrice"
}
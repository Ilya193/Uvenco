package ru.ikom.catalog.domain

data class DrinkDomain(
    val id: Int,
    val name: String,
    val percent: Float,
    val price: Int?
)
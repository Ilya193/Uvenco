package ru.ikom.catalog.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class DrinkUi(
    val id: Int,
    val name: String,
    val percent: Float,
    val price: Int?
)
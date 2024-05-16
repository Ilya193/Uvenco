package ru.ikom.uvenco

import kotlinx.serialization.Serializable

@Serializable
object Catalog

@Serializable
data class Settings(
    val id: Int,
    val name: String,
    val price: String = ""
)
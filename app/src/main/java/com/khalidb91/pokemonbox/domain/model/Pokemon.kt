package com.khalidb91.pokemonbox.domain.model

data class Pokemon(
    val name: String,
    val types: List<String>,
    val description: String,
    val image: String?,
)

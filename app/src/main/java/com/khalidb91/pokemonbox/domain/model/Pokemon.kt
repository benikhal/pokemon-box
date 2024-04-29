package com.khalidb91.pokemonbox.domain.model

data class Pokemon(
    val name: String,
    val types: List<String>? = null,
    val description: String? = null,
    val image: String? = null,
)

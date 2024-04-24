package com.khalidb91.pokemonbox.domain.model

data class Pokemon(
    val name: String,
    val type: PokemonType,
    val description: String,
    val image: String?,
)

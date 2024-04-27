package com.khalidb91.pokemonbox.domain.repository

import com.khalidb91.pokemonbox.domain.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(offset: Int, limit: Int): List<Pokemon>

}


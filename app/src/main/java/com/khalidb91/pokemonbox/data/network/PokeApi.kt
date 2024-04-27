package com.khalidb91.pokemonbox.data.network

import com.khalidb91.pokemonbox.data.model.NamedApiResourceList

interface PokeApi {

    suspend fun getPokemonList(offset: Int, limit: Int): NamedApiResourceList

}

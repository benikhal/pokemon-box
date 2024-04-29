package com.khalidb91.pokemonbox.data.network

import com.khalidb91.pokemonbox.data.model.NamedApiResourceList
import com.khalidb91.pokemonbox.data.model.Pokemon

interface PokeApi {

    suspend fun getPokemonList(offset: Int, limit: Int): NamedApiResourceList

    suspend fun getPokemonDetail(name: String): Pokemon

}

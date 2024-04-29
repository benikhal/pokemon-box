package com.khalidb91.pokemonbox.data.network

import com.khalidb91.pokemonbox.data.model.NamedApiResourceList
import com.khalidb91.pokemonbox.data.model.Pokemon
import io.ktor.client.call.body
import io.ktor.client.request.get

class PokemonApi(private val config: ClientConfig) : PokeApi {

    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): NamedApiResourceList {
        return config.ktorHttpClient
            .get("${config.baseUrl}pokemon?offset=$offset&limit=$limit")
            .body<NamedApiResourceList>()
    }

    override suspend fun getPokemonDetail(name: String, ): Pokemon {
        return config.ktorHttpClient
            .get("${config.baseUrl}pokemon/$name")
            .body<Pokemon>()
    }

}
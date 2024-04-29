package com.khalidb91.pokemonbox.domain.repository

import com.khalidb91.pokemonbox.data.network.PokemonApi
import com.khalidb91.pokemonbox.domain.model.Pokemon

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemonList(offset: Int, limit: Int, search: String?): List<Pokemon> {

        val results = pokemonApi.getPokemonList(offset = offset, limit = limit).results

        val filteredResults = if (search.isNullOrBlank().not()) {
            results.filter { it.name.contains(search!!, ignoreCase = true) }
        } else {
            results
        }

        return filteredResults.map { pokemon ->
            val detail = pokemonApi.getPokemonDetail(pokemon.name)
            Pokemon(
                name = detail.name,
                image = detail.sprites.frontDefault,
                types = detail.types.map { it.type.name })
        }

    }

}
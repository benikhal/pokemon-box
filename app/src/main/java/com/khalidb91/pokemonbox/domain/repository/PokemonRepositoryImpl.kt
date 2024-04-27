package com.khalidb91.pokemonbox.domain.repository

import com.khalidb91.pokemonbox.data.network.PokemonApi
import com.khalidb91.pokemonbox.domain.model.Pokemon

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override suspend fun getPokemonList(
        offset: Int,
        limit: Int
    ): List<Pokemon> {
        return pokemonApi.getPokemonList(offset = offset, limit = limit).results.map {
            val imageUrlIndex = it.url.split("/").last { token -> token != "" }
            Pokemon(name = it.name, description = "Dopo la nascita, per un periodo di tempo cresce assorbendo i nutrienti stipati nel bulbo sul dorso.", image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${imageUrlIndex}.png", types = listOf("Normal", "Fire", "water"))
        }
    }

}
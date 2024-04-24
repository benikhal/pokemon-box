package com.khalidb91.pokemonbox.data

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonApi {

    private val baseUrl = "https://pokeapi.co/api/v2/"

    private val client = HttpClient()

    suspend fun getPokemonList(offset: Int, limit: Int): List<String> {
        return withContext(Dispatchers.IO) {
            val url = "$baseUrl/pokemon?offset=$offset&limit=$limit"
            val response = client.get<PokemonListResponse>(url)
            response.results.map { it.name }
        }
    }
}

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)

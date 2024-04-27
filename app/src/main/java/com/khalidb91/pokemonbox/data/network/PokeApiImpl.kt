package com.khalidb91.pokemonbox.data.network

import com.khalidb91.pokemonbox.data.model.NamedApiResourceList
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

    private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
        return try {
            ApiOperation.Success(data = apiCall())
        } catch (e: Exception) {
            ApiOperation.Failure(e)
        }
    }

}

sealed interface ApiOperation<T> {

    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    fun onSuccess(block: (T) -> Unit): ApiOperation<T> {
        if (this is Success) block(data)
        return this
    }

    fun onFailure(block: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) block(exception)
        return this
    }

}
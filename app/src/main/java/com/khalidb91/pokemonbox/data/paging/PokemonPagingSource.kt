package com.khalidb91.pokemonbox.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.domain.repository.PokemonRepository


class PokemonPagingSource(
    private val pokemonRepository: PokemonRepository,
    val search: String?
) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        return try {

            val currentPage = params.key ?: STARTING_KEY
            val offset = if (params.key != null) ((currentPage - 1) * ITEMS_PER_PAGE) else STARTING_KEY


            val result: List<Pokemon> = pokemonRepository.getPokemonList(
                offset = offset,
                limit = params.loadSize,
                search = search
            )

            LoadResult.Page(
                data = result,
                prevKey = null ,
                nextKey = if (result.isEmpty()) {
                    null
                } else {
                    currentPage + (params.loadSize / ITEMS_PER_PAGE)
                }
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val STARTING_KEY: Int = 0
        const val ITEMS_PER_PAGE = 20
    }


}
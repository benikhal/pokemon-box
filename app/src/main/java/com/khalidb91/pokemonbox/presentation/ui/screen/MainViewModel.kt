package com.khalidb91.pokemonbox.presentation.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.khalidb91.pokemonbox.data.network.ClientConfig
import com.khalidb91.pokemonbox.data.network.PokemonApi
import com.khalidb91.pokemonbox.data.paging.PokemonPagingSource
import com.khalidb91.pokemonbox.domain.repository.PokemonRepository
import com.khalidb91.pokemonbox.domain.repository.PokemonRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlin.time.Duration.Companion.milliseconds

class MainViewModel : ViewModel() {

    private val pokemonApi = PokemonApi(ClientConfig())
    private val pokemonRepository: PokemonRepository =
        PokemonRepositoryImpl(pokemonApi = pokemonApi)

    private val _searchText = MutableStateFlow("")
    val searchText = this._searchText.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = "",
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val productsSearchResults = searchText.debounce(300.milliseconds).flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = PokemonPagingSource.ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(
                    pokemonRepository = pokemonRepository,
                    search = query
                )
            }
        ).flow.cachedIn(viewModelScope)
    }


    fun setSearch(query: String) {
        this._searchText.value = query
    }

}
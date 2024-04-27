package com.khalidb91.pokemonbox.presentation.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalidb91.pokemonbox.data.network.ClientConfig
import com.khalidb91.pokemonbox.data.network.PokemonApi
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.domain.repository.PokemonRepository
import com.khalidb91.pokemonbox.domain.repository.PokemonRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val pokemonApi = PokemonApi(ClientConfig())
    private val pokemonRepository: PokemonRepository = PokemonRepositoryImpl(pokemonApi = pokemonApi)

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(listOf())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList.asStateFlow()

    fun onSearchTextChange(query: String) {

        _searchText.value = query

        viewModelScope.launch(Dispatchers.IO) {

            _isLoading.value = true

            try {
                val result: List<Pokemon> = pokemonRepository.getPokemonList(0, 20)
                _pokemonList.value = result
            } catch (e: Exception) {
                Log.e("", e.message, e)
            }

            _isLoading.value = false

        }
    }

}
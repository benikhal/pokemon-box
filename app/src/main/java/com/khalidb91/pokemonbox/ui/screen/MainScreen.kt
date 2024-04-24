package com.khalidb91.pokemonbox.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalidb91.pokemonbox.R
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.domain.model.PokemonType
import com.khalidb91.pokemonbox.ui.component.PokemonItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonSearchScreen() {

    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var pokemonList by remember {
        mutableStateOf<List<Pokemon>>(
            listOf(
                Pokemon("test", PokemonType.BUG, "test"),
                Pokemon("test", PokemonType.BUG, "test"),
                Pokemon("test", PokemonType.BUG, "test"),
                Pokemon("test", PokemonType.BUG, "test")
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo), // Placeholder image resource
            contentDescription = null, // Content description can be added here
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterHorizontally)
        )

        // Search bar
        SearchBar(
            query = searchText,
            onQueryChange = {},
            onSearch = {},
            placeholder = {
                Text(text = "Search name or type")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            trailingIcon = {},
            content = {},
            active = true,
            onActiveChange = {},
            tonalElevation = 0.dp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display Pokemon list
        if (isLoading && pokemonList.isEmpty()) {
            // Show loading spinner
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            // Show Pokemon list
            LazyColumn {
                itemsIndexed(pokemonList) { index, pokemon ->

                    PokemonItem(pokemon = pokemon)

                    if (index == pokemonList.size - 1 && !isLoading) {
                        // Load more items when reaching the end of the list
                        loadMorePokemon()
                    }

                }
            }
        }
    }
}

@Composable
fun loadMorePokemon() {
    // Simulate loading more Pokemon
}

@Preview
@Composable
fun PreviewPokemonSearchScreen() {
    PokemonSearchScreen()
}



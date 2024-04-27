package com.khalidb91.pokemonbox.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khalidb91.pokemonbox.presentation.ui.component.PokemonItem
import com.khalidb91.pokemonbox.presentation.ui.component.SearchBarInputField
import com.khalidb91.pokemonbox.presentation.ui.theme.PokemonBoxTheme

@Composable
fun MainScreen() {

    val viewModel = MainViewModel()

    val searchText by viewModel.searchText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val pokemonList by viewModel.pokemonList.collectAsState()

    val logoText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = Thin)) { append("Pokemon") }
        withStyle(style = SpanStyle(fontWeight = Bold)) { append("Box") }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = logoText,
                    fontSize = 32.sp,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    SearchBarInputField(
                        query = searchText,
                        onQueryChange = viewModel::onSearchTextChange,
                        onSearch = viewModel::onSearchTextChange,
                        onActiveChange = {},
                        placeholder = { Text(text = "Search name or type") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                tint = MaterialTheme.colorScheme.onSurface,
                                contentDescription = null
                            )
                        }
                    )
                }

            }
        },
        content = { innerPadding ->

            Column(modifier = Modifier.padding(innerPadding)) {

                if (isLoading && pokemonList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                    ) {
                        itemsIndexed(pokemonList) { index, pokemon ->

                            PokemonItem(pokemon = pokemon)
                            HorizontalDivider(color = Color.Gray, thickness = 1.dp)


                            if (index == pokemonList.size - 1 && !isLoading) {
                                // Load more items when reaching the end of the list
                                loadMorePokemon()
                            }

                        }
                    }
                }

            }

        }
    )

}

@Composable
fun loadMorePokemon() {
    // Simulate loading more Pokemon
}

@Preview
@Composable
fun MainScreenPreview() {
    PokemonBoxTheme {
        MainScreen()
    }
}



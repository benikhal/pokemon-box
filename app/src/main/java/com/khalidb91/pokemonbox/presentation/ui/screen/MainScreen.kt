package com.khalidb91.pokemonbox.presentation.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Thin
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.presentation.ui.component.PokemonItem
import com.khalidb91.pokemonbox.presentation.ui.component.SearchBarInputField
import com.khalidb91.pokemonbox.presentation.ui.theme.PokemonBoxTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {

    val viewModel = MainViewModel()

    val searchText by viewModel.searchText.collectAsState()

    val pokemonPagingItems: LazyPagingItems<Pokemon> =
        viewModel.productsSearchResults.collectAsLazyPagingItems()

    val logoText = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = Thin)) { append("Pokemon") }
        withStyle(style = SpanStyle(fontWeight = Bold)) { append("Box") }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = logoText,
                    fontSize = 32.sp,
                )
            }
        },
        content = { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                stickyHeader {
                    Box(
                        modifier = Modifier.background(
                            brush = Brush.verticalGradient(
                                startY = 50f,
                                endY = 250f,
                                colors = listOf(
                                    MaterialTheme.colorScheme.surface,
                                    Color.Transparent
                                )
                            )
                        )
                    )
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(shape = RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                        ) {
                            SearchBarInputField(
                                query = searchText,
                                onQueryChange = viewModel::setSearch,
                                onSearch = viewModel::setSearch,
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
                }

                items(pokemonPagingItems.itemCount) { index ->
                    PokemonItem(pokemon = pokemonPagingItems[index]!!)
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color.Gray,
                        thickness = 1.dp
                    )
                }

                pokemonPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val error = pokemonPagingItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier.fillParentMaxSize(),
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val error = pokemonPagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorMessage(
                                    modifier = Modifier,
                                    message = error.error.localizedMessage!!,
                                    onClickRetry = { retry() })
                            }
                        }
                    }
                }

            }

        }

    )

}

@Preview
@Composable
fun MainScreenPreview() {
    PokemonBoxTheme {
        MainScreen()
    }
}


@Composable
fun ErrorMessage(
    modifier: Modifier,
    message: String,
    onClickRetry: () -> Unit
) {
    Column(modifier = modifier.padding(25.dp)) {
        Text(text = message, textAlign = TextAlign.Center)
        Button(modifier = Modifier.align(Alignment.CenterHorizontally), onClick = onClickRetry) {
            Text(text = "Retry")
        }
    }
}

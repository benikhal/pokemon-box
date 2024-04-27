package com.khalidb91.pokemonbox.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.presentation.ui.theme.PokemonBoxTheme

@Composable
private fun ChipItem(label: String) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = label.capitalize(Locale.current),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Preview
@Composable
fun ChipItemPreview() {
    PokemonBoxTheme {
        ChipItem("Hello!")
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {

    val imagePainter = rememberAsyncImagePainter(model = pokemon.image)

    Row(modifier = modifier.padding(8.dp)) {

        Image(
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.CenterVertically),
            painter = imagePainter,
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = pokemon.name.capitalize(Locale.current),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            FlowRow(horizontalArrangement = Arrangement.Absolute.spacedBy(8.dp)) {
                pokemon.types.forEach { ChipItem(it) }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pokemon.description.capitalize(Locale.current),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

        }

    }

}

@Preview
@Composable
fun PokemonItemPreview() {
    PokemonBoxTheme {
        PokemonItem(
            pokemon = Pokemon(
                name = "pikachu",
                types = listOf("Grass", "Venom"),
                description = "Pikachu is an Electric-type Pokémon. It is well known for its lightning powers.",
                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"
            )
        )
    }
}

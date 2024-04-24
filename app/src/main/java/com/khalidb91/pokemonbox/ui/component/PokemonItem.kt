package com.khalidb91.pokemonbox.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.khalidb91.pokemonbox.R
import com.khalidb91.pokemonbox.domain.model.Pokemon
import com.khalidb91.pokemonbox.domain.model.PokemonType

@Composable
fun PokemonItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon
) {
    Row(modifier = modifier.padding(8.dp)) {

        // Pokemon image
        Image(
            painter = painterResource(id = R.drawable.placeholder_pokemon), // Placeholder image resource
            contentDescription = null, // Content description can be added here
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Pokemon details
        Column {

            Text(
                text = pokemon.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = pokemon.type.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = pokemon.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewPokemonItem() {
    PokemonItem(
        pokemon = Pokemon(
            name = "Pikachu",
            type = PokemonType.BUG,
            description = "Pikachu is an Electric-type Pokémon. It is well known for its lightning powers.",
            image = ""
        )
    )
}

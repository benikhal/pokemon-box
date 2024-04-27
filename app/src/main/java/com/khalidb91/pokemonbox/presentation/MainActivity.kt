package com.khalidb91.pokemonbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import com.khalidb91.pokemonbox.presentation.ui.screen.MainScreen
import com.khalidb91.pokemonbox.presentation.ui.theme.PokemonBoxTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Box(Modifier.safeDrawingPadding()) {
                PokemonBoxTheme {
                    MainScreen()
                }
            }
        }

    }

}
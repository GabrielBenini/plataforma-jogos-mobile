package com.gabrielbenini.agigames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.gabrielbenini.agigames.navigation.NavGraph
import com.gabrielbenini.agigames.ui.theme.AgiGamesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgiGamesTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}



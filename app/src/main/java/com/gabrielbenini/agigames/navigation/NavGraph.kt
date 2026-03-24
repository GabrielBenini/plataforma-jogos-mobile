package com.gabrielbenini.agigames.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gabrielbenini.agigames.feature.tictactoe.TicTacToeScreen
import com.gabrielbenini.agigames.feature.tictactoe.TicTacToeViewModel
import com.gabrielbenini.agigames.feature.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME){
            HomeScreen(navController = navController)
        }
        composable(Routes.TICTACTOE){
            val ticTacToeViewModel: TicTacToeViewModel = viewModel()
            TicTacToeScreen(ticTacToeViewModel, navController)
        }
    }
}
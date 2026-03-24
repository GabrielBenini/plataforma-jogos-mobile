package com.gabrielbenini.agigames.feature.tictactoe

data class TicTacToeUiState(
    val board: List<List<String>> = emptyList(),
    val currentPlayer: String = "x",
    val winningText: String? = null
)

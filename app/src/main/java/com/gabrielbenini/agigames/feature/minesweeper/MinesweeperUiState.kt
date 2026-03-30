package com.gabrielbenini.agigames.feature.minesweeper

import com.gabrielbenini.agigames.feature.minesweeper.model.Board
import com.gabrielbenini.agigames.feature.minesweeper.model.enums.State

data class MinesweeperUiState(
    val board: Board = Board(),
    val state: State = State.PLAYING
)

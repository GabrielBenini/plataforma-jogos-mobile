package com.gabrielbenini.agigames.feature.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.collections.forEachIndexed
import kotlin.collections.listOf

class TicTacToeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(TicTacToeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        clear()
    }

    fun clear() {
        _uiState.value = uiState.value.copy(
            board = listOf(
                listOf("", "", ""),
                listOf("", "", ""),
                listOf("", "", "")
            ),
            winningText = null
        )
    }

    fun onSwitchPlayer() {
        _uiState.value = uiState.value.copy(
            currentPlayer = if (uiState.value.currentPlayer == "x") "o" else "x"
        )
    }

    fun onClickButton(x: Int, y: Int) {
        val currentBoard = uiState.value.board.map { it.toMutableList() }.toMutableList()
        if (currentBoard[x][y] == ""){
            currentBoard[x][y] = uiState.value.currentPlayer
            _uiState.value = uiState.value.copy(
                board = currentBoard.map { it.toList() }
            )
            onSwitchPlayer()
            changeWinningText()
        }
    }

    fun checkWinner(): String? {
        fun checkLine(vararg cells: String): String? {
            return if (cells.first().isNotEmpty() && cells.all { it == cells.first() }) {
                cells.first()
            } else null
        }

        val board = uiState.value.board
        board.forEach { row ->
            checkLine(row[0], row[1], row[2])?.let { return it }
        }
        for (col in 0..2) {
            checkLine(board[0][col], board[1][col], board[2][col])?.let { return it }
        }
        checkLine(board[0][0], board[1][1], board[2][2])?.let { return it }
        checkLine(board[0][2], board[1][1], board[2][0])?.let { return it }

        if (board.all{ row -> row.all{ it != "" } }) return "deu velha!"

        return null
    }

    fun changeWinningText(){
        if (checkWinner() != null){
            _uiState.value = uiState.value.copy(
                winningText = when (val w = checkWinner()) {
                    "x", "o" -> "$w ganhou!"
                    "deu velha!" -> "deu velha!"
                    else -> null
                }
            )
        }
    }
}

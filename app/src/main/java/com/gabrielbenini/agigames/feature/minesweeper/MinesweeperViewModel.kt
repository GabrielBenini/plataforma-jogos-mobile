package com.gabrielbenini.agigames.feature.minesweeper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielbenini.agigames.feature.minesweeper.model.Board
import com.gabrielbenini.agigames.feature.minesweeper.model.Cell
import com.gabrielbenini.agigames.feature.minesweeper.model.enums.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MinesweeperViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MinesweeperUiState())
    val uiState = _uiState.asStateFlow()

    init {
        reset()
    }

    private fun clearedBoard() = Board(
        List(10) { x -> List(10) { y -> Cell(clicked = false, x = x, y = y) } }
    )

    private fun addBombs(bombCount: Int = 10): Board {
        val positions = mutableSetOf<Pair<Int, Int>>()
        while (positions.size < bombCount) {
            positions.add(Random.nextInt(0, 10) to Random.nextInt(0, 10))
        }
        val newBoard = clearedBoard().board.mapIndexed { row, line ->
            line.mapIndexed { col, cell ->
                if (positions.contains(row to col)) {
                    cell.copy(hasBomb = true)
                } else cell
            }
        }
        return Board(newBoard)
    }

    private fun setNeighborBombs(): Board {
        val boardWithBombs = addBombs()
        val newBoard = boardWithBombs.board.mapIndexed { _, line ->
            line.mapIndexed { _, cell ->
                if (!cell.hasBomb) {
                    cell.copy(neighborBombs = calcCellNumber(cell, boardWithBombs.board))
                } else cell
            }
        }
        return Board(newBoard)
    }

    fun reset() {
        _uiState.value = _uiState.value.copy(
            board = setNeighborBombs(),
            state = State.PLAYING
        )
    }

    fun onCellClick(row: Int, col: Int) {
        val board = uiState.value.board.board
        val cell = board[row][col]

        if (cell.clicked || cell.flagged || uiState.value.state != State.PLAYING) return

        val newBoard = board.mapIndexed { r, line ->
            if (r == row) {
                line.mapIndexed { c, cell ->
                    if (c == col) cell.copy(clicked = true) else cell
                }
            } else line
        }

        _uiState.value = _uiState.value.copy(board = Board(newBoard))

        if (cell.neighborBombs == 0 && !cell.hasBomb) {
            viewModelScope.launch(Dispatchers.Default) {
                val finalBoard = revealBlankArea(newBoard)
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value.copy(
                        board = Board(finalBoard),
                    )
                }
            }
        }
        _uiState.value = uiState.value.copy(
            state = checkState()
        )
    }

    private fun revealBlankArea(initialBoard: List<List<Cell>>): List<List<Cell>> {
        val toReveal = mutableSetOf<Pair<Int, Int>>()
        val visited = mutableSetOf<Pair<Int, Int>>()

        fun flood(x: Int, y: Int, board: List<List<Cell>>) {
            val key = x to y

            if (x !in 0..9 || y !in 0..9) return
            if (visited.contains(key)) return

            visited.add(key)
            val cell = board[x][y]

            if (cell.hasBomb || cell.flagged) return

            toReveal.add(key)

            if (cell.neighborBombs == 0) {
                flood(x - 1, y - 1, board)
                flood(x - 1, y, board)
                flood(x - 1, y + 1, board)
                flood(x, y - 1, board)
                flood(x, y + 1, board)
                flood(x + 1, y - 1, board)
                flood(x + 1, y, board)
                flood(x + 1, y + 1, board)
            }
        }

        initialBoard.forEachIndexed { row, line ->
            line.forEachIndexed { col, cell ->
                if (cell.clicked && cell.neighborBombs == 0 && !cell.hasBomb) {
                    flood(row, col, initialBoard)
                }
            }
        }

        return initialBoard.mapIndexed { r, line ->
            line.mapIndexed { c, cell ->
                if (toReveal.contains(r to c)) {
                    cell.copy(clicked = true)
                } else cell
            }
        }
    }

    private fun calcCellNumber(cell: Cell, board: List<List<Cell>>) =
        getNeighbors(cell, board).count { it.hasBomb }

    fun onCellHold(row: Int, col: Int) {
        val newBoard = uiState.value.board.board.mapIndexed { r, line ->
            if (r == row) {
                line.mapIndexed { c, cell ->
                    if (c == col && !cell.clicked && uiState.value.state == State.PLAYING) cell.copy(flagged = !cell.flagged) else cell
                }
            } else {
                line
            }
        }
        _uiState.value = _uiState.value.copy(board = Board(newBoard))
    }

    fun checkState() = when {
        bombsClicked() -> State.LOSS
        checkWin() -> State.WIN
        else -> State.PLAYING
    }

    fun bombsClicked(): Boolean {
        uiState.value.board.board.forEach { line ->
            line.forEach { cell ->
                if (cell.clicked && cell.hasBomb) {
                    return true
                }
            }
        }
        return false
    }

    fun checkWin(): Boolean {
        uiState.value.board.board.forEach { line ->
            line.forEach { cell ->
                if (!cell.clicked && !cell.hasBomb) {
                    return false
                }
            }
        }
        return true
    }

    private fun getNeighbors(cell: Cell, board: List<List<Cell>>): List<Cell> {
        val x = cell.x
        val y = cell.y
        val limit = board.size - 1
        val neighbors = mutableListOf<Cell>()

        if (x > 0 && y > 0) neighbors.add(board[x - 1][y - 1])
        if (x > 0) neighbors.add(board[x - 1][y])
        if (x > 0 && y < limit) neighbors.add(board[x - 1][y + 1])
        if (y > 0) neighbors.add(board[x][y - 1])
        if (y < limit) neighbors.add(board[x][y + 1])
        if (x < limit && y > 0) neighbors.add(board[x + 1][y - 1])
        if (x < limit) neighbors.add(board[x + 1][y])
        if (x < limit && y < limit) neighbors.add(board[x + 1][y + 1])

        return neighbors
    }
}
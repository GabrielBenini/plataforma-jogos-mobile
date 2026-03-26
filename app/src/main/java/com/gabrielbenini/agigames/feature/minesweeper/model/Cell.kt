package com.gabrielbenini.agigames.feature.minesweeper.model

data class Cell(
    val hasBomb: Boolean = false,
    val clicked: Boolean = false,
    val flagged: Boolean = false,
    val neighborBombs: Int = 0,
    val x: Int = -1,
    val y: Int = -1,
)

package com.gabrielbenini.agigames.feature.minesweeper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabrielbenini.agigames.feature.minesweeper.model.Cell

@Composable
fun MinesweeperCell(
    onClick: () -> Unit,
    onHold: () -> Unit,
    cell: Cell,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .border(3.dp, Color.DarkGray)
            .background(if (cell.clicked) Color.White else Color.Gray)
            .size(40.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onHold() }
                )
            },
    ) {
        Text(
            text = when {
                cell.hasBomb && cell.clicked -> "o"
                cell.neighborBombs != 0 && cell.clicked -> cell.neighborBombs.toString()
                cell.flagged -> "f"
                else -> ""
            },
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )

    }
}

@Preview
@Composable
private fun MinesweeperCellNotClickedPreview() {
    MinesweeperCell(
        onClick = {},
        onHold = {},
        cell = Cell(),
    )
}

@Preview
@Composable
private fun MinesweeperCellClickedPreview() {
    MinesweeperCell(
        onClick = {},
        onHold = {},
        cell = Cell(clicked = true),
    )
}
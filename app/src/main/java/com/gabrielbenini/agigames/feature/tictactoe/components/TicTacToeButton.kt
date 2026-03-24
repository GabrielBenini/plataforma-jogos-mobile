package com.gabrielbenini.agigames.feature.tictactoe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TicTacToeButton(
    x: Int,
    y: Int,
    onClick: () -> Unit,
    text: String = "",
    enabled: Boolean = true
) {
    Box(
        modifier = Modifier
            .borderSides(
                width = 3.dp,
                color = Color.Blue,
                top = y == 2,
                bottom = y == 0,
                start = x == 2,
                end = x == 0,
            )
            .clickable { if (enabled) onClick() }
            .size(120.dp)
        ,
    ){
        Text(
            text,
            modifier = Modifier.align(alignment = Alignment.Center),
            style = MaterialTheme.typography.displayMedium
        )
    }
}

fun Modifier.borderSides(
    width: Dp,
    color: Color,
    top: Boolean = false,
    bottom: Boolean = false,
    start: Boolean = false,
    end: Boolean = false
) = this.drawBehind {
    val strokeWidth = width.toPx()

    if (top) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            strokeWidth = strokeWidth
        )
    }

    if (bottom) {
        drawLine(
            color = color,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth
        )
    }

    if (start) {
        drawLine(
            color = color,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = strokeWidth
        )
    }

    if (end) {
        drawLine(
            color = color,
            start = Offset(size.width, 0f),
            end = Offset(size.width, size.height),
            strokeWidth = strokeWidth
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TicTacToeButtonPreview() {
    TicTacToeButton(
        x = 0,
        y = 0,
        onClick = {},
        text = "o",
    )
}

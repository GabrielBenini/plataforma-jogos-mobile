package com.gabrielbenini.agigames.feature.minesweeper

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbenini.agigames.feature.minesweeper.components.MinesweeperCell
import com.gabrielbenini.agigames.feature.minesweeper.model.enums.State
import kotlin.collections.forEachIndexed

@Composable
fun MinesweeperScreen(
    viewModel: MinesweeperViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val board = uiState.board.board

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ){
            TextButton(
                onClick = {navController.popBackStack()}
            ) {
                Text(
                    "x",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
        board.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { colIndex, cell ->
                    MinesweeperCell(
                        onClick = { if (!cell.clicked && !cell.flagged) viewModel.onCellClick(rowIndex, colIndex) },
                        onHold = { if (!cell.clicked) viewModel.onCellHold(rowIndex, colIndex) },
                        cell = board[rowIndex][colIndex],
                    )
                }
            }
        }
        Spacer(Modifier.size(30.dp))
        Button(
            onClick = { viewModel.reset() }
        ) {
            Text("reset")
        }
        Text(
            text = when(uiState.state){
                State.WIN -> "voce ganhou"
                State.LOSS -> "voce perdeu"
                State.PLAYING -> ""
            }
        )
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
private fun MinesweeperScreenPreview() {
    MinesweeperScreen(MinesweeperViewModel(), rememberNavController())
}
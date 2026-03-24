package com.gabrielbenini.agigames.feature.tictactoe

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
import com.gabrielbenini.agigames.feature.tictactoe.components.TicTacToeButton

@Composable
fun TicTacToeScreen(
    viewModel: TicTacToeViewModel,
    navController: NavHostController,
    modifier: Modifier
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val board = uiState.board
    val winningText = uiState.winningText
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
                row.forEachIndexed { colIndex, value ->
                    TicTacToeButton(
                        x = colIndex,
                        y = rowIndex,
                        onClick = {viewModel.onClickButton(rowIndex, colIndex)},
                        text = value,
                        enabled = uiState.winningText == null
                    )
                }
            }
        }
        Spacer(Modifier.size(30.dp))
        Button(
            onClick = { viewModel.clear() }
        ){
            Text("reset")
        }
        Spacer(Modifier.size(60.dp))
        Text(
            text = winningText ?: "",
            style = MaterialTheme.typography.headlineLarge)
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
private fun TicTacToeScreenPreview() {
    TicTacToeScreen(viewModel = TicTacToeViewModel(), rememberNavController(), Modifier)
}
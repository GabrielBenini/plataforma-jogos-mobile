package com.gabrielbenini.agigames.feature.select

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SelectScreen(
    navController : NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            item{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Escolha que jogo vc quer",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    TextButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Text(
                            "x",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.Black
                        )
                    }
                }
            }
            items(gamesList){
                Button(
                    onClick = { navController.navigate(it.second) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ){
                    Text(it.first)
                }
            }
        }
    }
}
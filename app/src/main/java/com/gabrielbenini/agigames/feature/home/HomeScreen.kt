package com.gabrielbenini.agigames.feature.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gabrielbenini.agigames.R
import com.gabrielbenini.agigames.navigation.Routes

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val infiniteTransition = rememberInfiniteTransition(label = "gradient")
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    val gradientColors = listOf(
        Color(0xFFB07CF1),
        Color(0xFFE97EB5),
        Color(0xFFF08599),
        Color(0xFFB07CF1)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF0F0F1A),
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.bem_vindo_ao),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFFA855F7).copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.agigames),
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(offset, 0f),
                        end = Offset(offset + 300f, 300f),
                        tileMode = TileMode.Repeated
                    )
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = stringResource(R.string.sua_divers_o_come_a_aqui),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFFA855F7).copy(alpha = 0.5f),
                modifier = Modifier
                    .padding(bottom = 36.dp)
            )

            Button(
                onClick = { navController.navigate(Routes.TICTACTOE) },
                contentPadding = PaddingValues(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(12.dp),
                        spotColor = Color(0xFFA855F7).copy(alpha = 0.3f)
                    )
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFA855F7),
                                    Color(0xFF8B5CF6),
                                    Color(0xFF6366F1)
                                )
                            )
                        )
                        .padding(horizontal = 32.dp, vertical = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "COMEÇAR A JOGAR",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(Modifier, rememberNavController())
}
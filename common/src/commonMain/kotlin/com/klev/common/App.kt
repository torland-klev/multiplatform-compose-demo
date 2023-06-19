package com.klev.common

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun App(initialState: TikTakToeState = TikTakToeService.new()) {
    Box(modifier = Modifier.fillMaxSize().background(Color(116, 115, 189, 255)), contentAlignment = Alignment.Center) {
        TikTakToe(initialState)
    }
}

@Composable
private fun TikTakToe(initialState: TikTakToeState) {
    var state by remember { mutableStateOf(initialState) }
    var xTurn by remember { mutableStateOf(true) }
    var bannerText by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.clip(RoundedCornerShape(10.dp)).background(Color(227, 227, 242, 255)).size(540.dp)
    ) {
        items(
            items = state.squares.sortedWith(compareBy<Square> { it.y }.thenBy { it.x }),
            key = { "${it.x}${it.y}" }) { square ->
            Box(
                modifier = Modifier
                    .border(2.dp, Color(186, 185, 222, 255))
                    .height(180.dp)
                    .clickable {
                        if (bannerText.isEmpty() && square.symbol == Symbol.EMPTY) {
                            state = TikTakToeService.update(state, square, xTurn)
                            xTurn = xTurn.not()
                            bannerText = TikTakToeService.bannerText(state)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                AnimatedVisibility(
                    visible = square.symbol != Symbol.EMPTY, exit = fadeOut(), enter = fadeIn(
                        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
                    )
                ) {
                    Text(square.symbol.symbol, textAlign = TextAlign.Center, fontSize = 80.sp)
                }
            }
        }
    }
    AnimatedVisibility(
        visible = bannerText.isNotEmpty(),
        enter = slideInHorizontally() + fadeIn(),
        exit = slideOutHorizontally() + fadeOut()
    ) {
        Box(
            Modifier.fillMaxWidth().height(140.dp).background(Color(214, 216, 255, 255)),
            contentAlignment = Alignment.Center
        ) {
            Text(bannerText, fontSize = 40.sp, fontStyle = FontStyle.Italic)
        }
    }
    LaunchedEffect(bannerText) {
        if (bannerText.isNotEmpty()) {
            delay(2000)
            state = TikTakToeService.new()
            bannerText = ""
            xTurn = true
        }
    }
}

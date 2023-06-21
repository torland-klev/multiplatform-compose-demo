package com.klev.common

import ResultsBanner
import SquareAnimation
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import appModifier
import boardModifier
import kotlinx.coroutines.delay
import squareModifier

@Composable
fun App() {
    Box(modifier = appModifier, contentAlignment = Alignment.Center) {
        TikTakToe()
    }
}

@Composable
private fun TikTakToe() {
    var state by remember { mutableStateOf(TikTakToeService.new()) }
    var xTurn by remember { mutableStateOf(true) }
    var bannerText by remember { mutableStateOf("") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = boardModifier
    ) {
        items(
            items = TikTakToeService.sorted(state.squares),
            key = { "${it.x}${it.y}" }) {
            BoardSquare(square = it, enabled = bannerText.isEmpty() && it.symbol == Symbol.EMPTY) {
                state = TikTakToeService.update(state, it, xTurn)
                xTurn = xTurn.not()
                bannerText = TikTakToeService.bannerText(state)
            }
        }
    }
    ResultsBanner(bannerText)
    LaunchedEffect(bannerText) {
        if (bannerText.isNotEmpty()) {
            delay(2000)
            state = TikTakToeService.new()
            bannerText = ""
            xTurn = true
        }
    }
}

@Composable
fun BoardSquare(square: Square, enabled: Boolean, onClick: (Square) -> Unit) {
    Box(
        modifier = squareModifier.clickable(enabled = enabled) { onClick(square) },
        contentAlignment = Alignment.Center
    ) {
        SquareAnimation(square) {
            Text(square.symbol.symbol, textAlign = TextAlign.Center, fontSize = 80.sp)
        }
    }
}

package com.klev.common


data class TikTakToeState(
    val squares: List<Square>
)

data class Square(
    val x: Int,
    val y: Int,
    val symbol: Symbol
)

enum class Symbol(val symbol: String) {
    CROSS("X"),
    CIRCLE("O"),
    EMPTY("")
}
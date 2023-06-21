package com.klev.common

object TikTakToeService {

    fun new() = TikTakToeState(
        (0..8).map { index ->
            Square(
                x = index % 3,
                y = index / 3,
                symbol = Symbol.EMPTY
            )
        }
    )

    private fun checkState(state: TikTakToeState): BoardState {
        val rows = checkRows(state)
        val columns = checkColumns(state)
        val checkDiagonalUpDown = checkDiagonalUpDown(state)
        val checkDiagonalDownUp = checkDiagonalDownUp(state)
        return if (rows == BoardState.VICTORY_X || columns == BoardState.VICTORY_X || checkDiagonalUpDown == BoardState.VICTORY_X || checkDiagonalDownUp == BoardState.VICTORY_X) BoardState.VICTORY_X
        else if (rows == BoardState.VICTORY_O || columns == BoardState.VICTORY_O || checkDiagonalUpDown == BoardState.VICTORY_O || checkDiagonalDownUp == BoardState.VICTORY_O) BoardState.VICTORY_O
        else if (state.squares.any { it.symbol == Symbol.EMPTY }) BoardState.IN_PROGRESS
        else BoardState.DRAW
    }


    private fun checkRows(state: TikTakToeState) = state.squares.groupBy { it.y }.check()

    private fun checkColumns(state: TikTakToeState) = state.squares.groupBy { it.x }.check()

    private fun checkDiagonalUpDown(state: TikTakToeState) = state.squares.filter { it.x == it.y }.check()

    private fun checkDiagonalDownUp(state: TikTakToeState) = state.squares.filter { it.x + it.y == 2 }.check()
    private fun List<Square>.check() =
        if (this.all { it.symbol == Symbol.CROSS }) BoardState.VICTORY_X else if (this.all { it.symbol == Symbol.CIRCLE }) BoardState.VICTORY_O else if (this.any { it.symbol == Symbol.EMPTY }) BoardState.IN_PROGRESS else BoardState.DRAW

    private fun Map<Int, List<Square>>.check() =
        if (this.any { it.value.all { it.symbol == Symbol.CROSS } }) BoardState.VICTORY_X else if (this.any { it.value.all { it.symbol == Symbol.CIRCLE } }) BoardState.VICTORY_O else if (this.any { it.value.any { it.symbol == Symbol.EMPTY } }) BoardState.IN_PROGRESS else BoardState.DRAW

    fun bannerText(state: TikTakToeState) = when (checkState(state)) {
        BoardState.DRAW -> "DRAW"
        BoardState.IN_PROGRESS -> ""
        BoardState.VICTORY_X -> "Congratulations, X!"
        BoardState.VICTORY_O -> "Congratulations, O!"
    }

    fun update(state: TikTakToeState, square: Square, xTurn: Boolean): TikTakToeState =
        state.copy(squares = state.squares.filterNot { it == square } + square.copy(symbol = if (xTurn) Symbol.CROSS else Symbol.CIRCLE))

    fun sorted(squares: List<Square>) = squares.sortedWith(compareBy<Square> { it.y }.thenBy { it.x })

    enum class BoardState {
        DRAW,
        IN_PROGRESS,
        VICTORY_X,
        VICTORY_O,
    }

}
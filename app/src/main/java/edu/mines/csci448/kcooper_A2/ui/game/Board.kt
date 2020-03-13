package edu.mines.csci448.kcooper_A2.ui.game

class Board {
    val board = Array(3) { arrayOfNulls<String>(3) }
    var winner: String? = null

    val gameOver: Boolean
        get() = hasPlayerWon("W") || hasPlayerWon("W") || isBoardFull()

    fun hasPlayerWon(player: String): Boolean {
        if (board[0][0] == board[1][1] &&
            board[0][0] == board[2][2] &&
            board[0][0] == player ||
            board[0][2] == board[1][1] &&
            board[0][2] == board[2][0] &&
            board[0][2] == player
        ) {
            winner = player
            return true
        }

        for (i in board.indices) {
            if (
                board[i][0] == board[i][1] &&
                board[i][0] == board[i][2] &&
                board[i][0] == player ||
                board[0][i] == board[1][i] &&
                board[0][i] == board[2][i] &&
                board[0][i] == player
            ) {
                winner = player
                return true
            }
        }

        return false
    }
}
package edu.mines.csci448.kcooper_A2.ui.history

import androidx.lifecycle.ViewModel
import edu.mines.csci448.kcooper_A2.data.Game
import edu.mines.csci448.kcooper_A2.data.GameRepository

class GameHistoryViewModel(private val gameRepository: GameRepository) : ViewModel() {
    val gameHistoryLiveData = gameRepository.getGames()

    fun addGame(game: Game) {
        gameRepository.addGame(game)
    }
}
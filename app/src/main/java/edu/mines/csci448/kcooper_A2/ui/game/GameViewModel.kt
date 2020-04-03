package edu.mines.csci448.kcooper_A2.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import edu.mines.csci448.kcooper_A2.data.Game
import edu.mines.csci448.kcooper_A2.data.GameRepository
import java.util.*

class GameViewModel(private val gameRepository: GameRepository): ViewModel() {
    private val gameIdLiveData = MutableLiveData<UUID>()

    var gameLiveData: LiveData<Game?> =
        Transformations.switchMap(gameIdLiveData) { gameId ->
            gameRepository.getGame(gameId)
        }

    fun addGame(game: Game){
        gameRepository.addGame(game)
    }
}
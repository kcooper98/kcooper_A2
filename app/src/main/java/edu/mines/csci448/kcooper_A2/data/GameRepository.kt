package edu.mines.csci448.kcooper_A2.data

import android.content.Context
import androidx.lifecycle.LiveData
import java.util.*
import java.util.concurrent.Executors

class GameRepository(private val gameDao: GameDao) {
    private val executor = Executors.newSingleThreadExecutor()

    fun getGames(): LiveData<List<Game>> = gameDao.getGames()

    fun getGame(id: UUID): LiveData<Game?> = gameDao.getGame(id)

    fun wipeGames() {
        executor.execute {
            gameDao.wipeGames()
        }
    }

    fun addGame(game: Game) {
        executor.execute {
            gameDao.addGame(game)
        }
    }

    companion object {
        private var instance: GameRepository? = null
        fun getInstance(context: Context): GameRepository? {
            return instance ?: let {
                if (instance == null) {
                    val database = GameDatabase.getInstance(context)
                    instance = GameRepository(database.gameDao())
                }
                return instance
            }
        }
    }
}
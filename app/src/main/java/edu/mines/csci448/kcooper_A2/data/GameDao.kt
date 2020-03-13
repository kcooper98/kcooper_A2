package edu.mines.csci448.kcooper_A2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getGames(): LiveData<List<Game>>

    @Query("SELECT * FROM game WHERE id=(:id)")
    fun getGame(id: UUID): LiveData<Game?>

    @Insert
    fun addGame(game: Game)

    @Query("DELETE FROM game")
    fun wipeGames()
}
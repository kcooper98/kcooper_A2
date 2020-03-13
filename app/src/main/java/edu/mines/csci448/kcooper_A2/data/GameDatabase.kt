package edu.mines.csci448.kcooper_A2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Game::class], version = 1)
@TypeConverters(GameTypeConverters::class)
abstract class GameDatabase : RoomDatabase() {
    companion object {
        private var instance: GameDatabase? = null
        private const val DATABASE_NAME = "game-database"

        fun getInstance(context: Context): GameDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                GameDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }

    abstract fun gameDao(): GameDao
}
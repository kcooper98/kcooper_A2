package edu.mines.csci448.kcooper_A2.ui.game

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.mines.csci448.kcooper_A2.data.GameRepository

class GameViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor((GameRepository::class.java))
            .newInstance(GameRepository.getInstance(context))
    }
}
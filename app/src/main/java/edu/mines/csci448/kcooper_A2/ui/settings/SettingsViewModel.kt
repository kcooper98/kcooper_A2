package edu.mines.csci448.kcooper_A2.ui.settings

import androidx.lifecycle.ViewModel
import edu.mines.csci448.kcooper_A2.data.GameRepository

class SettingsViewModel(private val gameRepository: GameRepository): ViewModel() {
    fun wipeGames() {
        gameRepository.wipeGames()
    }
}
package edu.mines.csci448.kcooper_A2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.mines.csci448.kcooper_A2.ui.game.GameFragment
import edu.mines.csci448.kcooper_A2.ui.HistoryFragment
import edu.mines.csci448.kcooper_A2.ui.SettingsFragment
import edu.mines.csci448.kcooper_A2.ui.WelcomeFragment

class MainActivity : AppCompatActivity(), WelcomeFragment.Callbacks {
    override fun startGame() {
        val fragment = GameFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun startHistory() {
        val fragment = HistoryFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun startSettings() {
        val fragment = SettingsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = WelcomeFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}

package edu.mines.csci448.kcooper_A2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.mines.csci448.kcooper_A2.ui.game.GameFragment
import edu.mines.csci448.kcooper_A2.ui.history.GameHistoryFragment
import edu.mines.csci448.kcooper_A2.ui.settings.SettingsFragment
import edu.mines.csci448.kcooper_A2.ui.WelcomeFragment

class MainActivity : AppCompatActivity(), WelcomeFragment.Callbacks, GameFragment.Callbacks, GameHistoryFragment.Callbacks {
    override fun startGame() {
        val fragment = GameFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun startHistory() {
        val fragment = GameHistoryFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun startSettings() {
        val fragment =
            SettingsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun returnToWelcome() {
        val fragment = WelcomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(currentFragment == null) {
            val fragment = WelcomeFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

    }

    override fun closeApp() {
        System.exit(0)
    }
}

package edu.mines.csci448.kcooper_A2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import edu.mines.csci448.kcooper_A2.ui.WelcomeFragment

class MainActivity : AppCompatActivity() {

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

package edu.mines.csci448.kcooper_A2.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.mines.csci448.kcooper_A2.R

class GameFragment: Fragment() {
    interface Callbacks {

    }

    private var callbacks: Callbacks? = null

    private val logTag = "448.GameFrag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(logTag, "onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        return view
    }
}
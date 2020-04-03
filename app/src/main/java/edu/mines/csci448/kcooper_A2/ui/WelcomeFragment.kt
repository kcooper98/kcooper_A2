package edu.mines.csci448.kcooper_A2.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import edu.mines.csci448.kcooper_A2.R

class WelcomeFragment: Fragment() {
    interface Callbacks {
        fun startGame()
        fun startHistory()
        fun startSettings()
        fun closeApp()
    }

    private var callbacks: Callbacks? = null

    private val logTag = "448.WelcomeFrag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "oncCreate() called")

        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(logTag, "onOptionsItemSelected() called")
        return when (item.itemId) {
            R.id.new_game_menu_button -> {
                callbacks?.startGame()
                true
            }
            R.id.history_button -> {
                callbacks?.startHistory()
                true
            }
            R.id.settings_button -> {
                callbacks?.startSettings()
                true
            }
            R.id.exit_button -> {
                callbacks?.closeApp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(logTag, "onCreateView() called")
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(logTag, "onCreateOptionsMenu() called")
        inflater.inflate(R.menu.fragment_welcome, menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach() called")
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(logTag, "onDetach() called")
        callbacks=null
    }
}
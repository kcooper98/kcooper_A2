package edu.mines.csci448.kcooper_A2.ui.history

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.mines.csci448.kcooper_A2.R
import edu.mines.csci448.kcooper_A2.data.Game
import edu.mines.csci448.kcooper_A2.ui.WelcomeFragment

class GameHistoryFragment: Fragment() {
    interface Callbacks {
        fun startGame()
        fun startSettings()
        fun closeApp()
    }

    private lateinit var gameRecyclerView: RecyclerView
    private lateinit var adapter: GameHistoryAdapter
    private lateinit var gameHistoryViewModel: GameHistoryViewModel
    private lateinit var scoreTextView: TextView

    private var callbacks: Callbacks? = null

    private val logTag = "448.GameHistoryFrag"

    private fun updateUI(games: List<Game>) {
        adapter = GameHistoryAdapter(games) { }

        gameRecyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate() called")

        val factory = GameHistoryViewModelFactory(requireContext())
        gameHistoryViewModel = ViewModelProvider(this, factory)
            .get(GameHistoryViewModel::class.java)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(logTag, "onCreateOptionsMenu() called")
        inflater.inflate(R.menu.fragment_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(logTag, "onOptionsItemSelected() called")
        return when (item.itemId) {
            R.id.new_game_menu_button -> {
                callbacks?.startGame()
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
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        gameRecyclerView = view.findViewById(R.id.game_recycler_view) as RecyclerView
        gameRecyclerView.layoutManager = LinearLayoutManager(context)
        scoreTextView = view.findViewById(R.id.score_textview)

        updateUI(emptyList())

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(logTag, "onViewCreated() called")
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val wins = prefs.getInt("win_score", 0)
        val losses = prefs.getInt("loss_score", 0)
        val ties = prefs.getInt("tie_score", 0)

        val result = "$wins-$losses-$ties"

        scoreTextView.text = result
        gameHistoryViewModel.gameHistoryLiveData.observe(
            viewLifecycleOwner,
            Observer { games ->
                games?.let {
                    Log.i(logTag, "Got games #${games.size}")
                    updateUI(games)
                }
            }
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach() called")
        callbacks = context as GameHistoryFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(logTag, "onDetach() called")
        callbacks=null
    }
}
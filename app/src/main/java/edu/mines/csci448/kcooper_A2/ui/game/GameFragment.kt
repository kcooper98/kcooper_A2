package edu.mines.csci448.kcooper_A2.ui.game

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import edu.mines.csci448.kcooper_A2.R
import edu.mines.csci448.kcooper_A2.data.Game
import edu.mines.csci448.kcooper_A2.ui.WelcomeFragment
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment: Fragment() {
    interface Callbacks {
        fun returnToWelcome()
    }

    private lateinit var restartButton: Button
    private lateinit var boardGrid: GridLayout
    private lateinit var returnButton: Button
    private lateinit var board: Board
    private lateinit var game: Game
    private lateinit var gameViewModel: GameViewModel

    private var callbacks: Callbacks? = null

    private val logTag = "448.GameFrag"
    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3)}

    private var playerCount: Int? = null
    private var difficulty: String? = null
    private var humanFirst: Boolean? = null
    private var firstNation: String? = null
    private var secondNation: String? = null
    private var activeNation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate() called")

        game = Game()

        val factory = GameViewModelFactory(requireContext())
        gameViewModel = ViewModelProvider(this, factory)
            .get(GameViewModel::class.java)
    }

    private fun updatePreferences() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        playerCount = if(preferences.getBoolean("player_count", false)) 1 else 2
        difficulty = if(preferences.getBoolean("computer_difficulty", true)) "hard" else "easy"
        humanFirst = preferences.getBoolean("human_goes_first", true)
        firstNation = if(preferences.getBoolean("first_nation", true)) "W" else "F"
        secondNation = if(!preferences.getBoolean("first_nation", true)) "W" else "F"
    }

    private fun loadBoard() {
        for (i in boardCells.indices) {
            for(j in boardCells.indices) {
                boardCells[i][j] = ImageButton(requireContext())
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 180
                    height = 180
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.scaleType = ImageView.ScaleType.CENTER_INSIDE
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.boardBackground))
                boardCells[i][j]?.setOnClickListener(CellClickListener(i,j))
                boardGrid.addView(boardCells[i][j])
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(logTag, "onCreateView() called")
        updatePreferences()
        board = Board(firstNation!!, secondNation!!)
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        boardGrid = view.findViewById(R.id.layout_board)
        loadBoard()

        restartButton = view.findViewById(R.id.button_restart)
        returnButton = view.findViewById(R.id.button_return)

        if(!humanFirst!!) {
            computerMove()
            updateUI()
        }
        activeNation = firstNation

        restartButton.setOnClickListener {
            board = Board(firstNation!!, secondNation!!)
            game = Game()
            status_indicator.text = ""
            restartButton.visibility = View.GONE
            returnButton.visibility = View.GONE

            updateUI()
        }

        returnButton.setOnClickListener {
            callbacks?.returnToWelcome()
        }

        return view
    }

    private fun updateUI() {
        Log.d(logTag, "updateUI() called")

        if(playerCount == 2) {
            if (activeNation == "W") {
                status_indicator.text = "Water Nation's turn"
            } else if (activeNation == "F") {
                status_indicator.text = "Fire Nation's turn"
            }
        }

        for (i in board.board.indices) {
            for(j in board.board.indices) {
                when(board.board[i][j]) {
                    board.player -> {
                        boardCells[i][j]?.setImageResource(R.drawable.fire_nation)
                        boardCells[i][j]?.isEnabled = false
                    }
                    board.computer -> {
                        boardCells[i][j]?.setImageResource(R.drawable.water_nation)
                        boardCells[i][j]?.isEnabled = false
                    }
                    else -> {
                        boardCells[i][j]?.setImageResource(0)
                        boardCells[i][j]?.isEnabled = true
                    }
                }
            }
        }
    }

    inner class CellClickListener(private val i: Int, private val j: Int) : View.OnClickListener {
        override fun onClick(v: View?) {
            if(!board.gameOver) {
                // create new cell at clicked index
                val cell = Cell(i,j)

                // place the move for the player
                activeNation?.let { board.placeMove(cell, it) }

                if(playerCount == 1) {
                    computerMove()
                } else if(playerCount == 2) {
                    if(activeNation == firstNation) {
                        activeNation = secondNation
                    } else if(activeNation == secondNation) {
                        activeNation = firstNation
                    }
                }

                updateUI()
            }

            val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val editor = prefs.edit()
            var wins = prefs.getInt("win_score", 0)
            var losses = prefs.getInt("loss_score", 0)
            var ties = prefs.getInt("tie_score", 0)

            // End game if possible
            when {
                board.hasComputerWon() -> {
                    if(secondNation == "W") {
                        status_indicator.text = getString(R.string.fire_win_label)
                        game.winner = "Winner: Water Nation"
                        game.loser = "Loser: Fire Nation"
                    } else if(secondNation == "F") {
                        status_indicator.text = getString(R.string.water_win_label)
                        game.winner = "Winner: Fire Nation"
                        game.loser = "Loser: Water Nation"
                    }
                    losses++
                    gameViewModel.addGame(game)
                    editor.putInt("loss_score", losses)
                    restartButton.visibility = View.VISIBLE
                    returnButton.visibility = View.VISIBLE
                }
                board.hasPlayerWon() -> {
                    if(firstNation == "W"){
                        status_indicator.text = getString(R.string.water_win_label)
                        game.winner = "Winner: Water Nation"
                        game.loser = "Loser: Fire Nation"
                    } else if(firstNation == "F") {
                        status_indicator.text = getString(R.string.fire_win_label)
                        game.winner = "Winner: Fire Nation"
                        game.loser = "Loser: Water Nation"
                    }
                    wins++
                    editor.putInt("win_score", wins)
                    gameViewModel.addGame(game)
                    restartButton.visibility = View.VISIBLE
                    returnButton.visibility = View.VISIBLE
                }
                board.gameOver -> {
                    status_indicator.text = getString(R.string.tie_label)
                    game.winner = "Tie"
                    game.loser = "Tie"
                    ties++
                    editor.putInt("tie_score", ties)
                    gameViewModel.addGame(game)
                    restartButton.visibility = View.VISIBLE
                    returnButton.visibility = View.VISIBLE
                }
            }
            editor.apply()

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(logTag, "onViewCreated() called")

        gameViewModel.gameLiveData.observe(
            viewLifecycleOwner,
            Observer { game ->
                game?.let {
                    this.game = game
                    updateUI()
                }
            }
        )
    }

    private fun computerMove() {
        // get computer's move based on preferences
        if (difficulty == "easy") {
            board.easyMove()
        } else if (difficulty == "hard") {
            board.minimax(0, secondNation!!)
        }

        // perform move for the computer
        board.computerMove?.let {
            board.placeMove(it, secondNation!!)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(logTag, "onAttach() called")
        callbacks = context as GameFragment.Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(logTag, "onDetach() called")
        callbacks=null
    }
}
package edu.mines.csci448.kcooper_A2.ui.game

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
import edu.mines.csci448.kcooper_A2.R
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment: Fragment() {
    interface Callbacks {

    }

    private lateinit var restartButton: Button
    private lateinit var boardGrid: GridLayout

    private var callbacks: Callbacks? = null

    private val logTag = "448.GameFrag"
    private val boardCells = Array(3) { arrayOfNulls<ImageView>(3)}

    var board = Board("F", "W")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(logTag, "onCreate() called")

    }

    private fun loadBoard() {
        for (i in boardCells.indices) {
            for(j in boardCells.indices) {
                boardCells[i][j] = ImageButton(requireContext())
                boardCells[i][j]?.layoutParams = GridLayout.LayoutParams().apply {
                    rowSpec = GridLayout.spec(i)
                    columnSpec = GridLayout.spec(j)
                    width = 250
                    height = 230
                    bottomMargin = 5
                    topMargin = 5
                    leftMargin = 5
                    rightMargin = 5
                }
                boardCells[i][j]?.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
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
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        boardGrid = view.findViewById(R.id.layout_board)

        loadBoard()

        restartButton = view.findViewById(R.id.button_restart)
        restartButton.setOnClickListener {
            board = Board("F", "W")

            turn_indicator.text = ""

            updateUI()
        }

        return view
    }

    private fun updateUI() {
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
                board.placeMove(cell, board.player)

                // calculate minimax for computer's move
                board.minimax(0, board.computer)

                // perform move for the computer
                board.computerMove?.let {
                    board.placeMove(it, board.computer)
                }

                updateUI()
            }

            // End game if possible
            when {
                board.hasComputerWon() -> turn_indicator.text = "Computer Won"
                board.hasPlayerWon() -> turn_indicator.text = "Player Won"
                board.gameOver -> turn_indicator.text = "Nobody wins! Tie."
            }
        }
    }
}
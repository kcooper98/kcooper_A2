package edu.mines.csci448.kcooper_A2.ui.history

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.mines.csci448.kcooper_A2.R
import edu.mines.csci448.kcooper_A2.data.Game
import kotlinx.android.synthetic.main.list_item_game.view.*

class GameHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private lateinit var game: Game

    private val winnerTextView: TextView = itemView.findViewById(R.id.label_winner)
    private val loserTextView: TextView = itemView.findViewById(R.id.label_loser)
    private val dateTextView: TextView = itemView.findViewById(R.id.label_timestamp)

    fun bind(game: Game, clickListener: (Game) -> Unit) {
        this.game = game
        itemView.setOnClickListener { clickListener(this.game) }

        winnerTextView.text = this.game.winner
        loserTextView.text = this.game.loser
        dateTextView.text = this.game.date.toString()
    }
}
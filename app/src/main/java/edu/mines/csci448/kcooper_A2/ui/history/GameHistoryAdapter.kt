package edu.mines.csci448.kcooper_A2.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.mines.csci448.kcooper_A2.R
import edu.mines.csci448.kcooper_A2.data.Game

class GameHistoryAdapter(private val games: List<Game>, private val clickListener: (Game) -> Unit) : RecyclerView.Adapter<GameHolder>() {
    private var currentPosition: Int = 0
        get() = currentPosition
    override fun getItemCount(): Int = games.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_game, parent, false)

        return GameHolder(view)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val game = games[position]
        currentPosition = holder.adapterPosition
        holder.bind(game, clickListener)
    }
}
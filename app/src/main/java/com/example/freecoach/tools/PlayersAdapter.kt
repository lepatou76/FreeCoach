package com.example.freecoach.tools

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freecoach.Player
import com.example.freecoach.PlayersActivity
import com.example.freecoach.R


class PlayersAdapter(private var players: List<Player>, val context: PlayersActivity):
    RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    private val db = PlayersDataBaseHelper(context)

    class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lastNameTextView: TextView = itemView.findViewById(R.id.player_lastname)
        val firstNameTextView: TextView = itemView.findViewById(R.id.player_first_name)
        val bestScoreTextView: TextView = itemView.findViewById(R.id.best_score)
        val totalPlaytimeTextView: TextView = itemView.findViewById(R.id.playtime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_item, parent, false)

        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.lastNameTextView.text = player.lastName
        holder.firstNameTextView.text = player.firstName
        holder.bestScoreTextView.text = player.totalMax.toString()
        holder.totalPlaytimeTextView.text = player.playtime.toString()

    }

    fun refreshData(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}
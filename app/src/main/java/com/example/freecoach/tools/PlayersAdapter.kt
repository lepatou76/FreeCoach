package com.example.freecoach.tools

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freecoach.R


class PlayersAdapter(context: Context):
    RecyclerView.Adapter<PlayersAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val lastNameTextView: TextView = itemView.findViewById(R.id.player_lastname)
        val firstNameTextView: TextView = itemView.findViewById(R.id.player_first_name)
        val bestScoreTextView: TextView = itemView.findViewById(R.id.best_score)
        val totalPlaytimeTextView: TextView = itemView.findViewById(R.id.playtime)
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val detailsButton: ImageView = itemView.findViewById(R.id.detailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_item, parent, false)

        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {

    }


}
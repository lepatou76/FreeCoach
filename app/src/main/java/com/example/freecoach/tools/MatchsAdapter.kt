package com.example.freecoach.tools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freecoach.Match
import com.example.freecoach.MatchsActivity
import com.example.freecoach.R

class MatchsAdapter(private var matchs: List<Match>, val context: MatchsActivity ):
    RecyclerView.Adapter<MatchsAdapter.MatchsViewHolder>(){

    class MatchsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val homeTeamName: TextView = itemView.findViewById(R.id.match_item_home_team_name)
        val outsideTeamName: TextView = itemView.findViewById(R.id.match_item_outside_team_name)
        val homeTeamGoals: TextView = itemView.findViewById(R.id.match_item_home_team_goals)
        val outsideTeamGoals: TextView = itemView.findViewById(R.id.match_item_outside_team_goals)
        val challengeResult: TextView = itemView.findViewById(R.id.match_item_challenge_result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_item, parent, false)

        return MatchsViewHolder(view)
    }

    override fun getItemCount(): Int = matchs.size

    override fun onBindViewHolder(holder: MatchsViewHolder, position: Int) {
        val currentMatch = matchs[position]
        holder.homeTeamName.text = currentMatch.teamHome
        holder.outsideTeamName.text = currentMatch.teamOutside
        holder.homeTeamGoals.text = currentMatch.goalTeamHome.toString()
        holder.outsideTeamGoals.text = currentMatch.goalTeamOutside.toString()
        if(currentMatch.challengeResult) {
            holder.challengeResult.text = "Gagné"
            holder.challengeResult.setTextColor(Color.parseColor("#30A330"))
        } else { holder.challengeResult.text = "Perdu"
            holder.challengeResult.setTextColor(Color.parseColor("red"))}
    }

}
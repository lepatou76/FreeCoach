package com.example.freecoach.tools

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freecoach.InfosSeason
import com.example.freecoach.Match
import com.example.freecoach.MatchsActivity
import com.example.freecoach.PopupMatch
import com.example.freecoach.PopupPlayer
import com.example.freecoach.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MatchsAdapter(private var matchs: List<Match>, val context: MatchsActivity ):
    RecyclerView.Adapter<MatchsAdapter.MatchsViewHolder>(){

    val fileNameSeason = "infosSeason"
    val seasontype = object : TypeToken<InfosSeason>() {}.type
    var infosSeason = InfosSeason()

    class MatchsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val homeTeamName: TextView = itemView.findViewById(R.id.match_item_home_team_name)
        val outsideTeamName: TextView = itemView.findViewById(R.id.match_item_outside_team_name)
        val homeTeamGoals: TextView = itemView.findViewById(R.id.match_item_home_team_goals)
        val outsideTeamGoals: TextView = itemView.findViewById(R.id.match_item_outside_team_goals)
        val challengeResult: TextView = itemView.findViewById(R.id.match_item_challenge_result)
        val matchLayout: LinearLayout = itemView.findViewById(R.id.match_item_layout)
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
        holder.challengeResult.text = currentMatch.challengeResult

        // Récupération des infos de la saison sauvegardées si non nulles (nom de l'équipe)
        if(Serializer.deSerialize(fileNameSeason, context) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, context).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }

        // adapter la couleur du texte suivant le resultat du challenge
        if(currentMatch.challengeResult == "Gagné") {
            holder.challengeResult.setTextColor(Color.parseColor("#30A330"))
        }
        if(currentMatch.challengeResult == "Perdu") {
            holder.challengeResult.setTextColor(Color.parseColor("#F35F5F"))
        }

        // adapter la couleur du cadre de l'item au résultat
        if(currentMatch.goalTeamHome == currentMatch.goalTeamOutside) {
            holder.matchLayout.setBackgroundResource(R.drawable.black_border)
        }
        if(currentMatch.teamHome == infosSeason.teamName && currentMatch.goalTeamHome > currentMatch.goalTeamOutside) {
            holder.matchLayout.setBackgroundResource(R.drawable.green_border)
        }
        if(currentMatch.teamOutside == infosSeason.teamName && currentMatch.goalTeamHome < currentMatch.goalTeamOutside) {
            holder.matchLayout.setBackgroundResource(R.drawable.green_border)
        }

        // interaction click sur un joueur
        holder.itemView.setOnClickListener {
            // afficher les détails du joueur
            PopupMatch(this, currentMatch).show()
        }
    }

}
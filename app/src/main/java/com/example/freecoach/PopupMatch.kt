package com.example.freecoach

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.freecoach.tools.MatchsAdapter
import com.example.freecoach.tools.MatchsDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PopupMatch(adapter: MatchsAdapter,
    private val currentMatch: Match): Dialog(adapter.context) {

    private val db = MatchsDataBaseHelper(adapter.context)
    private var context: Context = adapter.context
    private val fileNameSeason = "infosSeason"
    private var infosSeason = InfosSeason()
    private val seasontype = object : TypeToken<InfosSeason>() {}.type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_match_details)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        // Récupération des infos de la saison sauvegardées si non nulles
        if(Serializer.deSerialize(fileNameSeason, context) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, context).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }

        // Affichage du résultat avec couleurs adaptées
        var matchResult = findViewById<TextView>(R.id.popup_match_result)

        if(currentMatch.goalTeamHome == currentMatch.goalTeamOutside) {
            matchResult.text = " Match Nul "
            matchResult.setBackgroundResource(R.drawable.black_border)
        }
        if(currentMatch.teamHome == infosSeason.teamName) {
            if (currentMatch.goalTeamHome > currentMatch.goalTeamOutside) {
                matchResult.text = " Match Gagné "
                matchResult.setBackgroundResource(R.drawable.green_border)
                matchResult.setTextColor(Color.parseColor("#30A330"))
                findViewById<RelativeLayout>(R.id.popup_match_score).setBackgroundResource(R.drawable.green_border)
            }
            if (currentMatch.goalTeamHome < currentMatch.goalTeamOutside) {
                matchResult.text = " Match Perdu "
                matchResult.setBackgroundResource(R.drawable.red_border)
                matchResult.setTextColor(Color.parseColor("#F35F5F"))
                findViewById<RelativeLayout>(R.id.popup_match_score).setBackgroundResource(R.drawable.red_border)
            }
        }
        if(currentMatch.teamHome != infosSeason.teamName) {
            if (currentMatch.goalTeamHome > currentMatch.goalTeamOutside) {
                matchResult.text = " Match Perdu "
                matchResult.setBackgroundResource(R.drawable.red_border)
                matchResult.setTextColor(Color.parseColor("#F35F5F"))
                findViewById<RelativeLayout>(R.id.popup_match_score).setBackgroundResource(R.drawable.red_border)
            }
            if (currentMatch.goalTeamHome < currentMatch.goalTeamOutside) {
                matchResult.text = " Match Gagné "
                matchResult.setBackgroundResource(R.drawable.green_border)
                matchResult.setTextColor(Color.parseColor("#30A330"))
                findViewById<RelativeLayout>(R.id.popup_match_score).setBackgroundResource(R.drawable.green_border)
            }
        }

        findViewById<TextView>(R.id.popup_match_homeTeam).text = currentMatch.teamHome
        findViewById<TextView>(R.id.popup_match_outsideTeam).text = currentMatch.teamOutside
        findViewById<TextView>(R.id.popup_match_homeGoals).text = currentMatch.goalTeamHome.toString()
        findViewById<TextView>(R.id.popup_match_outsideGoals).text = currentMatch.goalTeamOutside.toString()
        findViewById<TextView>(R.id.popup_match_challengeResult).text = currentMatch.challengeResult
        // adapter la couleur du texte suivant le resultat du challenge
        if(currentMatch.challengeResult == "Gagné") {
            findViewById<TextView>(R.id.popup_match_challengeResult).setBackgroundResource(R.drawable.green_border)
            findViewById<TextView>(R.id.popup_match_challengeResult).setTextColor(Color.parseColor("#30A330"))
        }
        if(currentMatch.challengeResult == "Perdu") {
            findViewById<TextView>(R.id.popup_match_challengeResult).setBackgroundResource(R.drawable.red_border)
            findViewById<TextView>(R.id.popup_match_challengeResult).setTextColor(Color.parseColor("#F35F5F"))
        }
        findViewById<TextView>(R.id.popup_match_report).text = currentMatch.matchReport

        setupCloseButton()
        setupDeleteMatch()
    }

    // Pour fermer la fenêtre
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_match_close_button).setOnClickListener {
            dismiss()
        }
    }
    // Pour supprimer le match
    private fun setupDeleteMatch() {
        findViewById<ImageView>(R.id.popup_delete_player_button).setOnClickListener {
            db.deleteMatch(currentMatch.id)
            val intent = Intent(context, MatchsActivity::class.java)
            context.startActivity(intent)

        }
    }
}
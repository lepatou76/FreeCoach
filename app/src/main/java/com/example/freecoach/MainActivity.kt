package com.example.freecoach

import android.content.Intent
import android.os.Bundle
import com.example.freecoach.R.layout.team_item_list
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityMainBinding
import com.example.freecoach.tools.PlayersDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: PlayersDataBaseHelper
    private val fileNameTeams = "groupTeamList"
    private val fileNameSeason = "infosSeason"
    private var teamsListView: ListView? = null
    private var teamsList = ArrayList<String>()
    private var infosSeason = InfosSeason()
    private val arraytype = object : TypeToken<ArrayList<String>>() {}.type
    private val seasontype = object : TypeToken<InfosSeason>() {}.type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PlayersDataBaseHelper(this)
        val nbPlayers = db.getAllPlayers("nom, prénom ASC").size
        binding.homeNbPlayers.text = nbPlayers.toString()

        // Récupération et affichage des infos de la saison sauvegardées si non nulles
        if(Serializer.deSerialize(fileNameSeason, this) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, this).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }
        binding.HomeTeamName.text = " " + infosSeason.teamName + " "
        binding.homeYearsSeason.text = infosSeason.yearsSeason
        binding.homeGroupLetter.text = infosSeason.groupLetter
        binding.homeNbTeams.text = infosSeason.nbTeams.toString()

        // Affichage de la liste des équipes sauvegardées si elle n'est pas nulle
        if(Serializer.deSerialize(fileNameTeams, this) != null) {
            val listBack = Serializer.deSerialize(fileNameTeams, this).toString()
            teamsList = Gson().fromJson(listBack, arraytype)
        }

        // Mise en page personnalisée pour afficher la liste des équipes
        teamsList.sort()
        teamsListView = binding.homeListTeams
        val adapter = ArrayAdapter(this@MainActivity, team_item_list, teamsList)
        teamsListView!!.adapter = adapter

        // Click ouvre le page d'édition de l'accueil
        binding.homeEditButton.setOnClickListener {
            val intent = Intent(this, EditHomeActivity::class.java)
            startActivity(intent)
        }
        // Click ouvre la page Joueurs
        binding.homePlayerScreenButton.setOnClickListener {
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
        // Click ouvre la page Matchs
        binding.homeMatchsScreenButton.setOnClickListener {
            val intent = Intent(this, MatchsActivity::class.java)
            startActivity(intent)
        }
    }
}
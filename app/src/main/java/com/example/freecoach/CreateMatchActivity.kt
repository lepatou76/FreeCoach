package com.example.freecoach

import android.R.*
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.freecoach.databinding.ActivityCreateMatchBinding
import com.example.freecoach.tools.MatchsDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CreateMatchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMatchBinding
    private lateinit var db: MatchsDataBaseHelper
    val fileNameTeams = "groupTeamList"
    val fileNameSeason = "infosSeason"
    var teamsList = ArrayList<String>()
    var infosSeason = InfosSeason()
    val arraytype = object : TypeToken<ArrayList<String>>() {}.type
    val seasontype = object : TypeToken<InfosSeason>() {}.type
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MatchsDataBaseHelper(this)

        // Récupération des infos de la saison sauvegardées si non nulles
        if(Serializer.deSerialize(fileNameSeason, this) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, this).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }

        // Récupération de la liste des équipes sauvegardées si elle n'est pas nulle
        if(Serializer.deSerialize(fileNameTeams, this) != null) {
            val listBack = Serializer.deSerialize(fileNameTeams, this).toString()
            teamsList = Gson().fromJson(listBack, arraytype)
        }
        teamsList.sort()
        // Ajout de l'équipe enregistrée dans la liste des équipes
        teamsList.add(0,infosSeason.teamName)


        // remplissage des spinners de selection des équipes du match
        val spinnerHome = binding.createSpinnerHome
        val spinnerOutside = binding.createSpinnerOutside
        val teamsAdapter = ArrayAdapter<String>(this, layout.simple_spinner_item, teamsList)
        teamsAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item)
        spinnerHome.adapter = teamsAdapter
        spinnerOutside.adapter = teamsAdapter

        // ne permettre qu'une seule selection de résultat du défi et enregistrer la valeur correspondante
        var challengeResult = "Egalité"
        binding.createRbWin.setOnClickListener {
            binding.createRbLoose.isChecked = false
            binding.createRbEquality.isChecked = false
            challengeResult = "Gagné"
        }
        binding.createRbLoose.setOnClickListener {
            binding.createRbWin.isChecked = false
            binding.createRbEquality.isChecked = false
            challengeResult = "Perdu"
        }
        binding.createRbEquality.setOnClickListener {
            binding.createRbWin.isChecked = false
            binding.createRbLoose.isChecked = false
            challengeResult = "Egalité"
        }

        // pour terminer la saisie des notes de match
        binding.createFinishNoteButton.setOnClickListener {
            hideKeyboard(binding.createReportTitle)
        }

        // Valider et enregistrer en BDD les infos du matchle
        binding.createMatchValidButton.setOnClickListener {
            // Eviter erreur si champs buts non remplis
            if(binding.createHomeGoals.text.isEmpty()) {
                binding.createHomeGoals.setText("0")
            }
            if(binding.createOutsideGoals.text.isEmpty()) {
                binding.createOutsideGoals.setText("0")
            }
            val homeTeam = binding.createSpinnerHome.selectedItem.toString()
            val outsideTeam = binding.createSpinnerOutside.selectedItem.toString()
            val homeGoals = binding.createHomeGoals.text.toString().toInt()
            val outsideGoals = binding.createOutsideGoals.text.toString().toInt()
            val matchReport = binding.createReportEditText.text.toString()

            val match = Match(0, homeTeam, outsideTeam, homeGoals, outsideGoals, challengeResult, matchReport)

            db.addMatch(match)
            val intent = Intent(this, MatchsActivity::class.java)
            startActivity(intent)
        }

        // retourner à l'écran des Matchs
        binding.createMatchExitButton.setOnClickListener {
            val intent = Intent(this, MatchsActivity::class.java)
            startActivity(intent)
        }
    }
}
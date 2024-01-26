package com.example.freecoach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityEditHomeBinding
import com.example.freecoach.tools.PlayersDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EditHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditHomeBinding
    val fileNameTeams = "groupTeamList"
    val fileNameSeason = "infosSeason"
    var teamsListView: ListView? = null
    var infosSeason = InfosSeason()
    var editTeamsList =  ArrayList<String>()
    val arraytype = object : TypeToken<ArrayList<String>>() {}.type
    val seasontype = object : TypeToken<InfosSeason>() {}.type


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupération et affichage des infos sauvegardées de la saison
        if(Serializer.deSerialize(fileNameSeason, this) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, this).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }
        binding.inputTeamName.setText(infosSeason.teamName)
        binding.inputSeason.setText(infosSeason.yearsSeason)
        binding.inputGroupLetter.setText(infosSeason.groupLetter)
        binding.inputNbTeams.setText(infosSeason.nbTeams.toString())

        // Affichage de la liste des équipes sauvegardées si elle n'est pas nulle
        if(Serializer.deSerialize(fileNameTeams, this) != null) {
            val listBack = Serializer.deSerialize(fileNameTeams, this).toString()
            editTeamsList = Gson().fromJson(listBack, arraytype)
        }

        // Ajout de l'équipe si le champ n'est pas vide par la validation au clavier
        binding.inputGroupTeams.setOnEditorActionListener { textView, actionID, KeyEvent ->
            if (binding.inputGroupTeams.text.isNotEmpty() && actionID == EditorInfo.IME_ACTION_DONE) {

                editTeamsList?.add((binding.inputGroupTeams.text.toString()))
                binding.inputGroupTeams.text.clear()
                hideKeyboard(binding.inputGroupTeams)
                // rafraichisement de la liste des équipes
                afficheEquipes()
                // Mise a jour du nombre d'équipe
                binding.inputNbTeams.setText((editTeamsList.size + 1).toString())


                return@setOnEditorActionListener true
            }
            false
        }

        // supprime une equipe si on clique dessus
        binding.editListTeams.setOnItemClickListener { parent, view, position, id ->
            editTeamsList.removeAt(position)
            // Mise a jour du nombre d'équipe
            binding.inputNbTeams.setText((editTeamsList.size + 1).toString())
            afficheEquipes()
        }

        // sauvegarde des informations au clic sur VALIDER
        binding.editHomeValidButton.setOnClickListener {

            // Sauvegarde de la liste des équipes du groupe
            val jsonString = Gson().toJson(editTeamsList)
            Serializer.serialize(fileNameTeams, jsonString, this)

            // Récupération des infos de la saisons puis sauvegarde
            val teamName = binding.inputTeamName.text.toString()
            val yearsSeason = binding.inputSeason.text.toString()
            val groupLetter = binding.inputGroupLetter.text.toString()
            val nbTeams = binding.inputNbTeams.text.toString().toInt()

            infosSeason = InfosSeason(teamName, yearsSeason, groupLetter, nbTeams)

            val jsonString2 = Gson().toJson(infosSeason)
            Serializer.serialize(fileNameSeason, jsonString2, this)

            // Retour sur la page d'accueil
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Vide la liste des équipes par le clic du bouton Vider Liste
        binding.clearListButton.setOnClickListener {
            editTeamsList.clear()
            // Mise a jour du nombre d'équipe
            binding.inputNbTeams.setText(editTeamsList.size.toString())
            afficheEquipes()
        }

        // Retourne a l'accueil si on clique
        binding.editHomeExitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        afficheEquipes()

        // Vide les champs quand on est dessus
        binding.inputTeamName.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.inputTeamName).text.clear()
            }}
        binding.inputSeason.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.inputSeason).text.clear()
            }}
        binding.inputGroupLetter.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.inputGroupLetter).text.clear()
            }}
    }

    // Pour mettre a jour la liste des équipes
    fun afficheEquipes() {
        // Mise en page personnalisée pour afficher la liste des équipes
        teamsListView = binding.editListTeams
        val adapter =
            ArrayAdapter(this@EditHomeActivity, R.layout.team_item_list, editTeamsList)
        teamsListView!!.adapter = adapter
    }

}
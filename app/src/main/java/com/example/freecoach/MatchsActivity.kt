package com.example.freecoach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freecoach.databinding.ActivityMatchsBinding
import com.example.freecoach.tools.MatchsAdapter
import com.example.freecoach.tools.MatchsDataBaseHelper
import com.example.freecoach.tools.PlayersDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MatchsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchsBinding
    private lateinit var db: MatchsDataBaseHelper
    private lateinit var matchAdapter: MatchsAdapter
    val fileNameSeason = "infosSeason"
    val seasontype = object : TypeToken<InfosSeason>() {}.type
    var infosSeason = InfosSeason()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MatchsDataBaseHelper(this)

        // Récupération et affichage des infos de la saison sauvegardées si non nulles (durée des matchs)
        if(Serializer.deSerialize(fileNameSeason, this) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, this).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }
        binding.matchDuration.setText(" Durée des matchs: " + infosSeason.matchDuration + " mn")

        // récupération et affichage du playersRecyclerview
        matchAdapter = MatchsAdapter(db.getAllMatchs(), this)

        binding.matchsRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.matchsRecyclerview.adapter = matchAdapter

        // Ecouteurs sur les boutons
        binding.matchsHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.matchsPlayerScreenButton.setOnClickListener {
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
        binding.addNewMatchButton.setOnClickListener {
            val intent = Intent(this, CreateMatchActivity::class.java)
            startActivity(intent)
        }
    }
}
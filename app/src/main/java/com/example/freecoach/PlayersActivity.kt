package com.example.freecoach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityPlayersBinding
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freecoach.tools.PlayersAdapter
import com.example.freecoach.tools.PlayersDataBaseHelper

class PlayersActivity: AppCompatActivity() {

    private lateinit var binding: ActivityPlayersBinding
    private lateinit var db: PlayersDataBaseHelper
    private lateinit var playerAdapter: PlayersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PlayersDataBaseHelper(this)

        // récupération et affichage du playersRecyclerview
        playerAdapter = PlayersAdapter(db.getAllPlayers(),this)

        binding.playersRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.playersRecyclerview.adapter = playerAdapter

        // retour a l'accueil par click bouton Home
        binding.playersHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // Click ouvre la page Matchs
        binding.playersMatchsScreenButton.setOnClickListener {
            val intent = Intent(this, MatchsActivity::class.java)
            startActivity(intent)
        }
        // Ouverture du popup d'ajout de joueur
        binding.addNewPlayerButton.setOnClickListener {
            PopupAddPlayer(adapter = PlayersAdapter(db.getAllPlayers(),this)).show()
        }
    }
    override fun onResume() {
        super.onResume()
        playerAdapter.refreshData(db.getAllPlayers())
    }

}

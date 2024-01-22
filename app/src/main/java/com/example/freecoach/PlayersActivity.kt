package com.example.freecoach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityPlayersBinding
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freecoach.tools.PlayersAdapter

class PlayersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayersBinding
    private lateinit var playerAdapter: PlayersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerAdapter = PlayersAdapter(this)

        binding.playersRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.playersRecyclerview.adapter = playerAdapter

        binding.addNewPlayer.setOnClickListener {

        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}

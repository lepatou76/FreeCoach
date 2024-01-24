package com.example.freecoach

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freecoach.databinding.ActivityEditPlayerBinding
import com.example.freecoach.databinding.ActivityMainBinding
import com.example.freecoach.databinding.ActivityPlayersBinding

class EditPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

    binding.editPlayerExitButton.setOnClickListener {
        val intent = Intent(this, PlayersActivity::class.java)
        startActivity(intent)
    }

    }
}
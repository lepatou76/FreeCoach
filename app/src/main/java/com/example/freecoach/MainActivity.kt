package com.example.freecoach

import android.content.Intent
import android.os.Bundle
import com.example.freecoach.R.layout.team_item_list
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var teamsListView: ListView? = null
    var teamsList = arrayOf(
        "ENT HVO / PAYS MINIER 4", "VESOUL F.C. 3", "PAYS MINIER 2", "LURE J.S. 2", "MELISEY / ST BARTHELEMY", "DAMPIERRE / LINOTTE", "FRANCHEVELLE 2"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mise en page personnalisée pour afficher la liste des équipes
        teamsListView = binding.homeListTeams
        val adapter = ArrayAdapter(this@MainActivity, team_item_list, teamsList)
        teamsListView!!.adapter = adapter

        binding.homeEditButton.setOnClickListener {
            val intent = Intent(this, EditHomeActivity::class.java)
            startActivity(intent)
        }
    }


}
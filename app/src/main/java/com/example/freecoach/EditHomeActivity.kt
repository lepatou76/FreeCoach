package com.example.freecoach

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityEditHomeBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard


class EditHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditHomeBinding

    var teamsListView: ListView? = null
    var editTeamsList =  mutableListOf<String>()


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.inputGroupTeams.setOnEditorActionListener { textView, actionID, KeyEvent ->
            if (actionID == EditorInfo.IME_ACTION_DONE) {

                editTeamsList?.add((binding.inputGroupTeams.text.toString()))
                binding.inputGroupTeams.text.clear()
                hideKeyboard(binding.inputGroupTeams)
                // Mise en page personnalisée pour afficher la liste des équipes
                teamsListView = binding.editListTeams
                val adapter =
                    ArrayAdapter(this@EditHomeActivity, R.layout.team_item_list, editTeamsList)
                teamsListView!!.adapter = adapter

                return@setOnEditorActionListener true
            }
            false
        }
        // Mise en page personnalisée pour afficher la liste des équipes
        teamsListView = binding.editListTeams
        val adapter =
            ArrayAdapter(this@EditHomeActivity, R.layout.team_item_list, editTeamsList)
        teamsListView!!.adapter = adapter
    }
}
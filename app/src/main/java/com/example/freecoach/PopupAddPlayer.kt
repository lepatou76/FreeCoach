package com.example.freecoach

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import com.example.freecoach.tools.PlayersAdapter
import com.example.freecoach.tools.PlayersDataBaseHelper

class PopupAddPlayer(adapter: PlayersAdapter): Dialog(adapter.context) {

    private lateinit var db: PlayersDataBaseHelper
    private var context: Context = adapter.context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_ajout_joueur)
        setupCloseButton()

        db = PlayersDataBaseHelper(context)
        val editTextFirstName = findViewById<EditText>(R.id.popup_addPlayer_firstName)
        val editTextLastName = findViewById<EditText>(R.id.popup_addPlayer_lastName)

        // Valider sur le clavier virtuel crée le joueur et l'ajoute dans la BDD
        editTextFirstName.setOnEditorActionListener { v, actionId, event ->
            if (editTextFirstName.text.isNotEmpty() &&
                editTextLastName.text.isNotEmpty() &&
                actionId == EditorInfo.IME_ACTION_DONE) {
                val lastName = editTextLastName.text.toString()
                val firstName = editTextFirstName.text.toString()
                val player = Player(0, lastName, firstName)
                db.addPlayer(player)
                val intent = Intent(context, PlayersActivity::class.java)
                context.startActivity(intent)
                dismiss()

                return@setOnEditorActionListener true
            }
            false

        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_addPlayer_closeButton).setOnClickListener {
            // fermer la fenêtre
            dismiss()
        }
    }
}



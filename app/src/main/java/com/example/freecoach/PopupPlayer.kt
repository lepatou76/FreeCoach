package com.example.freecoach

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.freecoach.tools.PlayersAdapter
import com.example.freecoach.tools.PlayersDataBaseHelper

class PopupPlayer(
    adapter: PlayersAdapter,
    private val currentPlayer: Player): Dialog(adapter.context) {

    private val db = PlayersDataBaseHelper(adapter.context)
    private var context: Context = adapter.context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_player_details)
        getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        findViewById<TextView>(R.id.popup_lastName).text = currentPlayer.lastName
        findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.firstName
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.strongMax.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.weakMax.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.headMax.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.totalMax.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.nbMatchs.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.playtime.toString()
        //findViewById<TextView>(R.id.popup_firstName).text = currentPlayer.scored.toString()

        setupCloseButton()
        setupDeletePlayer()
        setupEditPlayerButton()
    }

    // Acceder aux modifications des infos joueur
    private fun setupEditPlayerButton() {
        findViewById<ImageView>(R.id.popup_edit_player_button).setOnClickListener {
            val intent = Intent(context, EditPlayerActivity::class.java)
            context.startActivity(intent)
        }
    }

    // Pour fermer la fenêtre
    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.popup_close_button).setOnClickListener {
            dismiss()
        }
    }
    // Pour supprimer un joueur
    private fun setupDeletePlayer() {
        findViewById<ImageView>(R.id.popup_delete_player_button).setOnClickListener {
            db.deletePlayer(currentPlayer.id)
            val intent = Intent(context, PlayersActivity::class.java)
            context.startActivity(intent)

        }
    }
}
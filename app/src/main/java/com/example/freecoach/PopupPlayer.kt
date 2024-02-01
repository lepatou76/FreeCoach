package com.example.freecoach


import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.example.freecoach.R.*
import com.example.freecoach.tools.PlayersAdapter
import com.example.freecoach.tools.PlayersDataBaseHelper

class PopupPlayer(adapter: PlayersAdapter,
    private val currentPlayer: Player): Dialog(adapter.context) {

    private val db = PlayersDataBaseHelper(adapter.context)
    private var context: Context = adapter.context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(layout.popup_player_details)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        findViewById<TextView>(id.popup_lastName).text = currentPlayer.lastName
        findViewById<TextView>(id.popup_firstName).text = currentPlayer.firstName
        findViewById<TextView>(id.popup_max_strong).text = currentPlayer.strongMax.toString()
        findViewById<ImageView>(id.popup_image_strong).setImageResource(currentPlayer.strongImage)
        findViewById<TextView>(id.popup_max_weak).text = currentPlayer.weakMax.toString()
        findViewById<ImageView>(id.popup_image_weak).setImageResource(currentPlayer.weakImage)
        findViewById<TextView>(id.popup_max_head).text = currentPlayer.headMax.toString()
        findViewById<ImageView>(id.popup_image_head).setImageResource(currentPlayer.headImage)
        findViewById<TextView>(id.popup_max_total).text = currentPlayer.totalMax.toString()
        findViewById<ImageView>(id.popup_image_total).setImageResource(currentPlayer.totalImage)
        findViewById<TextView>(id.popup_nbMatchs).text = currentPlayer.nbMatchs.toString()
        findViewById<TextView>(id.popup_playtime).text = currentPlayer.playtime.toString()
        findViewById<TextView>(id.popup_nbGoals).text = currentPlayer.scored.toString()

        // si le joueur a joué au moins 1 match, calcul et affichage de son temps de jeu par match
        if(currentPlayer.nbMatchs.toString().toInt() != 0) {
            findViewById<TextView>(id.popup_ratio).text =
                (currentPlayer.playtime.toString().toInt())
                    .div(currentPlayer.nbMatchs.toString().toInt()).toString()
        }

        setupCloseButton()
        setupDeletePlayer()
        setupEditPlayerButton()
    }

    // Acceder aux modifications des infos joueur
    private fun setupEditPlayerButton() {
        findViewById<ImageView>(id.popup_edit_player_button).setOnClickListener {

            val intent = Intent(context, EditPlayerActivity()::class.java)
            intent.putExtra("player_id", currentPlayer.id)
            context.startActivity(intent)
        }
    }

    // Pour fermer la fenêtre
    private fun setupCloseButton() {
        findViewById<ImageView>(id.popup_close_button).setOnClickListener {
            dismiss()
        }
    }
    // Pour supprimer un joueur
    private fun setupDeletePlayer() {
        findViewById<ImageView>(id.popup_delete_player_button).setOnClickListener {
            db.deletePlayer(currentPlayer.id)
            val intent = Intent(context, PlayersActivity::class.java)
            context.startActivity(intent)

        }
    }
}
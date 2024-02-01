package com.example.freecoach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.freecoach.databinding.ActivityEditPlayerBinding
import com.example.freecoach.tools.PlayersDataBaseHelper
import com.example.freecoach.tools.Serializer
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EditPlayerActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityEditPlayerBinding
    private lateinit var db: PlayersDataBaseHelper
    private var playerID: Int = -1
    private val fileNameSeason = "infosSeason"
    private val seasontype = object : TypeToken<InfosSeason>() {}.type
    private var infosSeason = InfosSeason()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = PlayersDataBaseHelper(this)

        // Récupération et affichage des infos de la saison sauvegardées si non nulles
        if(Serializer.deSerialize(fileNameSeason, this) != null) {
            val seasonBack = Serializer.deSerialize(fileNameSeason, this).toString()
            infosSeason = Gson().fromJson(seasonBack, seasontype)
        }

        // récupérer le joueur et ses infos par son ID
        playerID = intent.getIntExtra("player_id", -1)
        if(playerID == -1) {
            finish()
            return
        }
        val currentPlayer = db.getPlayerByID(playerID)
        binding.editLastName.setText(currentPlayer.lastName)
        binding.editFirstName.setText(currentPlayer.firstName)


        // valider les valeurs entrées dans scéance jongles par le clavier fait le total
        // et compare avec les meilleurs scores enregistrés dans la BDD
        binding.editNewHead.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var strong = binding.editNewStrong.text.toString()
                var weak = binding.editNewWeak.text.toString()
                var head = binding.editNewHead.text.toString()

                // rempli les champs si laissés vides par erreur
                if (strong.isEmpty()) { strong = "0"
                    binding.editNewStrong.setText("0")
                }
                if (weak.isEmpty()) { weak = "0"
                    binding.editNewWeak.setText("0")
                }
                if (head.isEmpty()) { head = "0"
                    binding.editNewHead.setText("0")
                }
                compareResult(strong.toInt(), currentPlayer.strongMax, binding.editImageStrong)
                compareResult(weak.toInt(), currentPlayer.weakMax, binding.editImageWeak)
                compareResult(head.toInt(), currentPlayer.headMax, binding.editImageHead)
                compareResult((strong.toInt() + weak.toInt() + head.toInt()), currentPlayer.totalMax, binding.editImageTotal)

                binding.editNewTotal.setText((strong.toInt() + weak.toInt() + head.toInt()).toString())
                hideKeyboard(binding.editNewTotal)

                return@setOnEditorActionListener true
            }
            false
        }

        // Test si le temps de jeu entré n'est pas supérieur à la durée d'un match
        // et affichage d'un message d'erreur sinon
        binding.editAddPlaytime.setOnEditorActionListener { v, actionId, event ->
            val matchDuration = infosSeason.matchDuration
            var timeAdded = binding.editAddPlaytime.text.toString()
            if (timeAdded.isEmpty()) { timeAdded = "0"
                binding.editAddPlaytime.setText("0")}
            if (actionId == EditorInfo.IME_ACTION_DONE){

                if(timeAdded.toInt() > matchDuration) {
                    val error  = Toast.makeText(this, "  LE TEMPS SAISI EST SUPERIEUR" +
                            "\nAU TEMPS MAXIMUM D\'UN MATCH" ,
                        Toast.LENGTH_SHORT)
                    error.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0)
                    error.show()
                    binding.editAddPlaytime.text.clear()
                    hideKeyboard(binding.editAddPlaytime)
                }

                hideKeyboard(binding.editAddPlaytime)
                return@setOnEditorActionListener true
            }
            false
        }
        // Ne pas laisser le champ but à ajouter vide par erreur
        binding.editAddGoals.setOnEditorActionListener { v, actionId, event ->
            var goalsToAdd = binding.editAddGoals.text.toString()

            if (actionId == EditorInfo.IME_ACTION_DONE){
                if(goalsToAdd.isEmpty()) {binding.editAddGoals.setText("0")}
                hideKeyboard(binding.editAddGoals)
                return@setOnEditorActionListener true
            }
            false
        }

        // Enregistre dans la BDD les nouvelles informations du joueur
        binding.editPlayerValidButton.setOnClickListener {
            val newLastName = binding.editLastName.text.toString()
            val newFirstName = binding.editFirstName.text.toString()
            var newStrongMax = binding.editNewStrong.text.toString().toInt()
            var newStrongMaxImage = currentPlayer.strongImage
            var newWeakMax = binding.editNewWeak.text.toString().toInt()
            var newWeakMaxImage = currentPlayer.weakImage
            var newHeadMax = binding.editNewHead.text.toString().toInt()
            var newHeadMaxImage = currentPlayer.headImage
            var newTotalMax = binding.editNewTotal.text.toString().toInt()
            var newTotalMaxImage = currentPlayer.totalImage
            var newNbMatchs: Int = currentPlayer.nbMatchs

            // Si nouvelle scéance de jongle, récupérer les images de comparaison
            if (binding.editNewTotal.text.toString().toInt() != 0) {
                newStrongMaxImage = compareResultForBDD(newStrongMax, currentPlayer.strongMax)
                newWeakMaxImage = compareResultForBDD(newWeakMax, currentPlayer.weakMax)
                newHeadMaxImage = compareResultForBDD(newHeadMax, currentPlayer.headMax)
                newTotalMaxImage = compareResultForBDD(newTotalMax, currentPlayer.totalMax)
            }

            // Tester les nouveaux scores face au max enregistrés et garder le plus haut
            if (newStrongMax < currentPlayer.strongMax ) {
                newStrongMax = currentPlayer.strongMax}
            if (newWeakMax < currentPlayer.weakMax ) {
                newWeakMax = currentPlayer.weakMax}
            if (newHeadMax < currentPlayer.headMax ) {
                newHeadMax = currentPlayer.headMax}
            if (newTotalMax < currentPlayer.totalMax ) {
                newTotalMax = currentPlayer.totalMax}

            // Si il y a du temps de jeu ajouté, mettre à jour le nombre de matchs
            if(binding.editAddPlaytime.text.toString().toInt() != 0) {
                newNbMatchs ++
            }
            // mettre à jour le temps de jeu et les buts marqués
            val newPlaytime = currentPlayer.playtime + binding.editAddPlaytime.text.toString().toInt()
            val newScored = currentPlayer.scored + binding.editAddGoals.text.toString().toInt()

            // Créer l'objet joueur pour le mettre à jour dans la BDD
            val updatedPlayer = Player(playerID, newLastName, newFirstName,
                newStrongMax, newStrongMaxImage, newWeakMax, newWeakMaxImage,
                newHeadMax, newHeadMaxImage, newTotalMax, newTotalMaxImage,
                newNbMatchs, newPlaytime, newScored)

            db.updatePlayer(updatedPlayer)
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }

        // Bouton pour fermer et retourner à l'écran joueur
        binding.editPlayerExitButton.setOnClickListener {
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }

        // Vide les champs quand on est dessus
        binding.editNewStrong.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.editNewStrong).text.clear()
            }}
        binding.editNewWeak.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.editNewWeak).text.clear()
            }}
        binding.editNewHead.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.editNewHead).text.clear()
            }}
        binding.editAddPlaytime.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.editAddPlaytime).text.clear()
            }}
        binding.editAddGoals.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (binding.editAddGoals).text.clear()
            }}

    }

    // Pour comparer les nouveaux scores aux scores max et afficher l'image correspondante
    // sur la page Modification Infos Joueur
    fun compareResult(score: Int, max: Int, image: ImageView) {
        if (score > max) {
            image.setImageResource(R.drawable.more_than_last)
        }
        if (score < max) {
            image.setImageResource(R.drawable.less_than_last)
        }
        if (score == max) {
            image.setImageResource(R.drawable.same_than_last)
        }
    }

    // Pour comparer les nouveaux scores aux scores max pour enregistrer en BDD
    // et afficher l'image correspondante sur la page Infos Joueur
    fun compareResultForBDD(score: Int,max: Int): Int {
        var imageID = 0
        if (score > max) {
            imageID =  (R.drawable.more_than_last)
        }
        if (score < max) {
            imageID =  (R.drawable.less_than_last)
        }
        if (score == max) {
            imageID =  (R.drawable.same_than_last)
        }
        return imageID
    }
}
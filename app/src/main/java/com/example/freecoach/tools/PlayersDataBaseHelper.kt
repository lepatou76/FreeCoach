package com.example.freecoach.tools

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.freecoach.Player
import com.example.freecoach.PlayersActivity

class PlayersDataBaseHelper(context: Context): SQLiteOpenHelper
    (context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "players.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allplayers"
        private const val COLUMN_ID = "id"
        private const val COLUMN_LASTNAME = "nom"
        private const val COLUMN_FIRSTNAME = "prénom"
        private const val COLUMN_STRONG = "piedFort"
        private const val COLUMN_STRONGIMG = "imageFort"
        private const val COLUMN_WEAK = "piedfaible"
        private const val COLUMN_WEAKIMG = "imageFaible"
        private const val COLUMN_HEAD = "tête"
        private const val COLUMN_HEADIMG = "imageTête"
        private const val COLUMN_TOTAL = "totalJongles"
        private const val COLUMN_TOTALIMG = "imageTotal"
        private const val COLUMN_NBMATCHS = "nbMatchs"
        private const val COLUMN_PLAYTIME = "totalTempsJeu"
        private const val COLUMN_SCORED = "butsMarqués"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY," +
                " $COLUMN_LASTNAME TEXT," +
                " $COLUMN_FIRSTNAME TEXT," +
                " $COLUMN_STRONG INTEGER," +
                " $COLUMN_STRONGIMG INTEGER," +
                " $COLUMN_WEAK INTEGER," +
                " $COLUMN_WEAKIMG INTEGER," +
                " $COLUMN_HEAD INTEGER," +
                " $COLUMN_HEADIMG INTEGER," +
                " $COLUMN_TOTAL INTEGER," +
                " $COLUMN_TOTALIMG INTEGER," +
                " $COLUMN_NBMATCHS INTEGER," +
                " $COLUMN_PLAYTIME INTEGER," +
                " $COLUMN_SCORED INTEGER)"
        db?.execSQL(createTableQuery)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // ajouter un nouveau joueur dans la BDD
    fun addPlayer(player: Player){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LASTNAME, player.lastName)
            put(COLUMN_FIRSTNAME, player.firstName)
            put(COLUMN_STRONG, player.strongMax)
            put(COLUMN_STRONGIMG, player.strongImage)
            put(COLUMN_WEAK, player.weakMax)
            put(COLUMN_WEAKIMG, player.weakImage)
            put(COLUMN_HEAD, player.headMax)
            put(COLUMN_HEADIMG, player.headImage)
            put(COLUMN_TOTAL, player.totalMax)
            put(COLUMN_TOTALIMG, player.totalImage)
            put(COLUMN_NBMATCHS,player.nbMatchs)
            put(COLUMN_PLAYTIME, player.playtime)
            put(COLUMN_SCORED, player.scored)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    /** fonction pour récupérer les joueurs
     * @return liste des joueurs
     */
    fun getAllPlayers(): List<Player> {
        val playerList = mutableListOf<Player>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
            val strong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRONG))
            val strongImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRONGIMG))
            val weak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEAK))
            val weakImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEAKIMG))
            val head = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEAD))
            val headImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEADIMG))
            val total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))
            val totalImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTALIMG))
            val nbMatchs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NBMATCHS))
            val playTime = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PLAYTIME))
            val scored = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORED))
            val player =
                Player(id, lastName, firstName, strong, strongImage,
                    weak, weakImage, head, headImage, total, totalImage, nbMatchs, playTime, scored)
            playerList.add(player)
        }
        cursor.close()
        db.close()
        return playerList
    }

    /** Récupérer un joueur dans la BDD par son ID
     * @param playerID ID du joueur dans la BDD
     * @return le joueur correspondant
     */
    fun getPlayerByID(playerID: Int): Player {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $playerID"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LASTNAME))
        val firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRSTNAME))
        val strong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRONG))
        val strongImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRONGIMG))
        val weak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEAK))
        val weakImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEAKIMG))
        val head = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEAD))
        val headImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEADIMG))
        val total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))
        val totalImage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTALIMG))
        val nbMatchs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NBMATCHS))
        val playTime = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PLAYTIME))
        val scored = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORED))
        val player =
            Player(id, lastName, firstName, strong, strongImage,
                weak, weakImage, head, headImage, total, totalImage, nbMatchs, playTime, scored)

        cursor.close()
        db.close()
        return player
    }

    // Pour mettre à jour les infos d'un joueur dans la BDD
    fun updatePlayer(player: Player) {

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LASTNAME, player.lastName)
            put(COLUMN_FIRSTNAME, player.firstName)
            put(COLUMN_STRONG, player.strongMax)
            put(COLUMN_STRONGIMG, player.strongImage)
            put(COLUMN_WEAK, player.weakMax)
            put(COLUMN_WEAKIMG, player.weakImage)
            put(COLUMN_HEAD, player.headMax)
            put(COLUMN_HEADIMG, player.headImage)
            put(COLUMN_TOTAL, player.totalMax)
            put(COLUMN_TOTALIMG, player.totalImage)
            put(COLUMN_NBMATCHS, player.nbMatchs)
            put(COLUMN_PLAYTIME, player.playtime)
            put(COLUMN_SCORED, player.scored)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(player.id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    // Pour effacer un joueur de la BDD
    fun deletePlayer(playerId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(playerId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
    }
}
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
        private const val COLUMN_WEAK = "piedfaible"
        private const val COLUMN_HEAD = "tête"
        private const val COLUMN_TOTAL = "totalJongles"
        private const val COLUMN_PLAYTIME = "totalTempsJeu"
        private const val COLUMN_SCORED = "butsMarqués"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY," +
                " $COLUMN_LASTNAME TEXT," +
                " $COLUMN_FIRSTNAME TEXT," +
                " $COLUMN_STRONG INTEGER," +
                " $COLUMN_WEAK INTEGER," +
                " $COLUMN_HEAD INTEGER," +
                " $COLUMN_TOTAL INTEGER," +
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
            put(COLUMN_WEAK, player.weakMax)
            put(COLUMN_HEAD, player.headMax)
            put(COLUMN_TOTAL, player.totalMax)
            put(COLUMN_PLAYTIME, player.playtime)
            put(COLUMN_SCORED, player.scored)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    /** fonction pour récupérer les joueurs
     * @return playersList
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
            val weak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEAK))
            val head = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HEAD))
            val total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOTAL))
            val playTime = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PLAYTIME))
            val scored = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SCORED))
            val player =
                Player(id, lastName, firstName, strong, weak, head, total, playTime, scored)
            playerList.add(player)
            }
        cursor.close()
        db.close()
        return playerList
    }
}
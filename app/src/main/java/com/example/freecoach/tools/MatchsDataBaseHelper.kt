package com.example.freecoach.tools

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.freecoach.Match
import com.example.freecoach.Player

class MatchsDataBaseHelper(context: Context): SQLiteOpenHelper
    (context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "matchs.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allmatchs"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TEAMHOME = "équipeDomicile"
        private const val COLUMN_TEAMOUTSIDE = "équipeExtérieur"
        private const val COLUMN_GOALSTEAMHOME = "butsDomicile"
        private const val COLUMN_GOALSTEAMOUTSIDE = "butsExtérieur"
        private const val COLUMN_CHALLENGERESULT = "résultatDéfi"
        private const val COLUMN_MATCHREPORT = "compteRendu"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TEAMHOME TEXT," +
                "$COLUMN_TEAMOUTSIDE TEXT," +
                "$COLUMN_GOALSTEAMHOME INT," +
                "$COLUMN_GOALSTEAMOUTSIDE INT," +
                "$COLUMN_CHALLENGERESULT BOOLEAN," +
                "$COLUMN_MATCHREPORT TEXT)"

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    // fonction pour ajouter un match
    fun addMatch(match: Match) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TEAMHOME, match.teamHome)
            put(COLUMN_TEAMOUTSIDE, match.teamOutside)
            put(COLUMN_GOALSTEAMHOME, match.goalTeamHome)
            put(COLUMN_GOALSTEAMOUTSIDE, match.goalTeamOutside)
            put(COLUMN_CHALLENGERESULT, match.challengeResult)
            put(COLUMN_MATCHREPORT, match.matchReport)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // fonction pour récupérer tous les matchs
    fun getAllMatchs(): List<Match> {
        val matchList = mutableListOf<Match>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val teamHome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEAMHOME))
            val teamOutside = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEAMOUTSIDE))
            val homeGoals = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GOALSTEAMHOME))
            val outsideGoals = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GOALSTEAMOUTSIDE))
            val challengeResult = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHALLENGERESULT)) == 1
            val matchReport = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MATCHREPORT))
            val match = Match (id, teamHome, teamOutside, homeGoals, outsideGoals, challengeResult , matchReport)
            matchList.add(match)
        }
        cursor.close()
        db.close()
        return matchList
    }
}
package vanderclay.comet.benson.franticsearch.data.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import org.jetbrains.anko.db.*
import vanderclay.comet.benson.franticsearch.ui.App

class CardDbHelper(ctx: Context?): SQLiteOpenHelper(ctx, CardDbHelper.DATABASE_NAME, null, CardDbHelper.DB_VERSION) {
    companion object {
        private val DATABASE_NAME = "frantic_search"
        private val DB_VERSION = 1
    }

    private val TAG = "CardDBHelper"
    override fun onCreate(db: SQLiteDatabase?) {
        Log.d(TAG, "creating $CardTable.NAME table")

        db?.createTable(CardTable.NAME, true,
                CardTable.ID to TEXT + PRIMARY_KEY,
                CardTable.MULTIVERSE_ID to INTEGER,
                CardTable.CARD_NAME to TEXT,
                CardTable.MANA_COST to TEXT,
                CardTable.CONVERTED_MANA_COST to REAL,
                CardTable.COLORS to TEXT,
                CardTable.TYPES to TEXT,
                CardTable.SUBTYPES to TEXT,
                CardTable.RARITY to TEXT,
                CardTable.TEXT to TEXT,
                CardTable.POWER to TEXT,
                CardTable.TOUGHNESS to TEXT,
                CardTable.IMAGE_URL to TEXT,
                CardTable.RESERVED to INTEGER,
                CardTable.OWNED to INTEGER
            )
        Log.d(TAG, "created $CardTable.NAME table")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "deleting ${CardTable.NAME}")
        db?.dropTable(CardTable.NAME, true)
        Log.d(TAG, "deleted ${CardTable.NAME}")
        onCreate(db)
    }
}

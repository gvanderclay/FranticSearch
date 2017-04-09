package vanderclay.comet.benson.franticsearch.data.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import vanderclay.comet.benson.franticsearch.ui.App

class CardDbHelper(ctx: Context? = App.instance): SQLiteOpenHelper(ctx, CardDbHelper.DATABASE_NAME, null, CardDbHelper.DB_VERSION) {
    companion object {
        private val DATABASE_NAME = "frantic_search"
        private val DB_VERSION = 1
        val instance by lazy { CardDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(
         """
            CREATE TABLE ${CardTable.NAME}(
                ${CardTable.ID} TEXT PRIMARY KEY,
                ${CardTable.CARD_NAME} TEXT,
                ${CardTable.MANA_COST} TEXT,
                ${CardTable.CONVERTED_MANA_COST} REAL,
                ${CardTable.COLORS} TEXT,
                ${CardTable.TYPE} TEXT,
                ${CardTable.SUBTYPE} TEXT,
                ${CardTable.RARITY} TEXT,
                ${CardTable.TEXT} TEXT,
                ${CardTable.POWER} TEXT,
                ${CardTable.TOUGHNESS} TEXT,
                ${CardTable.IMAGE_URL} TEXT,
                ${CardTable.RESERVED} INTEGER,
                ${CardTable.OWNED} INTEGER
            )
        """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${CardTable.NAME}")
        onCreate(db)
    }
}

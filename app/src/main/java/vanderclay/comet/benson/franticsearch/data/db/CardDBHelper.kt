package vanderclay.comet.benson.franticsearch.data.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import vanderclay.comet.benson.franticsearch.ui.App

class CardDbHelper(ctx: Context? = App.instance): SQLiteOpenHelper(ctx, CardDbHelper.DATABASE_NAME, null, CardDbHelper.DB_VERSION) {
    companion object {
        private val DATABASE_NAME = "frantic_search"
        private val TABLE_NAME = "cards"
        private val DB_VERSION = 1
        // Columns name names
        private val id = "id"
        private val name = "name"
        private val manaCost = "mana_cost"
        private val cmc = "cmc"
        private val colors = "colors"
        private val type = "type"
        private val subtypes = "sub_types"
        private val rarity = "rarity"
        private val text = "text"
        private val power = "power"
        private val toughness = "toughness"
        private val loyalty = "loyalty"
        private val imageName = "image_name"
        private val reserved = "reserved"
        private val releaseDate = "release_date"
        private val starter = "starter"
        private val owned = "owned"
        val instance by lazy { CardDbHelper() }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
         """
            CREATE TABLE $TABLE_NAME(
                $id TEXT PRIMARY KEY,
                $name TEXT,
                $manaCost TEXT,
                $cmc REAL,
                $colors TEXT,
                $type TEXT,
                $subtypes TEXT,
                $rarity TEXT,
                $text TEXT,
                $power TEXT,
                $toughness TEXT,
                $loyalty INTEGER,
                $imageName TEXT,
                $reserved INTEGER,
                $releaseDate TEXT,
                $starter INTEGER,
                $owned INTEGER
            )
        """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}

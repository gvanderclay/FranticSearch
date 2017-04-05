package vanderclay.comet.benson.franticsearch.API

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.io.Serializable
import io.magicthegathering.javasdk.api.CardAPI


class CardSearchAPI(context: Context) : SQLiteOpenHelper(context, CardSearchAPI.DATABASE_NAME, null, CardSearchAPI.DATABASE_VERSION), Serializable {


    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_CONTACTS_TABLE = """
            CREATE TABLE $TABLE_CARDS(
                $id INTEGER PRIMARY KEY,
                $name TEXT,
                $manaCost INTEGER,
                $cmc INTEGER,
                $colors TEXT,
                $type TEXT,
                $subtypes TEXT,
                $rarity TEXT,
                $text TEXT,
                $power INTEGER,
                $toughness INTEGER,
                $loyalty INTEGER,
                $imageName TEXT,
                $reserved TEXT,
                $releaseDate TEXT,
                $starter TEXT,
                $owned INTEGER
            )
        """
        db.execSQL(CREATE_CONTACTS_TABLE)
    }

    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME)
        // Create tables again
        onCreate(db)
    }

    /*
     * Get all the cards from the api we're using and creates an instance of the database.
     */
    fun addAllCards() {
        val allCards = CardAPI.getAllCards()
        val db = this.writableDatabase
        for (apiCard in allCards) {
            val values = ContentValues()

            values.put(id, apiCard.id)
            values.put(name, apiCard.name)
            values.put(manaCost, apiCard.manaCost)
            values.put(cmc, apiCard.cmc)
            values.put(colors, apiCard.colors.toString())
            values.put(type, apiCard.type)
            values.put(subtypes, apiCard.subtypes.toString())
            values.put(rarity, apiCard.rarity)
            values.put(text, apiCard.text)
            values.put(power, apiCard.power)
            values.put(toughness, apiCard.toughness)
            values.put(loyalty, apiCard.loyalty)
            values.put(imageName, apiCard.imageName)
            values.put(reserved, apiCard.isReserved)
            values.put(releaseDate, apiCard.releaseDate)
            values.put(starter, apiCard.isStarter)
            values.put(owned, 0)

            // Inserting Row
            db.insert(TABLE_CARDS, null, values)
            // Closing database connection
        }
        db.close()

    }

    val contacts: MutableList<CardDO>
        get() {
            val resultSet = mutableListOf<CardDO>()

            val db = this.readableDatabase
            val cursor = db.rawQuery("SELECT * FROM " + TABLE_CARDS, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val card = CardDO(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7),
                            cursor.getInt(8),
                            cursor.getInt(9),
                            cursor.getString(10),
                            cursor.getInt(11) == 1,
                            cursor.getString(12),
                            cursor.getString(13),
                            cursor.getInt(14) == 1
                    )
                    resultSet.add(card)
                } while (cursor.moveToFirst())
            } else {
                println("cursor is null or there's nothing in the database.")
            }
            return resultSet
        }

    companion object {

        // Database Version
        private val DATABASE_VERSION = 1

        // Database Name
        private val DATABASE_NAME = "frantic_search"

        // Contacts table name
        private val TABLE_CARDS = "Cards"

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

        @JvmStatic fun main(args: Array<String>) {
        }
    }
}

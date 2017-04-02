package vanderclay.comet.benson.franticsearch.API

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import io.magicthegathering.javasdk.api.CardAPI
import io.magicthegathering.javasdk.resource.Card


class CardSearchAPI(context: Context) : SQLiteOpenHelper(context, CardSearchAPI.DATABASE_NAME, null, CardSearchAPI.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val create_card_table = "CREATE TABLE " + TABLE_CARDS + " ( " +
                id + " INTEGER PRIMARY KEY, " + name + " TEXT, " + manaCost + " INTEGER, " +
                cmc + " INTEGER, " + colors + " TEXT , " + type + " TEXT, " + power + " INTEGER, " +
                toughness + " INTEGER, " + loyalty + " INTEGER, " + imageName + " TEXT, " +
                reserved + " NUMERIC, " + releaseDate + " TEXT, " + starter + ", TEXT " + owned + " NUMBERIC)"

        db.execSQL(create_card_table)
    }

    fun addCards(card: CardDO) {
        val db = this.writableDatabase

        val snapShot = ContentValues()
        val cardsFromCardApi = CardAPI.getAllCards()

        for (apiCard in cardsFromCardApi) {
            snapShot.put(id, apiCard.id)
            snapShot.put(name, apiCard.name)
            snapShot.put(manaCost, apiCard.manaCost)
            snapShot.put(cmc, apiCard.cmc)
            snapShot.put(colors, apiCard.colors.toString())
            snapShot.put(type, apiCard.type)
            snapShot.put(power, apiCard.power)
            snapShot.put(toughness, apiCard.toughness)
            snapShot.put(loyalty, apiCard.loyalty)
            snapShot.put(imageName, apiCard.imageName)
            snapShot.put(reserved, apiCard.isReserved)
            snapShot.put(releaseDate, apiCard.releaseDate)
            snapShot.put(starter, apiCard.isStarter)
            snapShot.put(owned, 0)
        }

        db.insert(TABLE_CARDS, null, snapShot)
        db.close()
    }

    // Getting all cards from the Local Database
    fun getAllShops(): List<> {
        val shopList = ArrayList<CardDO>()

        // Select All Query
        val selectQuery = "SELECT * FROM " + TABLE_CARDS

        val db = this.writableDatabase

        val cursor = db.rawQuery(selectQuery, null)

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                var card = CardDO()
//                snapShot.put(id, apiCard.id)
//                snapShot.put(name, apiCard.name)
//                snapShot.put(manaCost, apiCard.manaCost)
//                snapShot.put(cmc, apiCard.cmc)
//                snapShot.put(colors, apiCard.colors.toString())
//                snapShot.put(type, apiCard.type)
//                snapShot.put(power, apiCard.power)
//                snapShot.put(toughness, apiCard.toughness)
//                snapShot.put(loyalty, apiCard.loyalty)
//                snapShot.put(imageName, apiCard.imageName)
//                snapShot.put(reserved, apiCard.isReserved)
//                snapShot.put(releaseDate, apiCard.releaseDate)
//                snapShot.put(starter, apiCard.isStarter)
//                snapShot.put(owned, 0)
                card.id = Integer.parseInt(cursor.getString(0))
                card.name =

//                        setId(Integer.parseInt(cursor.getString(0)))
//                shop.setName(cursor.getString(1))
//                shop.setAddress(cursor.getString(2))
                // Adding contact to list
//                shopList.add(shop)
            } while (cursor.moveToNext())
        }
        // return contact list
        return shopList
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        // Creating tables again
        onCreate(db)
    }

    companion object {

        private val DATABASE_VERSION = 1

        private val DATABASE_NAME = "frantic_search"

        //name of the table
        private val TABLE_CARDS = "cards"

        //multiverse id of the card, and the primary key of the card itself.
        private val id = "id"

        //name of the card
        private val name = "name"

        //mana cost of the card itself
        private val manaCost = "mana_cost"

        //converted mana cost of the card
        private val cmc = "cmc"

        //color identity of the card
        private val colors = "colors"

        //type of the card i.e. human or rogue
        private val type = "type"

        //sub types of the cards
        private val subtypes = "sub_types"

        //rarity of the card
        private val rarity = "rarity"

        //the actual text of the card itself
        private val text = "text"

        //the power of the given card
        private val power = "power"

        //the toughness of a given card
        private val toughness = "toughness"

        //loyalty of a planewalkers
        private val loyalty = "loyalty"

        //where to find the card itself
        private val imageName = "image_name"

        //is the card on the reserved list or not
        private val reserved = "reserved"

        //the release date of the card itself
        private val releaseDate = "release_date"

        //no idea what this field is but sure
        private val starter = "starter"

        //field for if you own the card or not
        private val owned = "owned"
    }
}

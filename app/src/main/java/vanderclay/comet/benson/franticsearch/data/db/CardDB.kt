package vanderclay.comet.benson.franticsearch.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.SQLException
import android.util.Log
import vanderclay.comet.benson.franticsearch.data.domain.datasource.CardDataSource
import vanderclay.comet.benson.franticsearch.extensions.clear
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import vanderclay.comet.benson.franticsearch.data.db.Card as DBCard

/**
 * Created by gclay on 4/5/17.
 */

class CardDB(val cardDbHelper: CardDbHelper = CardDbHelper.instance,
             val dataMapper: DbDataMapper = DbDataMapper()): CardDataSource {

    private val CARD_SELECTION = "${CardTable.CARD_NAME} = ?"

    private val TAG = "CARDB"


    override fun requestCardByName(name: String?): Card {
        val db = cardDbHelper.readableDatabase
        val cursor = db.query(CardTable.NAME, null, CARD_SELECTION, arrayOf(name), null, null, null)
        cursor.moveToFirst()
        val dbCard = getCardFromCursor(cursor)
        return dataMapper.convertToDomain(dbCard)

    }

    override fun requestAllCards(): List<Card> {
        val db = cardDbHelper.readableDatabase
        val cursor = db.query(CardTable.NAME, null, null, null, null, null, null)
        cursor.moveToFirst()
        val cards = mutableListOf<Card>()
        do {
            val dbCard = getCardFromCursor(cursor)
            val card = dataMapper.convertToDomain(dbCard)
            cards.add(card)
        } while(cursor.moveToNext())
        return cards.toList()
    }

    fun saveCard(card: Card) {
        val db = cardDbHelper.writableDatabase
        db.clear(CardTable.NAME)
        val values = ContentValues()

        with(dataMapper.convertFromDomain(card)) {

            values.put(CardTable.ID, id)
            values.put(CardTable.CARD_NAME, name)
            values.put(CardTable.MANA_COST, manaCost)
            values.put(CardTable.CONVERTED_MANA_COST, convertedManaCost)
            values.put(CardTable.COLORS, colors)
            values.put(CardTable.TYPE, type)
            values.put(CardTable.POWER, power)
            values.put(CardTable.TOUGHNESS, toughness)
            values.put(CardTable.IMAGE_URL, imageUrl)
            values.put(CardTable.RESERVED, reserved)
            values.put(CardTable.OWNED, owned)
            cardDbHelper.writableDatabase.insert(CardTable.NAME, null, values)
        }
    }

    fun saveCards(cards: List<Card>) {
        val db = cardDbHelper.writableDatabase
        try {
            db.beginTransaction()
            cards.forEach {
                saveCard(it)
            }
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            Log.e(TAG, "Error batch saving cards", e)
        } finally {
            db.endTransaction()
        }
    }

    private fun getCardFromCursor(cursor: Cursor): DBCard {
        return  DBCard(
                cursor.getString(cursor.getColumnIndex(CardTable.ID)),
                cursor.getString(cursor.getColumnIndex(CardTable.CARD_NAME)),
                cursor.getString(cursor.getColumnIndex(CardTable.MANA_COST)),
                cursor.getDouble(cursor.getColumnIndex(CardTable.CONVERTED_MANA_COST)),
                cursor.getString(cursor.getColumnIndex(CardTable.COLORS)),
                cursor.getString(cursor.getColumnIndex(CardTable.TYPE)),
                cursor.getString(cursor.getColumnIndex(CardTable.SUBTYPE)),
                cursor.getString(cursor.getColumnIndex(CardTable.RARITY)),
                cursor.getString(cursor.getColumnIndex(CardTable.TEXT)),
                cursor.getString(cursor.getColumnIndex(CardTable.POWER)),
                cursor.getString(cursor.getColumnIndex(CardTable.TOUGHNESS)),
                cursor.getString(cursor.getColumnIndex(CardTable.IMAGE_URL)),
                cursor.getInt(cursor.getColumnIndex(CardTable.RESERVED)) == 1,
                cursor.getInt(cursor.getColumnIndex(CardTable.OWNED)) == 1
        )
    }
}

package vanderclay.comet.benson.franticsearch.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.util.Log
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.insertOrThrow
import org.jetbrains.anko.db.select
import vanderclay.comet.benson.franticsearch.data.domain.datasource.CardDataSource
import vanderclay.comet.benson.franticsearch.extensions.clear
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import vanderclay.comet.benson.franticsearch.data.db.Card as DBCard

/**
 * Created by gclay on 4/5/17.
 */

class CardDB(val ctx: Context?, val cardDbHelper: CardDbHelper = CardDbHelper(ctx),
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
        return db.select(CardTable.NAME).exec {
            moveToFirst()
            val cards = mutableListOf<Card>()
            while(!isAfterLast){
                val dbCard = getCardFromCursor(this)
                val card = dataMapper.convertToDomain(dbCard)
                cards.add(card)
                moveToNext()
            }
            cards
        }
    }

    fun saveCard(card: Card) {
        val db = cardDbHelper.writableDatabase

        with(dataMapper.convertFromDomain(card)) {
            db.insertOrThrow(
                    CardTable.NAME,
                    CardTable.ID to  id,
                    CardTable.MULTIVERSE_ID to multiverseId,
                    CardTable.CARD_NAME to  name,
                    CardTable.MANA_COST to  manaCost,
                    CardTable.CONVERTED_MANA_COST to  convertedManaCost,
                    CardTable.COLORS to  colors,
                    CardTable.TYPES to  types,
                    CardTable.SUBTYPES to  subtypes,
                    CardTable.RARITY to  rarity,
                    CardTable.TEXT to text,
                    CardTable.POWER to  power,
                    CardTable.TOUGHNESS to  toughness,
                    CardTable.IMAGE_URL to  imageUrl,
                    CardTable.RESERVED to  reserved,
                    CardTable.OWNED to  owned
            )
        }
    }

    fun saveCards(cards: List<Card>) {
        try {
            cards.forEach {
                saveCard(it)
            }

        } catch (e: SQLException) {
            Log.e(TAG, "Error batch saving cards", e)
        } catch(e: Throwable) {
            Log.e(TAG, "Error", e)
        }
    }

    private fun getCardFromCursor(cursor: Cursor): DBCard {
        return  DBCard(
                cursor.getString(cursor.getColumnIndex(CardTable.ID)),
                cursor.getInt(cursor.getColumnIndex(CardTable.MULTIVERSE_ID)),
                cursor.getString(cursor.getColumnIndex(CardTable.CARD_NAME)),
                cursor.getString(cursor.getColumnIndex(CardTable.MANA_COST)),
                cursor.getDouble(cursor.getColumnIndex(CardTable.CONVERTED_MANA_COST)),
                cursor.getString(cursor.getColumnIndex(CardTable.COLORS)),
                cursor.getString(cursor.getColumnIndex(CardTable.TYPES)),
                cursor.getString(cursor.getColumnIndex(CardTable.SUBTYPES)),
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

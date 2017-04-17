package vanderclay.comet.benson.franticsearch.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import vanderclay.comet.benson.franticsearch.api.MtgAPI

/**
 * Created by gclay on 4/14/17.
 */

class Deck(val name: String, deckKey:String? = null) {
    private val TAG = "Deck"

    private val mDeckDatabase = FirebaseDatabase.getInstance()
            .reference
            .child("Decks")
            .child(FirebaseAuth.getInstance().currentUser?.uid)

    private val deckReference: DatabaseReference

    private val cardListReference: DatabaseReference

    var key: String? = deckKey

    var cards = mutableListOf<Card>()

    // index of the card that will be used for the cover
    var coverCardIndex = 0

    init {
        if(key != null) {
            deckReference = mDeckDatabase.child(key)
            cardListReference = deckReference.child("cards")
        }
        else {
            deckReference = mDeckDatabase.push()
            key = deckReference.key
            deckReference.child("deckName").setValue(name)
            cardListReference = deckReference.child("cards")
            cardListReference.setValue(arrayListOf<String>())
        }
        Log.d(TAG, "Deck created")
    }

    fun addCard(card: Card) {
        val cardKey = cardListReference.push().key
        cardListReference.child(cardKey).setValue(card.multiverseid)
        cards.add(card)
    }

    fun deleteCard(index: Int): Card {
        return cards.removeAt(index)
    }

    companion object {
        fun loadInstance(name: String, key: String): Deck {
            val deck = Deck(name, key)
            return deck

        }
    }


}

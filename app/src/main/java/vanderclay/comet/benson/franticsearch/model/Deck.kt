package vanderclay.comet.benson.franticsearch.model

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.api.CardAPI
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.api.MtgAPI
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter
import java.util.concurrent.Future

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

    // list of card ids
    var cardIds = mutableListOf<Long>()

    // index of the card that will be used for the cover
    var coverCardIndex = 0

    init {
        // if key is already defined, the deck is already in firebase
        if(key != null) {
            deckReference = mDeckDatabase.child(key)
            cardListReference = deckReference.child("cards")
        }
        else {
            // else create a new deck and add it to firebase
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
        val cardReference = cardListReference.child(cardKey)
        with(card) {
            cardReference.child("multiverse").setValue(multiverseid.toString())
            cardReference.child("id").setValue(id)
            cardReference.child("mana").setValue(manaCost)
            cardReference.child("name").setValue(name)
            cardReference.child("type").setValue(type)
            cardReference.child("set").setValue(set)
            cardReference.child("rarity").setValue(rarity)
            cardReference.child("imageUrl").setValue(imageUrl)
        }
        cards.add(card)
    }

    fun deleteCard(index: Int): Card {
        return cards.removeAt(index)
    }

    fun loadCards() {
        val apiCards = cardIds.map {
            CardAPI.getCard(it.toInt())
        }
        cards = apiCards.toMutableList()
    }

    companion object {
        fun loadInstance(name: String, key: String): Deck {
            val deck = Deck(name, key)
            return deck

        }

        fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: DeckListAdapter? = null) {
            val deckDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Decks")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)

            val valueEventListener = object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    doAsync {
                        for(snapshot: DataSnapshot in dataSnapshot.children) {
                            val deckMap = snapshot.value as Map<String, *>
                            val deck = Deck.loadInstance(deckMap["deckName"] as String, snapshot.key)
                            if(deckMap.containsKey("cards")) {
                                (deckMap["cards"] as Map<String, Map<String, String>>).forEach {
                                    val card = Card()
                                    with(it) {
                                        card.id = value["id"]
                                        card.multiverseid = value["multiverse"]!!.toInt()
                                        card.manaCost = value["mana"]
                                        card.name = value["name"]
                                        card.type = value["type"]
                                        card.set = value["set"]
                                        card.rarity = value["rarity"]
                                        card.imageUrl = value["imageUrl"]
                                    }
                                    deck.addCard(card)
                                }
                            }
                            decks.add(deck)
                        }
                        uiThread {
                            deckListAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            deckDatabaseRef.addListenerForSingleValueEvent(valueEventListener)
        }
    }


}

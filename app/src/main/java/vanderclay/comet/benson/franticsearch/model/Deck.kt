package vanderclay.comet.benson.franticsearch.model

import android.util.Log
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter


class Deck(val name: String, deckKey: String? = null) {
    private val TAG = "Deck"

    private val mDeckDatabase = FirebaseDatabase.getInstance()
            .reference
            .child("Decks")
            .child(FirebaseAuth.getInstance().currentUser?.uid)

    private val deckReference: DatabaseReference

    private val cardListReference: DatabaseReference

    var key: String? = deckKey

    var cards = mutableMapOf<Card, Long>()

    // index of the card that will be used for the cover
    var coverCardIndex = 0

    val coverCardImageUrl: String?
        get() = if(coverCardIndex < cards.size && coverCardIndex > 0) {
            cards.keys.toList()[coverCardIndex].imageUrl
        } else {
            null
        }

    init {
        // if key is already defined, the deck is already in firebase
        if (key != null) {
            deckReference = mDeckDatabase.child(key)
            cardListReference = deckReference.child("cards")
        } else {
            // else create a new deck and add it to firebase
            deckReference = mDeckDatabase.push()
            key = deckReference.key
            deckReference.child("deckName").setValue(name)
            cardListReference = deckReference.child("cards")
            cardListReference.setValue(arrayListOf<String>())
        }
        Log.d(TAG, "Deck created")
    }

    fun addCard(card: Card, firebase: Boolean=true) {
        if(firebase) {

            val cardReference = cardListReference.child(card.id)
            cardReference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot?) {
                    if(snapshot?.child("count")?.value != null) {
                        val count = snapshot.child("count").value as Long
                        snapshot.ref.child("count").setValue(count + 1)
                        return
                    }
                    with(card) {
                        with(snapshot?.ref!!) {
                            child("multiverse").setValue(multiverseid.toString())
                            child("id").setValue(id)
                            child("mana").setValue(manaCost)
                            child("name").setValue(name)
                            child("type").setValue(type)
                            child("types").setValue(types.joinToString(separator = " "))
                            child("set").setValue(set)
                            child("rarity").setValue(rarity)
                            child("imageUrl").setValue(imageUrl)
                            child("count").setValue(1)
                        }
                    }
                }

            })
        }
        if(cards.containsKey(card)){
            cards[card] = cards[card]!! + 1
        } else {
            cards[card] = 1
        }
        Log.d(TAG, "${card.name} Added to deck $name")
    }

    fun getManaTypes(): MutableSet<String> {
        val reg = Regex("[\\{\\}]")
        val manaSymbols = mutableSetOf<String>()
        cards.keys.forEach {
            if(it.manaCost == null) {
                return@forEach
            }
            val cardManaSymbols = reg.split(it.manaCost).filter(String::isNotEmpty)
            cardManaSymbols.map {
                if(it.toIntOrNull() == null && !it.equals("X")) {
                    manaSymbols.add(it)
                }
            }
        }
        return manaSymbols
    }

    fun sortByType(): Map<String, Map<Card, Long>> {
        val cardsByType = mutableMapOf<String, MutableMap<Card, Long>>()

        cards.keys.forEach {
            val type = it.types.joinToString(separator = " ")
            if(!cardsByType.containsKey(type)) {
                cardsByType[type] = mutableMapOf<Card, Long>()
            }
            if(!cardsByType[type]?.containsKey(it)!!) {
                cardsByType[type]?.set(it, 0L)
            }
            val cardCount = cards[it]
            if (cardCount != null) {
                cardsByType[type]?.set(it, cardCount)!!
            }
        }
        return cardsByType
    }

    fun deleteCard(card: Card, firebase: Boolean=true): Card? {

        if(firebase) {

        }
        if(cards.containsKey(card)) {
            if(cards[card] == 1L){
                cards.remove(card)
            }
            else {
                cards[card] = cards[card]!! - 1
            }
            return card
        }
        return null
    }

    fun loadCards() {
        val newCards = mutableMapOf<Card, Long>()
        cards.keys.forEach {
            if(cards.containsKey(it)) {
                cards[it] = cards[it]!! + 1
            }
            else {
                cards[it] = 1
            }
        }
        cards = newCards
    }

    override fun toString(): String {
        return name
    }

    companion object {

        fun loadInstance(name: String, key: String): Deck {
            val deck = Deck(name, key)
            return deck

        }

        fun getAllDecks(decks: MutableList<Deck>, callback:() -> Unit) {
            val deckDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Decks")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    doAsync {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            val deckMap = snapshot.value as Map<String, *>
                            val deck = Deck.loadInstance(deckMap["deckName"] as String, snapshot.key)
                            if (deckMap.containsKey("cards")) {
                                (deckMap["cards"] as Map<String, Map<String, *>>).forEach {
                                    val card = Card()
                                    Log.d("Deck", "${it.value["name"]} retrieved")
                                    with(it) {
                                        card.id = value["id"] as String?
                                        card.multiverseid = (value["multiverse"] as String).toInt()
                                        card.manaCost = value["mana"] as String?
                                        card.name = value["name"] as String?
                                        card.type = value["type"] as String?
                                        card.types = (value["types"] as String?)?.split(' ')?.toTypedArray()
                                        card.set = value["set"] as String?
                                        card.rarity = value["rarity"] as String?
                                        card.imageUrl = value["imageUrl"] as String?
                                    }
                                    for(i in 1..(it.value["count"] as Long)) {
                                        deck.addCard(card, false)
                                    }
                                }
                            }
                            decks.add(deck)
                        }
                        uiThread {
                            callback.invoke()
                        }
                    }
                }
            }
            deckDatabaseRef.addListenerForSingleValueEvent(valueEventListener)

        }

        fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: DeckListAdapter?) {
            getAllDecks(decks, {
                deckListAdapter?.notifyDataSetChanged()
            })

        }

        fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: BaseAdapter? = null) {
            getAllDecks(decks, {
                deckListAdapter?.notifyDataSetChanged()
            })
        }
    }



}

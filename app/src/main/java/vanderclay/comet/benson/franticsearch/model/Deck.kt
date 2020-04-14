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

    private val mDeckDatabase = FirebaseAuth.getInstance().currentUser?.uid?.let {
        FirebaseDatabase.getInstance()
            .reference
            .child("Decks")
            .child(it)
    }

    private var deckReference: DatabaseReference? = null
    private val cardListReference: DatabaseReference

    var key: String? = deckKey
    var cards = mutableMapOf<Card, Long>()

    // index of the card that will be used for the cover
    var coverCardIndex = 0

    val coverCardImageUrl: String?
        get() = if(coverCardIndex < cards.size && coverCardIndex >= 0) {
            cards.keys.toList()[coverCardIndex].imageUrl
        } else {
            null
        }

    init {
        // if key is already defined, the deck is already in firebase
        if (key != null) {
            if (mDeckDatabase != null) {
                deckReference = mDeckDatabase.child(key!!)
            }
            cardListReference = deckReference?.child("cards")!!
        } else {
            // else create a new deck and add it to firebase
            deckReference = mDeckDatabase?.push()
            key = deckReference?.key
            deckReference?.child("deckName")?.setValue(name)
            cardListReference = deckReference?.child("cards")!!
            cardListReference.setValue(arrayListOf<String>())
        }
    }

    fun addCard(card: Card, amount: Long=1, firebase: Boolean=true) {
        if(firebase) {

            val cardReference = cardListReference.child(card.id)
            cardReference.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(snapshot: DatabaseError) { }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.child("count").value != null) {
                        val count = snapshot.child("count").value as Long
                        snapshot.ref.child("count").setValue(count + 1)
                        return
                    }
                    with(card) {
                        with(snapshot.ref) {
                            child("multiverse").setValue(multiverseid.toString())
                            child("id").setValue(id)
                            child("mana").setValue(manaCost)
                            child("name").setValue(name)
                            child("type").setValue(type)
                            child("types").setValue(types.joinToString(separator = " "))
                            child("set").setValue(set)
                            child("rarity").setValue(rarity)
                            child("imageUrl").setValue(imageUrl)
                            child("count").setValue(amount)
                        }
                    }
                }

            })
        }
        if(cards.containsKey(card)){
            cards[card] = cards[card]!! + amount
        } else {
            cards[card] = amount
        }
    }

    fun getManaTypes(): MutableSet<String> {
        val reg = Regex("[{}]")
        val manaSymbols = mutableSetOf<String>()
        cards.keys.forEach { it ->
            if(it.manaCost == null) {
                return@forEach
            }
            val cardManaSymbols = reg.split(it.manaCost).filter(String::isNotEmpty)
            cardManaSymbols.map {
                if(it.toIntOrNull() == null && it != "X") {
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
                cardsByType[type] = mutableMapOf()
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

    fun deleteCard(card: Card, amount: Long=1): Card? {

        if(cards.containsKey(card)) {
            if(cards[card]!! <= amount){
                cards.remove(card)
                cardListReference.child(card.id).removeValue()
            }
            else {
                cards[card] = cards[card]!! - amount
                cardListReference.child(card.id).child("count").setValue(cards[card])
            }
            return card
        }
        return null
    }

    override fun toString(): String {
        return name
    }

    companion object {

        fun loadInstance(name: String, key: String): Deck {
            return Deck(name, key)
        }

        private fun getAllDecks(decks: MutableList<Deck>, callback:() -> Unit) {
            val deckDatabaseRef = FirebaseAuth.getInstance().currentUser?.uid?.let {
                FirebaseDatabase
                    .getInstance()
                    .getReference("Decks")
                    .child(it)
            }

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) { }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    doAsync {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            val deckMap = snapshot.value as Map<*, *>
                            val deck = snapshot.key?.let {
                                    loadInstance(deckMap["deckName"] as String,
                                        it
                                    )
                                }
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
                                    deck?.addCard(card, it.value["count"] as Long, false)
                                }
                            }
                            if (deck != null) {
                                decks.add(deck)
                            }
                        }
                        uiThread {
                            callback.invoke()
                        }
                    }
                }
            }
            deckDatabaseRef?.addListenerForSingleValueEvent(valueEventListener)

        }

        fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: DeckListAdapter?) {
            getAllDecks(decks) {
                deckListAdapter?.notifyDataSetChanged()
            }
        }

        fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: BaseAdapter? = null) {
            getAllDecks(decks) {
                deckListAdapter?.notifyDataSetChanged()
            }
        }
    }
}

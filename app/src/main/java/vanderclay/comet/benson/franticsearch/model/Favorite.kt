package vanderclay.comet.benson.franticsearch.model

import android.util.Log
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter

/**
 * Created by Ben on 4/22/2017.
 */

class Favorite {

    //tag value for debugging
    private val TAG = "Favorite"

    //Database Reference
    private val mFavoriteDatabase = FirebaseDatabase.getInstance()
            .reference
            .child("Favorites")
            .child(FirebaseAuth.getInstance().currentUser?.uid)

    fun addFavorite(card: Card, firebase: Boolean = true) {
        if (firebase) {
            mFavoriteDatabase.push().setValue(CardDO(card).toMap())
        }
    }

    private class CardDO(card: Card) {
        var id: String? = null
        var multiverseid: String? = null
        var manaCost: String? = null
        var name: String? = null
        var type: String? = null
        var types: String? = null
        var set: String? = null
        var rarity: String? = null
        var imageUrl: String? = null
        init {
            this.id = card.id
            this.imageUrl = card.imageUrl
            if(card.manaCost != null){
                this.manaCost = card.manaCost
            }else{
                this.manaCost = ""
            }

            this.multiverseid = card.multiverseid.toString()
            this.name = card.name
            this.type = card.type

            if(card.types != null){
                this.types = card.types.joinToString(" ")
            }else{
                this.types = ""
            }

            this.set = card.set
            this.rarity = card.rarity
        }

        constructor() : this(Card())

        fun toMap(): HashMap<String, String> {
            var map: HashMap<String, String> = HashMap<String, String>()
            map.put("id", id!!)
            map.put("mana", manaCost!!)
            map.put("multiverseid", multiverseid!!)
            map.put("name", name!!)
            map.put("type", type!!)
            map.put("types", types!!)
            map.put("set", set!!)
            map.put("rarity", rarity!!)
            map.put("imageUrl", imageUrl!!)
            return map
        }

        fun mapToCard(map: Map<String, *>): CardDO{
            var empty: CardDO = CardDO()
            empty.id = map["id"] as String
            empty.multiverseid = map["multiverseid"] as String
            empty.manaCost = map["mana"] as String
            empty.name = map["name"] as String
            empty.type = map["type"] as String
            empty.types = map["types"] as String
            empty.set = map["set"] as String
            empty.rarity = map["rarity"] as String
            empty.imageUrl = map["imageUrl"] as String
            return empty
        }

        fun convertCardDOtoCard(): Card{
            var card: Card = Card()
            card.id = this.id
            card.multiverseid = this.multiverseid!!.toInt()
            card.manaCost = this.manaCost
            card.name = this.name
            card.type = this.type
            card.types = types?.split("\\s+")?.toTypedArray()
            card.set = this.set
            card.rarity = this.rarity
            card.imageUrl = this.imageUrl
            return card
        }
    }

//    fun getManaTypes(): MutableSet<String> {
//        val reg = Regex("[\\{\\}]")
//        val manaSymbols = mutableSetOf<String>()
//        cards.keys.forEach {
//            if (it.manaCost == null) {
//                return@forEach
//            }
//            val cardManaSymbols = reg.split(it.manaCost).filter(String::isNotEmpty)
//            cardManaSymbols.map {
//                if (it.toIntOrNull() == null || it.equals("X")) {
//                    manaSymbols.add(it)
//                }
//            }
//        }
//        return manaSymbols
//    }
//
//    fun sortByType(): Map<String, Map<Card, Long>> {
//        val cardsByType = mutableMapOf<String, MutableMap<Card, Long>>()
//
//        cards.keys.forEach {
//            val type = it.types.joinToString(separator = " ")
//            if (!cardsByType.containsKey(type)) {
//                cardsByType[type] = mutableMapOf<Card, Long>()
//            }
//            if (!cardsByType[type]?.containsKey(it)!!) {
//                cardsByType[type]?.set(it, 0L)
//            }
//            val cardCount = cardsByType[type]?.get(it)
//            if (cardCount != null) {
//                cardsByType[type]?.set(it, cardCount.plus(1))!!
//            }
//        }
//        return cardsByType
//    }
//
//    fun deleteCard(card: Card, firebase: Boolean = true): Card? {
//
//        if (firebase) {
//
//        }
//        if (cards.containsKey(card)) {
//            if (cards[card] == 1L) {
//                cards.remove(card)
//            } else {
//                cards[card] = cards[card]!! - 1
//            }
//            return card
//        }
//        return null
//    }
//
//    fun loadCards() {
//        val newCards = mutableMapOf<Card, Long>()
//        cards.keys.forEach {
//            if (cards.containsKey(it)) {
//                cards[it] = cards[it]!! + 1
//            } else {
//                cards[it] = 1
//            }
//        }
//        cards = newCards
//    }
//
////    override fun toString(): String {
////        return name
////    }

    companion object {
//        favorites: MutableList<Card>, callback: () -> Unit
        fun getAllFavorites() {
            val favoritesDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Favorites")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    doAsync {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
//                            val deckMap = snapshot.value as Map<String, *>
//                            val deck = Deck.loadInstance(deckMap["deckName"] as String, snapshot.key)
//                            val cardDO: CardDO = CardDO()
//                            val card = Card()
                            Log.d("Favorites", snapshot.value.toString())
                            val card = snapshot.value as Map<String, *>
                            CardDO().mapToCard(card)
//                            for(snapshot: DataSnapshot in snapshot.children){
//                                Log.d("Favorites", snapshot.value.toString())
//                            }
//                                card.id = value["id"] as String?
//                                card.multiverseid = (value["multiverse"] as String).toInt()
//                                card.manaCost = value["mana"] as String?
//                                card.name = value["name"] as String?
//                                card.type = value["type"] as String?
//                                card.types = (value["types"] as String?)?.split(' ')?.toTypedArray()
//                                card.set = value["set"] as String?
//                                card.rarity = value["rarity"] as String?
//                                card.imageUrl = value["imageUrl"] as String?

//
                        }
//                        decks.add(deck)
                    }
//                    uiThread {
//                        callback.invoke()
//                    }
                }
            }
            favoritesDatabaseRef.addListenerForSingleValueEvent(valueEventListener)
        }

//    fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: DeckListAdapter?) {
//        getAllDecks(decks, {
//            deckListAdapter?.notifyDataSetChanged()
//        })
//
//    }
//
//    fun getAllDecks(decks: MutableList<Deck>, deckListAdapter: BaseAdapter? = null) {
//        getAllDecks(decks, {
//            deckListAdapter?.notifyDataSetChanged()
//        })
//    }
    }
}
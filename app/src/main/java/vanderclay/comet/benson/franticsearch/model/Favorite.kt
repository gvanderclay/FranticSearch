package vanderclay.comet.benson.franticsearch.model

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder
import kotlin.jvm.internal.Ref

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

    fun addFavorite(card: Card) {
        mFavoriteDatabase.child(card.id).setValue(CardDO(card).toMap())
    }

    fun removeFavorite(card: Card) {
        mFavoriteDatabase.child(card.id).removeValue()
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
        var power: String? = null
        var toughness: String? = null
        var abiltity: String? = null

        init {
            this.id = card.id
            if (card?.originalText != null) {
                this.abiltity = card.originalText
            } else {
                this.abiltity = " no ability "
            }

            //Some cards don't load in image urls which
            //causes the application to crash when the user taps favorites
            if(card.imageUrl != null){
                this.imageUrl = card.imageUrl
            }else{
                this.imageUrl = ""
            }

            if(card.toughness != null){
                this.toughness = card.toughness.toString()
            }else{
                this.toughness = ""
            }

            if(card.power != null){
                this.power = card.power
            }else{
                this.power = ""
            }

            //Lands Don't have a Mana cost
            if (card.manaCost != null) {
                this.manaCost = card.manaCost
            } else {
                this.manaCost = ""
            }

            //Don't need to worry about it, since it's initialized to -1
            this.multiverseid = card.multiverseid.toString()

            //Every card has a name
            this.name = card.name
            this.type = card.type

            //Not all cards have types.
            if (card.types != null) {
                this.types = card.types.joinToString(" ")
            } else {
                this.types = ""
            }
            //Every card has a set and rarity so we don't need to check
            this.set = card.set
            this.rarity = card.rarity
        }

        constructor() : this(Card())

        fun toMap(): HashMap<String, String> {
            var map: HashMap<String, String> = HashMap<String, String>()
            map.put("id", id!!)
            map.put("mana", manaCost!!)
            map.put("multiverseid", multiverseid!!)
            map.put("power", power!!)
            map.put("toughness", toughness!!)
            map.put("name", name!!)
            map.put("type", type!!)
            map.put("types", types!!)
            map.put("set", set!!)
            map.put("rarity", rarity!!)
            map.put("imageUrl", imageUrl!!)
            map.put("originalText", abiltity!!)
            return map
        }

        fun mapToCard(map: Map<String, *>): CardDO {
            var empty: CardDO = CardDO()
            empty.id = map["id"] as String
            empty.multiverseid = map["multiverseid"] as String
            empty.power = map["power"] as String
            empty.toughness = map["toughness"] as String
            empty.manaCost = map["mana"] as String
            empty.name = map["name"] as String
            empty.type = map["type"] as String
            empty.types = map["types"] as String
            empty.set = map["set"] as String
            empty.rarity = map["rarity"] as String
            empty.imageUrl = map["imageUrl"] as String
            empty.abiltity = map["originalText"] as String
            if(empty.imageUrl.equals("")){
                empty.imageUrl = null
            }

            return empty
        }

        fun convertCardDOtoCard(): Card {
            var card: Card = Card()
            card.id = this.id
            card.multiverseid = this.multiverseid!!.toInt()
            card.manaCost = this.manaCost
            card.name = this.name
            card.type = this.type
            card.power = this.power
            card.toughness = this.toughness
            card.types = types?.split("\\s+")?.toTypedArray()
            card.set = this.set
            card.rarity = this.rarity
            card.imageUrl = this.imageUrl
            card.originalText = this.abiltity
            return card
        }
    }


    companion object {
        fun getAllFavorites(favorites: MutableList<Card>, recycler: RecyclerView.Adapter<CardViewHolder>) {

            val favoritesDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Favorites")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        Log.d("Favorites", snapshot.value.toString())
                        var card = Card()
                        var convertedCard = CardDO()
                        try {
                            card = convertedCard.mapToCard(snapshot.value as Map<String, *>).convertCardDOtoCard()
                            favorites.add(card)
                        } catch(e: Exception) {
                            Log.d("Favorites", convertedCard.toString())
                        }
                    }
                    recycler.notifyDataSetChanged()

                }
            }

            favoritesDatabaseRef.addListenerForSingleValueEvent(valueEventListener)
        }

        fun findCardById(primaryKey: String, callback: (favorited: Boolean) -> Unit){
            var result = false
            val favoritesDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Favorites")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)
                    .child(primaryKey)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value != null){
                        Log.d("Favorites", dataSnapshot.value.toString())
                        callback(true)
                    }else {
                        callback(false)
                    }
                    result = true
                }
            }

            favoritesDatabaseRef.addValueEventListener(valueEventListener)
//            return result
        }
    }
}

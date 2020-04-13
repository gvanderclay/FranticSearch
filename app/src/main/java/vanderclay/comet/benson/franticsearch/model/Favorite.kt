package vanderclay.comet.benson.franticsearch.model

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder

@Suppress("UNCHECKED_CAST")
class Favorite {

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
            if (card.originalText != null) {
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
            val map: HashMap<String, String> = HashMap()
            map["id"] = id!!
            map["mana"] = manaCost!!
            map["multiverseid"] = multiverseid!!
            map["power"] = power!!
            map["toughness"] = toughness!!
            map["name"] = name!!
            map["type"] = type!!
            map["types"] = types!!
            map["set"] = set!!
            map["rarity"] = rarity!!
            map["imageUrl"] = imageUrl!!
            map["originalText"] = abiltity!!
            return map
        }

        fun mapToCard(map: Map<String, *>): CardDO {
            val empty = CardDO()
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
            val card = Card()
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
                override fun onCancelled(p0: DatabaseError?) { }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        Log.d("Favorites", snapshot.value.toString())
                        var card: Card
                        val convertedCard = CardDO()
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
            val favoritesDatabaseRef = FirebaseDatabase
                    .getInstance()
                    .getReference("Favorites")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)
                    .child(primaryKey)

            val valueEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) { }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.value != null){
                        Log.d("Favorites", dataSnapshot.value.toString())
                        callback(true)
                    }else {
                        callback(false)
                    }
                }
            }

            favoritesDatabaseRef.addValueEventListener(valueEventListener)
        }
    }
}

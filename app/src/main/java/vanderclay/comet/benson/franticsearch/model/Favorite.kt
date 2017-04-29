package vanderclay.comet.benson.franticsearch.model

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.commons.cardToMap
import vanderclay.comet.benson.franticsearch.commons.mapToCard
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
        mFavoriteDatabase.child(card.id).setValue(cardToMap(card))
    }

    fun removeFavorite(card: Card) {
        mFavoriteDatabase.child(card.id).removeValue()
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
                        val card = mapToCard(snapshot.value as Map<String, Object>)
                        try {
                            favorites.add(card)
                        } catch(e: Exception) {
                            Log.e("Favorites", card.toString())
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

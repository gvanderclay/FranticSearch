package vanderclay.comet.benson.franticsearch.ui.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder

class FavoriteListAdapter(val cards: MutableList<Card>): RecyclerView.Adapter<CardViewHolder>() {

    private var mCards = cards

    private val favoriteListTag = "FavoriteListAdapter"

    override fun getItemCount(): Int {
        return mCards.size
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val card = mCards[position]
        holder.bind(card)

        holder.itemView.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context!!)
            alertDialogBuilder.setMessage("Delete Favorite? ${card.name}?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                FirebaseDatabase
                    .getInstance()
                    .getReference("Favorites")
                    .child(FirebaseAuth.getInstance().currentUser?.uid)
                    .child(card.id)
                    .removeValue()
                cards.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cards.size)
                notifyDataSetChanged()

                //                val valueEventListener = object : ValueEventListener {
                //                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                //                        for (postSnapshot in dataSnapshot.children) {
                //                            FirebaseDatabase
                //                                    .getInstance()
                //                                    .getReference("Favorites")
                //                                    .child(FirebaseAuth.getInstance().currentUser?.uid)
                //                                    .child(postSnapshot.key)
                //                                    .removeValue()
                //                            val cardRemoved = cards.removeAt(position)
                //                            Log.d(TAG, cardRemoved.toString())
                //                            notifyItemRemoved(position)
                //                            notifyItemRangeChanged(position, cards.size)
                //                            notifyDataSetChanged()
                //                        }
                //
                //                    }
                //                    override fun onCancelled(databaseError: DatabaseError) {
                //                        Log.d(TAG, databaseError.toString())
                //                    }
                //                }
                //
                //                favoriteCardReference.addValueEventListener(valueEventListener)
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ ->
                Log.d(favoriteListTag, "Remove deck cancelled")
            }
            alertDialogBuilder.create().show()
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemCardRowBinding.inflate(layoutInflater, parent, false)
        return CardViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}


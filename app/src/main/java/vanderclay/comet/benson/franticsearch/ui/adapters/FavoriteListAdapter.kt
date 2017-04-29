package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder


class FavoriteListAdapter(val cards: MutableList<Card>): RecyclerView.Adapter<CardViewHolder>() {

    private var mCards = cards

    private val TAG = "FavoriteListAdapter"


    override fun getItemCount(): Int {
        return mCards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        val card = mCards[position]
        holder?.bind(card)

        holder?.itemView?.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView?.context!!)
            alertDialogBuilder.setMessage("Delete Favorite? ${card.name}?")
            alertDialogBuilder.setPositiveButton("Yes", { _, _ ->
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

            })
            alertDialogBuilder.setNegativeButton("No", { _, _ ->
                Log.d(TAG, "Remove deck cancelled")
            })
            alertDialogBuilder.create().show()
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemBinding = ItemCardRowBinding.inflate(layoutInflater, parent, false)
        return CardViewHolder(itemBinding)
    }
}


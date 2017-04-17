package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckViewHolder



class DeckListAdapter(decks: MutableList<Deck>): RecyclerView.Adapter<DeckViewHolder>(){

    private val mDecks = decks

    private val TAG = "DeckListAdapter"

    override fun getItemCount(): Int {
        return mDecks.size
    }

    override fun onBindViewHolder(holder: DeckViewHolder?, position: Int) {
        Log.d(TAG, "onBindViewHolder $position")
        val deck = mDecks[position]
        holder?.bind(deck)
        holder?.itemView?.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView?.context!!)
            alertDialogBuilder.setMessage("Delete Deck ${deck.name}?")
            alertDialogBuilder.setPositiveButton("Yes", { dialog, which ->
                val deck = mDecks.removeAt(position)

                FirebaseDatabase
                        .getInstance()
                        .getReference("Decks")
                        .child(FirebaseAuth.getInstance().currentUser?.uid)
                        .child(deck.key)
                        .removeValue()

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, mDecks.size)
            })
            alertDialogBuilder.setNegativeButton("No", { dialog, which ->
                Log.d(TAG, "Add deck cancelled")
            })
            alertDialogBuilder.create().show()
            true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeckViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemBinding = ItemDeckRowBinding.inflate(layoutInflater, parent, false)
        return DeckViewHolder(itemBinding)
    }


}
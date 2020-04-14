package vanderclay.comet.benson.franticsearch.ui.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckViewHolder

class DeckListAdapter(decks: MutableList<Deck>): RecyclerView.Adapter<DeckViewHolder>(){

    private val mDecks = decks

    private val deckListTag = "DeckListAdapter"

    override fun getItemCount(): Int {
        return mDecks.size
    }

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        Log.d(deckListTag, "onBindViewHolder $position")
        val deck = mDecks[position]
        holder.bind(deck)
        holder.itemView.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context!!)
            alertDialogBuilder.setMessage("Delete Deck ${deck.name}?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                mDecks.removeAt(position)

                FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                    deck.key?.let { it2 ->
                        FirebaseDatabase
                            .getInstance()
                            .getReference("Decks")
                            .child(it1)
                            .child(it2)
                            .removeValue()
                    }
                }
                Log.d(deckListTag, "Removing deck ${deck.name}")

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, mDecks.size)
            }
            alertDialogBuilder.setNegativeButton("No") { _, _ ->
                Log.d(deckListTag, "Remove deck cancelled")
            }
            alertDialogBuilder.create().show()
            true
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemDeckRowBinding.inflate(layoutInflater, parent, false)
        return DeckViewHolder(itemBinding)
    }

}
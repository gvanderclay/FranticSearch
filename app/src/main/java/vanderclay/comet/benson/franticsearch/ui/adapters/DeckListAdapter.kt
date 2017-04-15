package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import kotlinx.android.synthetic.main.item_deck_row.*
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckViewHolder

class DeckListAdapter(decks: MutableList<Deck>): RecyclerSwipeAdapter<DeckViewHolder>(){

    private val mDecks = decks

    private val TAG = "DeckListAdapter"

    override fun getItemCount(): Int {
        return mDecks.size
    }

    override fun onBindViewHolder(holder: DeckViewHolder?, position: Int) {
        val deck = mDecks[position]
        holder?.bind(deck)
        val deckDelete = holder?.itemView?.findViewById(R.id.deckDelete) as ImageView?
        deckDelete?.setOnClickListener { view ->
            mItemManger.removeShownLayouts(holder?.swipeLayout)
            mDecks.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mDecks.size)
            mItemManger.closeAllItems()
            Toast.makeText(view?.context, "${deck.name} Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeckViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemBinding = ItemDeckRowBinding.inflate(layoutInflater, parent, false)
        return DeckViewHolder(itemBinding)
    }

    override fun getSwipeLayoutResourceId(position: Int): Int {
        Log.d(TAG, "getswipelayoutid")
        return position
    }

    override fun openItem(position: Int) {
        super.openItem(position)
        Log.d(TAG, "Open Items ${openItems.size}")
    }

}
package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckViewHolder

/**
 * Created by gclay on 4/14/17.
 */

class DeckListAdapter(decks: MutableList<Deck>): RecyclerView.Adapter<DeckViewHolder>(){

    private val mDecks = decks

    override fun getItemCount(): Int {
        return mDecks.size
    }

    override fun onBindViewHolder(holder: DeckViewHolder?, position: Int) {
        val deck = mDecks[position]
        holder?.bind(deck)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeckViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemBinding = ItemDeckRowBinding.inflate(layoutInflater, parent, false)
        return DeckViewHolder(itemBinding)
    }

}
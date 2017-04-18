package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding

/**
 * Created by gclay on 4/5/17.
 */

class CardListAdapter(val cards: MutableList<Card>): RecyclerView.Adapter<CardViewHolder>() {

    private var mCards = cards

    private val TAG = "CardListAdapter"


    override fun getItemCount(): Int {
        return mCards.size
    }

    override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
        val card = mCards[position]
        holder?.bind(card)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemBinding = ItemCardRowBinding.inflate(layoutInflater, parent, false)
        return CardViewHolder(itemBinding)
    }
}


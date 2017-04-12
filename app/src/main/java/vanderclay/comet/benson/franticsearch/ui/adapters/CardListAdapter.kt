package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
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

    fun animateTo(cards: List<Card>){
        Log.d(TAG, "Size of cards ${cards.size}")
        applyAndAnimateRemovals(cards)
        applyAndAnimateAdditions(cards)
        applyAndAnimateMovedItems(cards)
        notifyDataSetChanged()
        Log.d(TAG, "Size of cards ${cards.size}")
    }

    private fun applyAndAnimateRemovals(newCards: List<Card>) {
        for(i in mCards.size - 1 downTo 0) {
            val card = mCards[i]
            if(!newCards.contains(card)) {
                removeItem(i)
           }
        }
    }

    private fun applyAndAnimateAdditions(newCards: List<Card>) {
        for(i in 0 until newCards.size) {
            val card = newCards[i]
            if(!mCards.contains(card)) {
                addItem(i, card)
            }
        }
    }

    private fun applyAndAnimateMovedItems(newCards: List<Card>) {
        for(toPosition in newCards.size - 1 downTo 0) {
            val card = newCards[toPosition]
            val fromPosition = newCards.indexOf(card)
            if(fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    fun removeItem(position: Int): Card {
        val card = mCards.removeAt(position)
        Log.d(TAG, "removing card ${card.name}")
        notifyItemRemoved(position)
        return card
    }

    fun addItem(position: Int, card: Card) {
        mCards.add(position, card)
        Log.d(TAG, "adding card ${card.name}")
        notifyItemInserted(position)
    }

    fun moveItem(from: Int, to: Int) {
        val card = mCards.removeAt(from)
        Log.d(TAG, "moving card ${card.name}")
        mCards.add(to, card)
        notifyItemMoved(from, to)
    }



}
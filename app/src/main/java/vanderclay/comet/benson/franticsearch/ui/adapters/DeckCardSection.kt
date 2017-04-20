package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardHeaderViewHolder
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardViewHolder


class DeckCardSection(val title: String, val deck: Deck?): StatelessSection(R.layout.deck_card_list_header, R.layout.card_deck_row) {

    override fun getContentItemsTotal(): Int {
        return deck?.cards!!.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val deckCardView = holder as DeckCardViewHolder
        val card = deck!!.cards.keys.toList()[position]

        deckCardView.cardText.text = card.name + " " + deck.cards[card]

    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return DeckCardViewHolder(view!!)
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return DeckCardHeaderViewHolder(view!!)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        super.onBindHeaderViewHolder(holder)
        val titleHolder = holder as DeckCardHeaderViewHolder
        titleHolder.title.text = title
    }

}
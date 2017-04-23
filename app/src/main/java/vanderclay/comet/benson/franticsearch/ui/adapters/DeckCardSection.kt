package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardHeaderViewHolder
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardViewHolder


class DeckCardSection(val title: String, val cards: Map<Card, Long>?): StatelessSection(R.layout.deck_card_list_header, R.layout.card_deck_row) {

    override fun getContentItemsTotal(): Int {
        return cards!!.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val deckCardView = holder as DeckCardViewHolder
        val card = cards?.keys?.toList()!![position]

        deckCardView.cardText.text = card.name
        deckCardView.cardCountText.text = cards[card].toString()
        Picasso.with(deckCardView.itemView.context)
                .load(card.imageUrl)
                .transform(CardImageTransform())
                .placeholder(R.drawable.no_card)
                .into(deckCardView.cardImage)
        deckCardView.cardImage.scaleType = ImageView.ScaleType.FIT_XY
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
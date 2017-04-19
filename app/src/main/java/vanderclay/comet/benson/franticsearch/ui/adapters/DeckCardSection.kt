package vanderclay.comet.benson.franticsearch.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder

/**
 * Created by Gage Vander Clay on 4/18/2017.
 */

class DeckCardSection(val title: String, val cards: List<Card>): StatelessSection(R.layout.deck_card_list_header, R.layout.item_card_row) {

    override fun getContentItemsTotal(): Int {
        return cards.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
    }

}
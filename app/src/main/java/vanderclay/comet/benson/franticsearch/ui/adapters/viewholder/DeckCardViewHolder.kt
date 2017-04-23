package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.card_deck_row.view.*
import org.jetbrains.anko.find
import vanderclay.comet.benson.franticsearch.R

/**
 * Created by gclay on 4/19/17.
 */

class DeckCardViewHolder(cardDeckRow: View): ViewHolder(cardDeckRow) {

    val cardText: TextView = cardDeckRow.findViewById(R.id.deckCardText) as TextView
    val cardCountText: TextView = cardDeckRow.findViewById(R.id.cardCount) as TextView
    val cardImage: ImageView = cardDeckRow.findViewById(R.id.deckCardImage) as ImageView


}

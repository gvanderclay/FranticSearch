package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import vanderclay.comet.benson.franticsearch.R

/**
 * Created by gclay on 4/19/17.
 */

class DeckCardViewHolder(cardDeckRow: View): ViewHolder(cardDeckRow) {

    val cardText = cardDeckRow.findViewById(R.id.deckCardText) as TextView
    val cardImage = cardDeckRow.findViewById(R.id.deckCardImage) as ImageView
    val manaContainer = cardDeckRow.findViewById(R.id.deckCardManaContainer) as LinearLayout?
}

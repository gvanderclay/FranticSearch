package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vanderclay.comet.benson.franticsearch.R

class DeckCardViewHolder(cardDeckRow: View): RecyclerView.ViewHolder(cardDeckRow) {

    val cardText = cardDeckRow.findViewById(R.id.deckCardText) as TextView
    val cardImage = cardDeckRow.findViewById(R.id.deckCardImage) as ImageView
    val manaContainer = cardDeckRow.findViewById(R.id.deckCardManaContainer) as LinearLayout?
}

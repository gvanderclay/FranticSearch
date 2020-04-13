package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vanderclay.comet.benson.franticsearch.R

class DeckCardHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val title = view.findViewById(R.id.cardTypeTitle) as TextView
}

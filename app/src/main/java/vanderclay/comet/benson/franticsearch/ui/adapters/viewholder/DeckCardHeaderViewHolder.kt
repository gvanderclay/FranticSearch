package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import vanderclay.comet.benson.franticsearch.R

/**
 * Created by gclay on 4/19/17.
 */

class DeckCardHeaderViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val title = view.findViewById(R.id.cardTypeTitle) as TextView
}

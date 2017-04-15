package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.daimajia.swipe.SwipeLayout
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck

/**
 * Created by gclay on 4/14/17.
 */
class DeckViewHolder(binding: ItemDeckRowBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private val mBinding = binding

    var swipeLayout: SwipeLayout? = null

    fun bind(deck: Deck) {
        swipeLayout = mBinding.deckSwipe
        mBinding.deck = deck
    }

    override fun onClick(v: View?) {
    }

}

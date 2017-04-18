package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.squareup.picasso.Picasso
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck

/**
 * Created by gclay on 4/14/17.
 */
class DeckViewHolder(binding: ItemDeckRowBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private val mBinding = binding


    fun bind(deck: Deck) {
        mBinding.deck = deck
    }

    override fun onClick(v: View?) {
    }

}

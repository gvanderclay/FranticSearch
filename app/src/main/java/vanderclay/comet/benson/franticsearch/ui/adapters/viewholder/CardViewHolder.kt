package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import vanderclay.comet.benson.franticsearch.data.domain.Card
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ItemCardRowBinding = binding



    // Bind a card to the ItemCardRow
    fun bind(card: Card) {
        mBinding.card = card
    }

}

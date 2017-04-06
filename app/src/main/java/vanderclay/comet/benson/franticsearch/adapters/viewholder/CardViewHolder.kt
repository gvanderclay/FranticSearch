package vanderclay.comet.benson.franticsearch.adapters.viewholder

import android.content.ClipData
import android.support.v7.widget.RecyclerView
import vanderclay.comet.benson.franticsearch.data.db.CardDO
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ItemCardRowBinding = binding



    // Bind a card to the ItemCardRow
    fun bind(card: CardDO) {
        mBinding.card = card
    }

}

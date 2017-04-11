package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ItemCardRowBinding = binding



    // Bind a card to the ItemCardRow
    fun bind(card: Card) {
        mBinding.card = card
        Picasso.with(mBinding.root.context).load(card.imageUrl).into(mBinding.cardImage)
    }

}

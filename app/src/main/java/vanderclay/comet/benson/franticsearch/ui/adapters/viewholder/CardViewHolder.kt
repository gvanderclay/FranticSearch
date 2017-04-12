package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.activities.CircleTransform

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ItemCardRowBinding = binding

    private val TAG = "CardViewHolder"


    // Bind a card to the ItemCardRow
    fun bind(card: Card) {
        mBinding.card = card
        val imageView = ImageView(mBinding.root.context)
        imageView.layoutParams = ViewGroup.LayoutParams(10, 10)
        mBinding.manaContainer.addView(imageView)
        Picasso.with(mBinding.root.context)
                .load(card.imageUrl)
                .transform(CircleTransform())
                .placeholder(R.drawable.no_card)
                .into(mBinding.cardImage)
        mBinding.cardImage.scaleType = ImageView.ScaleType.FIT_XY
        if (card.imageUrl != null) {
            mBinding.missingText.text = ""
        }
    }

}

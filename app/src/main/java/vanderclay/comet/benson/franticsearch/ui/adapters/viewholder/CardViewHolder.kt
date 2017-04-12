package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root) {
    private val mBinding: ItemCardRowBinding = binding

    private val TAG = "CardViewHolder"

    private val reg = Regex("[\\{\\}]")

    // Bind a card to the ItemCardRow
    fun bind(card: Card) {
        mBinding.card = card
        val imageView = ImageView(mBinding.root.context)
        addManaSymbols()
        imageView.setImageResource(R.drawable.white_circle)
        imageView.layoutParams = ViewGroup.LayoutParams(50, 50)
        mBinding.manaContainer.removeAllViews()
        mBinding.manaContainer.addView(imageView)

        if (card.imageUrl != null) {
            mBinding.missingText.text = ""
        }
        Picasso.with(mBinding.root.context)
                .load(card.imageUrl)
                .transform(CardImageTransform())
                .placeholder(R.drawable.no_card)
                .into(mBinding.cardImage)
        mBinding.cardImage.scaleType = ImageView.ScaleType.FIT_XY
    }

    private fun addManaSymbols() {
        // our regex includes empty string so filter them out
        if(mBinding.card.manaCost == null) {
            return
        }
        val manaSymbols = reg.split(mBinding.card.manaCost).filter {
            it.length > 0
        }
        Log.d(TAG, "Mana: ${manaSymbols}")
    }

}

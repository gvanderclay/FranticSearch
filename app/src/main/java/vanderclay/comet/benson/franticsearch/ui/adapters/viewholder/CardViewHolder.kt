package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.SetCache
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform

/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private val mBinding: ItemCardRowBinding = binding

    private val TAG = "CardViewHolder"

    private val reg = Regex("[\\{\\}]")


    // Bind a card to the ItemCardRow
    fun bind(card: Card) {

        mBinding.root.setOnClickListener(this)
        mBinding.card = card
        mBinding.manaContainer.removeAllViews()
        addManaSymbols()

        if (card.imageUrl != null) {
            mBinding.missingText.text = ""
        }
        Picasso.with(mBinding.root.context)
                .load(card.imageUrl)
                .transform(CardImageTransform())
                .placeholder(R.drawable.no_card)
                .into(mBinding.cardImage)
        mBinding.cardImage.scaleType = ImageView.ScaleType.FIT_XY
        Picasso.with(mBinding.root.context)
                .load("http://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=${getSetCode()}&size=large&rarity=${getRaritySymbol()}")
                .into(mBinding.setImage)
    }

    override fun onClick(view: View) {
        Log.d(TAG, "Set ${getSetCode()}:${mBinding.card.set} rarity ${mBinding.card.rarity}")
    }

    private fun addManaSymbols() {
        // our regex includes empty string so filter them out
        if(mBinding.card.manaCost == null) {
            return
        }
        val manaSymbols = reg.split(mBinding.card.manaCost).filter(String::isNotEmpty)
        for (manaType in manaSymbols) {
            val url = "http://gatherer.wizards.com/Handlers/Image.ashx?size=large&name=$manaType&type=symbol"
            val imageView = ImageView(mBinding.root.context)
            imageView.setImageResource(R.drawable.white_circle)
            imageView.layoutParams = ViewGroup.LayoutParams(50, 50)
            Picasso.with(mBinding.root.context)
                    .load(url)
                    .into(imageView)
            mBinding.manaContainer.addView(imageView)
            Log.d(TAG, "Mana: $manaType")
        }
    }

    private fun getSetCode(): String? {
        val card = mBinding.card
        val gathererCode = SetCache.getSets()?.get(card.set)?.gatherercode
        return gathererCode ?: card.set
    }

    private fun getRaritySymbol(): String {
        val rarity = mBinding.card.rarity
        return when(rarity){
            "Special" -> rarity
            else -> rarity.split(" ").map {
                         it[0]
                    }.joinToString("")
        }
    }


}

package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.ui.fragments.CardFragment
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.SetCache
import vanderclay.comet.benson.franticsearch.databinding.ItemCardRowBinding
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform
import android.app.Activity
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols


/**
 * Created by gclay on 4/5/17.
 */

class CardViewHolder(binding: ItemCardRowBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private val mBinding: ItemCardRowBinding = binding

    private val TAG = "CardViewHolder"



    // Bind a card to the ItemCardRow
    fun bind(card: Card) {

        mBinding.root.setOnClickListener(this)
        mBinding.card = card
        mBinding.manaContainer.removeAllViews()
        addManaSymbols(card, mBinding.root.context, mBinding.manaContainer)

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
        val newFragment = CardFragment()
        newFragment.card = mBinding.card
        val manager = (mBinding.root.context as MainActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.flContent, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        Log.d(TAG, "Set ${getSetCode()}:${mBinding.card.set} rarity ${mBinding.card.rarity}")
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

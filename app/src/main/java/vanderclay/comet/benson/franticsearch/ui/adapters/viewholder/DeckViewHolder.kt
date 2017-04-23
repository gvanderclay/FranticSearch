package vanderclay.comet.benson.franticsearch.ui.adapters.viewholder

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.databinding.ItemDeckRowBinding
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity
import vanderclay.comet.benson.franticsearch.ui.fragments.DeckFragment

/**
 * Created by gclay on 4/14/17.
 */
class DeckViewHolder(binding: ItemDeckRowBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private val TAG = "DeckViewHolder"

    private val mBinding = binding


    fun bind(deck: Deck) {
        mBinding.root.setOnClickListener(this)
        mBinding.deck = deck
        if(deck.coverCardIndex < deck.cards.size && deck.cards.size >= 0) {
            val imageUrl = deck.coverCardImageUrl
            Picasso.with(mBinding.root.context)
                    .load(imageUrl)
                    .transform(CardImageTransform())
                    .placeholder(R.drawable.no_card)
                    .into(mBinding.deckListCardImage)
            mBinding.deckListCardImage.scaleType = ImageView.ScaleType.FIT_XY
            addManaSymbols(deck.getManaTypes(), mBinding.root.context, mBinding.deckListManaContainer)
        }
    }

    override fun onClick(v: View?) {
        val newFragment = DeckFragment.newInstance()
        newFragment.deck = mBinding.deck
        val manager = (mBinding.root.context as MainActivity).supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.flContent, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}

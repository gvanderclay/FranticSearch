package vanderclay.comet.benson.franticsearch.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.card_item_row.*
import vanderclay.comet.benson.franticsearch.R

/**
 * Created by Gage Vander Clay on 4/2/2017.
 */

class CardAdapter(cards: ArrayList<Card>): RecyclerView.Adapter<CardAdapter.CardHolder>() {

    private var cards: ArrayList<Card> = cards



    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: CardHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class CardHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
        private val mItemImage: ImageView
        private val mItemDate: TextView
        private val mItemDescription: TextView
        private val mCard: Card

        init {
            mItemImage = view.findViewById(R.id.item_image) as ImageView
            mItemDate = view.findViewById(R.id.item_date) as TextView
            mItemDescription = view.findViewById(R.id.item_description) as TextView
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("RecyclerView", "Click!")
        }

    }
}
package vanderclay.comet.benson.franticsearch.ui.adapters

import android.content.DialogInterface
import android.content.res.Configuration
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardImageTransform
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardHeaderViewHolder
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.DeckCardViewHolder


class DeckCardSection(val title: String, val cards: MutableMap<Card, Long>?, val deck: Deck, val adapter: SectionedRecyclerViewAdapter): StatelessSection(R.layout.deck_card_list_header, R.layout.card_deck_row) {

    private val TAG = "DeckCardSection"

    override fun getContentItemsTotal(): Int {
        return cards!!.size
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val deckCardView = holder as DeckCardViewHolder
        val card = cards?.keys?.toList()!![position]
        Log.d(TAG, "Binding DeckCardViewHolder with ${card.name}")

        deckCardView.cardText.text = "${cards[card]}\t${card.name}"
        Picasso.with(deckCardView.itemView.context)
                .load(card.imageUrl)
                .transform(CardImageTransform())
                .placeholder(R.drawable.no_card)
                .into(deckCardView.cardImage)
        deckCardView.cardImage.scaleType = ImageView.ScaleType.FIT_XY
        deckCardView.manaContainer?.removeAllViews()
        addManaSymbols(card, deckCardView.itemView.context, deckCardView.manaContainer)
        deckCardView.itemView.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView?.context!!)
            alertDialogBuilder.setMessage("Delete Card ${card.name}?")
            alertDialogBuilder.setPositiveButton("Yes", { _, _ ->
                val innerDialog = AlertDialog.Builder(holder.itemView?.context!!)
                val amountInput = EditText(holder.itemView?.context)
                innerDialog.setTitle("Enter number of cards to delete")
                amountInput.inputType = InputType.TYPE_CLASS_NUMBER
                amountInput.setRawInputType(Configuration.KEYBOARD_12KEY)
                amountInput.setText("")
                amountInput.append("1")
                innerDialog.setView(amountInput)
                innerDialog.setPositiveButton("Delete", { _, _ ->
                    if(amountInput.text.isEmpty()) {
                        return@setPositiveButton
                    }
                    deck.deleteCard(card, amountInput.text.toString().toLong())

                    if(deck.cards.contains(card)){
                        cards[card] = deck.cards[card]!!
                    } else{
                        cards.remove(card)
                    }
                    adapter.notifyDataSetChanged()
                })
                innerDialog.setNegativeButton("Cancel", { _, _ ->
                    Log.d(TAG, "Remove card cancelled")
                })
                innerDialog.show()

                Log.d(TAG, "Removingcard ${card.name}")
            })
            alertDialogBuilder.setNegativeButton("No", { _, _ ->
                Log.d(TAG, "Remove card cancelled")
            })
            alertDialogBuilder.create().show()
            true
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return DeckCardViewHolder(view!!)
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return DeckCardHeaderViewHolder(view!!)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        super.onBindHeaderViewHolder(holder)
        val titleHolder = holder as DeckCardHeaderViewHolder
        titleHolder.title.text = title
    }

}
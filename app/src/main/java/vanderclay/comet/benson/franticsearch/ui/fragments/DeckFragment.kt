package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.toolbar.*

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckCardSection


/**
 * A simple [Fragment] subclass.
 */
class DeckFragment : Fragment() {

    var deck: Deck? = null
    var sectionAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_deck, container, false)

        activity.toolbar.title = deck?.name
        addManaSymbols(deck?.getManaTypes()!!,
                rootView.context,
                (rootView.findViewById(R.id.deckManaContainer) as LinearLayout))
        

        deck?.sortByType()?.forEach {
            val deckCardSection = DeckCardSection(it.key, it.value)
            sectionAdapter.addSection(deckCardSection)
        }

        val recyclerView = rootView.findViewById(R.id.cardDeckRecyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = sectionAdapter


        return rootView
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment DeckListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): DeckFragment {
            val fragment = DeckFragment()
            return fragment
        }
    }
}

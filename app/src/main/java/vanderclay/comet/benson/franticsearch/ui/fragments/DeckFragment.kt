package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckCardSection

/**
 * A simple [Fragment] subclass.
 */
class DeckFragment : Fragment() {

    var deck: Deck? = null
    private var sectionAdapter = SectionedRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_deck, container, false)

//        activity?.toolbar.title = deck?.name
        addManaSymbols(deck?.getManaTypes()!!,
                rootView.context,
                (rootView.findViewById(R.id.deckManaContainer) as LinearLayout))
        

        deck?.sortByType()?.forEach {
            val deckCardSection = DeckCardSection(it.key, it.value.toMutableMap(), deck!!, sectionAdapter)
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
        fun newInstance(): DeckFragment {
            return DeckFragment()
        }
    }
}

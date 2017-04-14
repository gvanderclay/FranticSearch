package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [DeckListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeckListFragment : Fragment() {
    private val TAG = "DeckListFragment"


    private var deckModel = mutableListOf(Deck("1"),Deck("2"),Deck("3"))
    private var deckAdapter = DeckListAdapter(deckModel)
    private var deckList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_deck_list, container, false)

        rootView.tag = TAG

        deckList = rootView.findViewById(R.id.deckList) as RecyclerView

        deckList?.layoutManager = LinearLayoutManager(activity.applicationContext)
        deckList?.setHasFixedSize(true)
        deckList?.adapter = deckAdapter

        return rootView
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment DeckListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): DeckListFragment {
            val fragment = DeckListFragment()
            return fragment
        }
    }

}// Required empty public constructor

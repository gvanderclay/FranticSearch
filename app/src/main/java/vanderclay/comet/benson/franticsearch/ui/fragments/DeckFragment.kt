package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.toolbar.*

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Deck


/**
 * A simple [Fragment] subclass.
 */
class DeckFragment : Fragment() {

    var deck: Deck? = null

    var deckName: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_deck, container, false)

        deckName = rootView.findViewById(R.id.deckName) as TextView
        activity.toolbar.title = deck?.name



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

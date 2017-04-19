package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import io.magicthegathering.javasdk.api.CardAPI
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.addManaSymbols
import vanderclay.comet.benson.franticsearch.model.Deck


/**
 * A simple [Fragment] subclass.
 */
class DeckFragment : Fragment() {

    var deck: Deck? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_deck, container, false)

        activity.toolbar.title = deck?.name
        addManaSymbols(deck?.getManaTypes()!!,
                rootView.context,
                (rootView.findViewById(R.id.deckManaContainer) as LinearLayout))

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

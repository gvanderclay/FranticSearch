package vanderclay.comet.benson.franticsearch.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Deck
import vanderclay.comet.benson.franticsearch.ui.adapters.DeckListAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [DeckListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeckListFragment : Fragment() {

    private val deckListTag = "DeckListFragment"

    private var deckModel = arrayListOf<Deck>()
    private var deckAdapter = DeckListAdapter(deckModel)
    private var deckList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadCards()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_deck_list, container, false)

        rootView.tag = deckListTag

        deckList = rootView.findViewById(R.id.deckList) as RecyclerView

        deckList?.layoutManager = LinearLayoutManager(activity?.applicationContext)
        deckList?.setHasFixedSize(true)
        deckList?.adapter = deckAdapter

        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.deck_add_menu, menu)
        menu.findItem(R.id.deckAdd)?.setOnMenuItemClickListener {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            val input = EditText(activity)
            alertDialogBuilder.setView(input)
            alertDialogBuilder.setPositiveButton("Add Deck") { _, _ ->
                if(input.text.isEmpty()) return@setPositiveButton
                val newDeck = Deck(input.text.toString())
                deckModel.add(newDeck)
                deckAdapter.notifyDataSetChanged()
                Log.d(deckListTag, "Add deck clicked")
            }
            alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
                Log.d(deckListTag, "Add deck cancelled")
            }
            alertDialogBuilder.create().show()
            true

        }
    }

    private fun loadCards() {
        Deck.getAllDecks(deckModel, deckAdapter)
    }

    companion object {


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment DeckListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): DeckListFragment {
            return DeckListFragment()
        }
    }

}// Required empty public constructor

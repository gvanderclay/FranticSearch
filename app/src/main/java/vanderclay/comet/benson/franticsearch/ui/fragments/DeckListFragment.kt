package vanderclay.comet.benson.franticsearch.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.magicthegathering.javasdk.api.CardAPI
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.uiThread

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

    private var deckModel = arrayListOf<Deck>()
    private var deckAdapter = DeckListAdapter(deckModel)
    private var deckList: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadCards()
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.deck_add_menu, menu)
        menu?.findItem(R.id.deckAdd)?.setOnMenuItemClickListener {
            val alertDialogBuilder = AlertDialog.Builder(activity)
            val input = EditText(activity)
            alertDialogBuilder.setView(input)
            alertDialogBuilder.setPositiveButton("Add Deck", { dialog, which ->
                if(input.text.isEmpty()) return@setPositiveButton
                val newDeck = Deck(input.text.toString())
                deckModel.add(newDeck)
                deckAdapter.notifyDataSetChanged()
                Log.d(TAG, "Add deck clicked")
            })
            alertDialogBuilder.setNegativeButton("Cancel", { dialog, which ->
                Log.d(TAG, "Add deck cancelled")
            })
            alertDialogBuilder.create().show()
            true

        }

    }

    private fun loadCards() {
        val deckDatabaseRef = FirebaseDatabase
                .getInstance()
                .getReference("Decks")
                .child(FirebaseAuth.getInstance().currentUser?.uid)

        val valueEventListener = object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                Log.d(TAG, "HERE")
                doAsync {
                    val decks = dataSnapshot?.value as Map<String, Map<String, Object>>
                    decks.map {
                        val name = it.value["deckName"].toString()
                        val deck = Deck.loadInstance(name, it.key)
                        deckModel.add(deck)
                    }
                    uiThread {
                        deckAdapter.notifyDataSetChanged()
                    }
                }
            }

        }

        deckDatabaseRef.addListenerForSingleValueEvent(valueEventListener)

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

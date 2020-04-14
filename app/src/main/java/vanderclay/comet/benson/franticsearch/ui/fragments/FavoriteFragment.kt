package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.commons.mapToCard
import vanderclay.comet.benson.franticsearch.ui.adapters.FavoriteListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.viewholder.CardViewHolder

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardSearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(){

    /*Reference to the Card Favorite Fragment*/
    private val TAG = "CardFavoriteFragment"

    /*Model of card that are retrieved from firebase*/
    private var cardModel = mutableListOf<Card>()

    /*The adapter for the recycler view*/
    private var cardAdapter = FavoriteListAdapter(cardModel)

    /*Reference to the Recycler View*/
    private var cardList: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /*TODO implment getting the first List of card from the favorites list*/
        loadNextDataFromApi(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater!!.inflate(R.layout.fragment_favorite_cards, container, false)
        rootView.tag = TAG
        cardList = rootView.findViewById(R.id.cardFavoriteList) as RecyclerView

        val layoutManager = LinearLayoutManager(activity.applicationContext)
        cardList?.layoutManager = layoutManager
        cardList?.setHasFixedSize(true)
        cardList?.adapter = cardAdapter
        return rootView

    }

    override fun onResume() {
        super.onResume()
    }


    private fun loadNextDataFromApi(page: Int) {
        getAllFavorites(cardModel, cardAdapter)
        cardAdapter.notifyDataSetChanged()
    }

    private fun getAllFavorites(favorites: MutableList<Card>, recycler: RecyclerView.Adapter<CardViewHolder>) {

        val favoritesDatabaseRef = FirebaseDatabase
                .getInstance()
                .getReference("Favorites")
                .child(FirebaseAuth.getInstance().currentUser?.uid)

        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot: DataSnapshot in dataSnapshot.children) {
                    Log.d("Favorites", snapshot.value.toString())
                    val card = mapToCard(snapshot.value as Map<String, Object>)
                    try {
                        favorites.add(card)
                    } catch(e: Exception) {
                        Log.e("Favorites", card.toString())
                    }
                }
                recycler.notifyDataSetChanged()

            }
        }

        favoritesDatabaseRef.addListenerForSingleValueEvent(valueEventListener)
    }
    companion object {
        fun newInstance(): FavoriteFragment {
            val fragment = FavoriteFragment()
            return fragment
        }
    }
}

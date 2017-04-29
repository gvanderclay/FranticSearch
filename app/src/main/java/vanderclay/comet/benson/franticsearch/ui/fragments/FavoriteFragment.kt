package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Favorite
import vanderclay.comet.benson.franticsearch.ui.adapters.FavoriteListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.listeners.EndlessRecyclerViewScrollListener

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
        Favorite.getAllFavorites(cardModel, cardAdapter)
        cardAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            val fragment = FavoriteFragment()
//            val args = Bundle()
            return fragment
        }
    }
}

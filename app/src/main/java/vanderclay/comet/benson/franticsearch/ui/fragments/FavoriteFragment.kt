package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity

import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.api.MtgAPI
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.listeners.EndlessRecyclerViewScrollListener

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardSearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CardSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(), SearchView.OnQueryTextListener {

    /*Reference to the Card Favorite Fragment*/
    private val TAG = "CardFavoriteFragment"

    /*Model of card that are retrieved from firebase*/
    private var cardModel = mutableListOf<Card>()

    /*The adapter for the recycler view*/
    private var cardAdapter = CardListAdapter(cardModel)

    /*Listener for the users endless scrolling*/
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    /*Search parameter for the top of the Fragment*/
    private var cardFilter: String? = ""

    /*bound to the message qeue or to the Thread */
    private val handler = Handler()

    /*Reference to the Recycler View*/
    private var cardList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        /*TODO implment getting the first List of card from the favorites list*/
//        loadNextDataFromApi(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)

        /*References to the menu for searching for specific cards.*/
        val item = menu?.findItem(R.id.action_search)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext)
        MenuItemCompat.setActionView(item, searchView)
        searchView.setOnQueryTextListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_favorite_cards, container, false)
        rootView.tag = TAG

        cardList = rootView.findViewById(R.id.cardFavoriteList) as RecyclerView
        val layoutManager = LinearLayoutManager(activity.applicationContext)
        cardList?.layoutManager = layoutManager

        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int): Boolean {
                //TODO get the next current data from the page.
//                loadNextDataFromApi(currentPage)
                return true
            }
        }

        cardList?.addOnScrollListener(scrollListener)
        cardList?.setHasFixedSize(true)
        cardList?.adapter = cardAdapter
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = activity.getString(R.string.card_search)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        cardFilter =  newText
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            cardAdapter.notifyDataSetChanged()
            loadNextDataFromApi(1)
            cardModel.clear()
            scrollListener?.resetState()

            cardList?.scrollToPosition(0)
        }, 500)

        return true
    }


    private fun loadNextDataFromApi(page: Int) {
        doAsync {
            val cards = MtgAPI.getCards(page, "name=$cardFilter", "orderBy=name")
            uiThread {
                cardModel.addAll(cards)
                Log.d(TAG, "Elements in array after search change ${cardModel.size}")
                cardAdapter.notifyDataSetChanged()
            }
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment FavoriteCardFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): FavoriteFragment {
            val fragment = FavoriteFragment()
//            val args = Bundle()
            return fragment
        }
    }
}

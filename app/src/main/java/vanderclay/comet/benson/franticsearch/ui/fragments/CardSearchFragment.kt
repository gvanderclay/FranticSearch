package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.magicthegathering.javasdk.resource.Card
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.api.MtgApi
import vanderclay.comet.benson.franticsearch.ui.activities.MainActivity
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.listeners.EndlessRecyclerViewScrollListener

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardSearchFragment] interface
 * to handle interaction events.
 * Use the [CardSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardSearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private val cardSearchTag = "CardSearchFragment"

    private var cardModel = mutableListOf<Card>()

    private var cardAdapter = CardListAdapter(cardModel)

    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    private var cardFilter: String? = ""

    private val handler = Handler()

    private var cardList: RecyclerView? = null
    private var searchView: SearchView? = null
    private var searchText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if(searchText.isEmpty()) {
            loadNextDataFromApi(1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView = SearchView((context as MainActivity).supportActionBar?.themedContext)
        MenuItemCompat.setActionView(item, searchView)
        searchView?.setOnQueryTextListener(this)
        if(searchText.isNotEmpty()) {
            searchView?.setQuery(searchText, true)
            cardModel.clear()
            cardAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_card_search, container, false)
        rootView.tag = cardSearchTag
        cardList = rootView.findViewById(R.id.cardList) as RecyclerView

        val layoutManager = LinearLayoutManager(activity!!.applicationContext)
        cardList?.layoutManager = layoutManager
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int): Boolean {
                loadNextDataFromApi(currentPage)
                return true
            }
        }

        cardList?.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        cardList?.setHasFixedSize(true)
        cardList?.adapter = cardAdapter
        return rootView
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = (activity as AppCompatActivity).getString(R.string.card_search)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        cardFilter =  newText
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            cardModel.clear()
            cardAdapter.notifyDataSetChanged()
            loadNextDataFromApi(1)
            scrollListener?.resetState()

            cardList?.scrollToPosition(0)
        }, 500)

        return true
    }


    private fun loadNextDataFromApi(page: Int) {
        doAsync {
//            val cards = MtgApi.getCards(page, "name=$cardFilter", "orderBy=name")
//            uiThread {
//                cardModel.addAll(cards)
//                Log.d(cardSearchTag, "Elements in array after search change ${cardModel.size}")
//                cardAdapter.notifyDataSetChanged()
//            }
        }
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment CardSearchFragment.
         */
        fun newInstance(): CardSearchFragment {
            return CardSearchFragment()
        }

        fun newInstance(searchText: String): CardSearchFragment {
            val fragment = CardSearchFragment()

            fragment.searchText = searchText
            return fragment
        }
    }
}

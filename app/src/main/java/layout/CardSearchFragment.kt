package layout

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import io.magicthegathering.javasdk.resource.Card
import kotlinx.android.synthetic.main.fragment_card_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

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
class CardSearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private val TAG = "CardSearchActivity"

    private var cardModel = mutableListOf<Card>()
    private var cardAdapter = CardListAdapter(cardModel)
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var cardFilter: String? = ""
    private val handler = Handler()

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val layoutManager = LinearLayoutManager(activity.applicationContext)
        cardList.layoutManager = layoutManager
        scrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int): Boolean {
                loadNextDataFromApi(currentPage)
                return true
            }
        }
        cardList.addOnScrollListener(scrollListener)
        loadNextDataFromApi(1)
        cardList.setHasFixedSize(true)
        cardList.adapter = cardAdapter
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val searchMenuItem = menu?.findItem(R.menu.search_menu)
        val searchView = MenuItemCompat.getActionView(searchMenuItem) as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_card_search, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        cardFilter =  newText
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            cardAdapter.notifyDataSetChanged()
            loadNextDataFromApi(1)
            cardModel.clear()
            scrollListener?.resetState()

            cardList.scrollToPosition(0)
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment CardSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CardSearchFragment {
            val fragment = CardSearchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}

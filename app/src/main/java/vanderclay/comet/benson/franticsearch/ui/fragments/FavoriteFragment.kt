package vanderclay.comet.benson.franticsearch.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.magicthegathering.javasdk.resource.Card
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.model.Favorite
import vanderclay.comet.benson.franticsearch.ui.adapters.FavoriteListAdapter

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CardSearchFragment] interface
 * to handle interaction events.
 * Use the [CardSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment(){

    /*Reference to the Card Favorite Fragment*/
    private val favoriteTag = "CardFavoriteFragment"

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
        loadNextDataFromApi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_favorite_cards, container, false)
        rootView.tag = favoriteTag
        cardList = rootView.findViewById(R.id.cardFavoriteList) as RecyclerView

        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        cardList?.layoutManager = layoutManager
        cardList?.setHasFixedSize(true)
        cardList?.adapter = cardAdapter
        return rootView

    }

    private fun loadNextDataFromApi() {
        Favorite.getAllFavorites(cardModel, cardAdapter)
        cardAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}

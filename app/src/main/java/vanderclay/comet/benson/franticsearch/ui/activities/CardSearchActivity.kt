package vanderclay.comet.benson.franticsearch.ui.activities

import android.os.Bundle
import android.os.Handler
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import io.magicthegathering.javasdk.resource.Card
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.api.MtgAPI
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter
import vanderclay.comet.benson.franticsearch.ui.adapters.listeners.EndlessRecyclerViewScrollListener

class CardSearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val TAG = "CardSearchActivity"

    private var cardModel = mutableListOf<Card>()
    private var cardAdapter = CardListAdapter(cardModel)
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private var cardFilter: String? = ""
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        val layoutManager = LinearLayoutManager(this)
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



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.main_menu, menu);

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)


        return super.onCreateOptionsMenu(menu)
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

}



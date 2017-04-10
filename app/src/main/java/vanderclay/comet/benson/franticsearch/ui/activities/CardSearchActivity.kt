package vanderclay.comet.benson.franticsearch.ui.activities

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.data.domain.datasource.CardProvider
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter

class CardSearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val TAG = "CardSearchActivity"

    private var cardModel = listOf<Card>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        val layoutManager = LinearLayoutManager(applicationContext)
        cardList.layoutManager = layoutManager
        cardList.setHasFixedSize(true)
        cardList.adapter = CardListAdapter(listOf())
        loadCards()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.main_menu, menu);

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private fun filter(cards: List<Card>, query: String?): List<Card> {
        return cards.filter {
            it.name!!.contains(query as CharSequence, true)
        }
    }

    private fun loadCards() = doAsync {
        val provider = CardProvider(applicationContext)
        var result = provider.requestAllCardsFromDB()
        if(result.size == 0) {
            Log.d(TAG, "No cards in database. Using API")
            result = provider.requestAllCardsFromAPI()
        }
        uiThread {
            val adapter = CardListAdapter(result)
            cardModel = result
            cardList.adapter = adapter
            cardList.invalidate()
            cardList.adapter.notifyDataSetChanged()
        }
    }
}



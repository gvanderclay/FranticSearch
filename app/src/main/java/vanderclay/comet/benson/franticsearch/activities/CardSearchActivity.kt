package vanderclay.comet.benson.franticsearch.activities

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import vanderclay.comet.benson.franticsearch.data.db.CardDO
import vanderclay.comet.benson.franticsearch.data.API.CardSearchAPI
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.adapters.CardListAdapter

class CardSearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var cardAPI: CardSearchAPI? = null
    var cardModelList: List<CardDO>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        cardAPI = CardSearchAPI(applicationContext)
        cardList.setHasFixedSize(true)
        GetCardTask(cardAPI).execute()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.main_menu, menu);

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)


        cardModelList = cardAPI?.contacts
        val adapter = CardListAdapter(cardModelList as MutableList<CardDO>)
        cardList.adapter = adapter


        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    private class GetCardTask(cardAPI: CardSearchAPI?): AsyncTask<Void, Void, Void>() {
        val mCardAPI = cardAPI
        override fun doInBackground(vararg params: Void?): Void? {
            mCardAPI?.addAllCards()
            Log.d("ASYNC", "done")
            return null
        }

    }
}



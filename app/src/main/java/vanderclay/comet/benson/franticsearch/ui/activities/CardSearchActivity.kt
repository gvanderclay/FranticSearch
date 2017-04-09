package vanderclay.comet.benson.franticsearch.ui.activities

import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*
import vanderclay.comet.benson.franticsearch.R
import vanderclay.comet.benson.franticsearch.data.domain.model.Card
import vanderclay.comet.benson.franticsearch.ui.adapters.CardListAdapter

class CardSearchActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var cardModelList: List<Card>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        cardList.setHasFixedSize(true)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.main_menu, menu);

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)


        val adapter = CardListAdapter(cardModelList as MutableList<Card>)
        cardList.adapter = adapter


        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }
}



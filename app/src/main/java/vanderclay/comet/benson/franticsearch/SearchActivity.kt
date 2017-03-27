package vanderclay.comet.benson.franticsearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView

class SearchActivity : AppCompatActivity() {

    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val lv = findViewById(R.id.cardSearchList) as ListView
        var array: Array<String> = arrayOf("This", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list", "Is", "A", "list")
        adapter = ArrayAdapter(
                this@SearchActivity,
                android.R.layout.simple_list_item_1,
                array)
        lv.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.menuSearch)
        val searchView = item?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false

            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}



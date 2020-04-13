package vanderclay.comet.benson.franticsearch.ui.adapters.listeners

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: LinearLayoutManager):
        RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 20
    private var firstVisibleItem: Int? = null
    private var visibleItemCount: Int? = null
    private var totalItemCount: Int? = null
    private var startingPageIndex = 1

    private var currentPage = startingPageIndex

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        if(loading) {
            if(totalItemCount!! > previousTotal) {
                loading = false
                previousTotal = totalItemCount!!
            }
        }
        if(!loading && (totalItemCount!! - visibleItemCount!!) <=
                (firstVisibleItem!! + visibleThreshold)) {
            currentPage++
            onLoadMore(currentPage)

            loading = true
        }

    }

    fun resetState() {
        currentPage = startingPageIndex
        previousTotal = 0
        loading = true
    }

    abstract fun onLoadMore(currentPage: Int): Boolean
}


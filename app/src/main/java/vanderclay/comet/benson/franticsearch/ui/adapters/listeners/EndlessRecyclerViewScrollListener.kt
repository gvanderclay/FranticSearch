package vanderclay.comet.benson.franticsearch.ui.adapters.listeners

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AbsListView

/**
 * Created by gclay on 4/10/17.
 */

abstract class EndlessRecyclerViewScrollListener(layoutManager: LinearLayoutManager):
        RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 5
    private var firstVisibleItem: Int? = null
    private var visibleItemCount: Int? = null
    private var totalItemCount: Int? = null
    private var startingPageIndex = 1

    private var currentPage = startingPageIndex
    private val layoutManager = layoutManager


    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        visibleItemCount = recyclerView?.childCount
        totalItemCount = layoutManager.itemCount
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition()
        if(loading) {
            if(totalItemCount!! > previousTotal!!) {
                loading = false
                previousTotal = totalItemCount!!
            }
        }
        if(!loading && (totalItemCount!! - visibleItemCount!!) <=
                (firstVisibleItem!! + visibleThreshold!!)) {
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


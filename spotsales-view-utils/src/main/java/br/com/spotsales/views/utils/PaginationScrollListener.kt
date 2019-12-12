package br.com.spotsales.views.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PaginationScrollListener(
        private val layoutManager: LinearLayoutManager,
        private val listener: OnPaginationListener
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val totalItems = layoutManager.childCount + firstVisibleItemPosition
        val moreItemsCondition = totalItems >= totalItemCount && firstVisibleItemPosition >= 0

        if (!listener.isLoading() && !listener.isLastPage() && moreItemsCondition) {
            listener.loadMoreItems()
        }
    }

    interface OnPaginationListener {
        fun isLastPage(): Boolean
        fun isLoading(): Boolean
        fun loadMoreItems()
    }
}

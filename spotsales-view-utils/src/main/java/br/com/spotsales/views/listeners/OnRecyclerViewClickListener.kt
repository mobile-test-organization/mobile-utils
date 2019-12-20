package br.com.spotsales.views.listeners

interface OnRecyclerViewClickListener<T> {
    fun onItemSelected(item: T, position: Int)
}

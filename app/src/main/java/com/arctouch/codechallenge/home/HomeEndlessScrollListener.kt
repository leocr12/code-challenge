package com.arctouch.codechallenge.home

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView




class HomeEndlessScrollListener(private val gridLayoutManager: GridLayoutManager?, private val dataLoader: DataLoader?): RecyclerView.OnScrollListener() {

    private var previousItemCount: Int = 0
    private var loading: Boolean = false

    init {
        reset()
    }

    override fun onScrolled(view: RecyclerView?, dx: Int, dy: Int) {
        if (dy > 0) {
            val itemCount = gridLayoutManager!!.itemCount

            if (itemCount != previousItemCount) {
                loading = false
            }

            if (!loading && gridLayoutManager.findLastVisibleItemPosition() >= itemCount - 1) {
                previousItemCount = itemCount
                loading = dataLoader!!.onLoadMore()
            }
        }
    }

    private fun reset() {
        this.loading = false
        this.previousItemCount = -1
    }

    interface DataLoader {
        fun onLoadMore(): Boolean
    }
}
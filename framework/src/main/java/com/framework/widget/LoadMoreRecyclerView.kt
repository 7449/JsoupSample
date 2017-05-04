package com.framework.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.AttributeSet

/**
 * by y on 2017/3/23
 */

class LoadMoreRecyclerView : RecyclerView {
    private var layoutManagerType: LAYOUT_MANAGER_TYPE? = null
    /**
     * 最后一个的位置
     */
    private var lastPositions: IntArray? = null
    /**
     * 最后一个可见的item的位置
     */
    private var lastVisibleItemPosition: Int = 0
    private var loadingData: LoadMoreListener? = null

    constructor(context: Context) : super(context) {}


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}


    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    fun setLoadingMore(loadingData: LoadMoreListener) {
        this.loadingData = loadingData
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        val layoutManager = layoutManager
        if (layoutManagerType == null) {
            if (layoutManager is GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID
            } else if (layoutManager is LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR
            } else if (layoutManager is StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID
            } else {
                throw RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
            }
        }

        when (layoutManagerType) {
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.LINEAR -> lastVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.GRID -> lastVisibleItemPosition = (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            LoadMoreRecyclerView.LAYOUT_MANAGER_TYPE.STAGGERED_GRID -> {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager.spanCount)
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions)
                lastVisibleItemPosition = findMax(lastPositions!!)
            }
        }

    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        val layoutManager = layoutManager
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && loadingData != null) {
            loadingData!!.onLoadMore()
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        return lastPositions.max()
                ?: lastPositions[0]
    }

    enum class LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    interface LoadMoreListener {
        fun onLoadMore()
    }

}

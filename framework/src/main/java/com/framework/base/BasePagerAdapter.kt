package com.framework.base

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.framework.base.mvp.BaseModel
import java.util.*

/**
 * by y on 2016/7/26.
 */
abstract class BasePagerAdapter<T : BaseModel>(datas: MutableList<T>) : PagerAdapter() {

    private var data = ArrayList<T>()

    init {
        this.data = datas as ArrayList<T>
    }


    fun clearAll() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<T>) {
        data.addAll(list)
        notifyDataSetChanged()
    }

    val listData: List<T>
        get() = data

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    fun getData(position: Int): T? {
        return if (data.isEmpty()) null else data[position]
    }

    fun getUrl(position: Int): String? {
        return if (getData(position) == null) null else getData(position)!!.url
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return instantiate(container, position, data[position])
    }

    protected abstract fun instantiate(container: ViewGroup, position: Int, data: T): Any

}


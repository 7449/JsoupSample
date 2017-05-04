package com.framework.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * by y on 2016/9/16.
 */
class SuperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val viewSparseArray: SparseArray<View> = SparseArray()
    val context: Context = itemView.context

    init {
        itemView.tag = viewSparseArray
    }

    operator fun <T : View> get(id: Int): T {
        var childView: View? = viewSparseArray.get(id)
        if (childView == null) {
            childView = itemView.findViewById(id)
            viewSparseArray.put(id, childView)
        }

        return (childView as T?)!!
    }

    fun getTextView(id: Int): TextView {
        return get(id)
    }

    fun getImageView(id: Int): ImageView {
        return get(id)
    }

    fun setTextView(id: Int, charSequence: CharSequence) {
        getTextView(id).text = charSequence
    }

    fun setTextColor(id: Int, color: Int) {
        getTextView(id).setTextColor(color)
    }

}

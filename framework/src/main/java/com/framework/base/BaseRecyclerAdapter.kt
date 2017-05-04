package com.framework.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * by y on 2016/9/13
 */
abstract class BaseRecyclerAdapter<T>(mDatas: MutableList<T>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mOnItemClickListener: OnItemClickListener<T>? = null
    private var mOnLongClickListener: OnItemLongClickListener<T>? = null
    protected var mDatas: MutableList<T> = LinkedList()

    init {
        this.mDatas = mDatas
    }

    fun getView(viewGroup: ViewGroup, id: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(id, viewGroup, false)
    }

    fun setOnItemClickListener(listener: OnItemClickListener<T>) {
        this.mOnItemClickListener = listener
    }

    fun setOnLongClickListener(listener: OnItemLongClickListener<T>) {
        this.mOnLongClickListener = listener
    }

    fun addAll(datas: List<T>) {
        mDatas.addAll(datas)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        mDatas.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    fun getData(position: Int): T {
        return mDatas[position]
    }

    fun removeAll() {
        mDatas.clear()
        notifyDataSetChanged()
    }

    val dataCount: Int
        get() = mDatas.size

    val datas: List<T>
        get() = mDatas

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SuperViewHolder(getView(parent, layoutId))
    }

    protected abstract val layoutId: Int

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBind(holder as SuperViewHolder, position, mDatas[position])
        val data = mDatas[position]
        if (null != mOnItemClickListener) {
            holder.itemView.setOnClickListener { v -> mOnItemClickListener!!.onItemClick(v, position, data) }
        }
        if (null != mOnLongClickListener) {
            holder.itemView.setOnLongClickListener { v ->
                mOnLongClickListener!!.onLongClick(v, position, data)
                true
            }
        }
    }

    protected abstract fun onBind(viewHolder: SuperViewHolder, position: Int, mDatas: T)

    override fun getItemCount(): Int {
        return mDatas.size
    }

    interface OnItemClickListener<in T> {
        fun onItemClick(view: View, position: Int, info: T)
    }

    interface OnItemLongClickListener<in T> {
        fun onLongClick(view: View, position: Int, info: T)
    }

}
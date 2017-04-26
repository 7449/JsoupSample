package com.framework.base;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * by y on 2016/9/13
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickListener<T> mOnItemClickListener;
    private OnItemLongClickListener<T> mOnLongClickListener;
    protected List<T> mDatas = new LinkedList<>();

    public BaseRecyclerAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public View getView(ViewGroup viewGroup, int id) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnLongClickListener(OnItemLongClickListener<T> listener) {
        this.mOnLongClickListener = listener;
    }

    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    public T getData(int position) {
        return mDatas.get(position);
    }

    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public int getDataCount() {
        return mDatas.size();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SuperViewHolder(getView(parent, getLayoutId()));
    }

    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        onBind((SuperViewHolder) holder, position, mDatas.get(position));
        final T data = mDatas.get(position);
        if (null != mOnItemClickListener) {
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position, data));
        }
        if (null != mOnLongClickListener) {
            holder.itemView.setOnLongClickListener(v -> {
                mOnLongClickListener.onLongClick(v, position, data);
                return true;
            });
        }
    }

    protected abstract void onBind(SuperViewHolder viewHolder, int position, T mDatas);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T info);
    }

    public interface OnItemLongClickListener<T> {
        void onLongClick(View view, int position, T info);
    }

}
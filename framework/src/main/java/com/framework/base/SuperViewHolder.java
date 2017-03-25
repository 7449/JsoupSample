package com.framework.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * by y on 2016/9/16.
 */
public class SuperViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;
    private Context context;

    public SuperViewHolder(final View itemView) {
        super(itemView);
        viewSparseArray = new SparseArray<>();
        context = itemView.getContext();
        itemView.setTag(viewSparseArray);
    }

    public <T extends View> T get(int id) {
        View childView = viewSparseArray.get(id);
        if (childView == null) {
            childView = itemView.findViewById(id);
            viewSparseArray.put(id, childView);
        }
        return (T) childView;
    }

    public Context getContext() {
        return context;
    }

    public TextView getTextView(int id) {
        return get(id);
    }

    public ImageView getImageView(int id) {
        return get(id);
    }

    public void setTextView(int id, CharSequence charSequence) {
        getTextView(id).setText(charSequence);
    }

    public void setTextColor(int id, int color) {
        getTextView(id).setTextColor(color);
    }

}

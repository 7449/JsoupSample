package com.image.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.image.R;
import com.image.manager.DBManager;
import com.image.mvp.model.CollectionModel;
import com.image.ui.dialog.CollectionDetailDialog;
import com.xadapter.OnItemLongClickListener;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class CollectionListFragment extends BaseFragment
        implements OnItemLongClickListener<CollectionModel> {

    private RecyclerView recyclerView;
    private TextView textView;

    private XRecyclerViewAdapter<CollectionModel> mAdapter;

    public static CollectionListFragment newInstance() {
        return new CollectionListFragment();
    }

    @Override
    protected void initById() {
        recyclerView = (RecyclerView) getView(R.id.recyclerView);
        textView = (TextView) getView(R.id.tv_empty);
    }

    @Override
    protected BasePresenterImpl initPresenter() {
        return null;
    }

    @Override
    protected void initActivityCreated() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<CollectionModel> collectionAll = DBManager.getCollectionAll();

        recyclerView.setAdapter(
                mAdapter.initXData(collectionAll).setLayoutId(R.layout.item_collection)
                        .onXBind((holder, position, collectionModel) -> ImageLoaderUtils.display(holder.getImageView(R.id.image), collectionModel.getUrl()))
                        .setOnItemClickListener((view2, position, info) -> CollectionDetailDialog.startFragment(position, getChildFragmentManager()))
                        .setOnLongClickListener(this)
        );
        notifyDataSetChangedView(collectionAll.isEmpty());
    }

    private void notifyDataSetChangedView(boolean isEmpty) {
        textView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }


    public void refreshUI() {
        if (mAdapter != null) {
            mAdapter.removeAll();
            List<CollectionModel> collectionAll = DBManager.getCollectionAll();
            mAdapter.addAllData(collectionAll);
            notifyDataSetChangedView(collectionAll.isEmpty());
        }
    }

    @Override
    public boolean onLongClick(View view, int position, CollectionModel info) {
        new MaterialDialog
                .Builder(getActivity())
                .title(UIUtils.getString(R.string.collection_title))
                .negativeText(R.string.collection_deleted)
                .onNegative((dialog, which) -> {
                    DBManager.clear(mAdapter.getData(position).getUrl());
                    mAdapter.remove(position);
                    if (mAdapter.getData().isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                })
                .show();
        return true;
    }
}

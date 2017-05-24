package com.image.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.framework.base.BaseFragment;
import com.framework.base.mvp.PresenterImplCompat;
import com.framework.utils.ImageLoaderUtils;
import com.image.R;
import com.image.collection.CollectionModel;
import com.image.collection.CollectionUtils;
import com.image.collection.GreenDaoDbUtils;
import com.image.ui.dialog.CollectionDetailDialog;
import com.image.ui.dialog.CollectionListDialog;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class CollectionListFragment extends BaseFragment
        implements CollectionListDialog.CollectionListener {

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
    protected PresenterImplCompat initPresenter() {
        return null;
    }

    @Override
    protected void initActivityCreated() {
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<CollectionModel> collectionAll = GreenDaoDbUtils.getCollectionAll();

        recyclerView.setAdapter(
                mAdapter.initXData(collectionAll).setLayoutId(R.layout.item_collection)
                        .onXBind((holder, position, collectionModel) -> ImageLoaderUtils.display(holder.getImageView(R.id.image), collectionModel.getUrl()))
                        .setOnItemClickListener((view2, position, info) -> CollectionDetailDialog.startFragment(position, getChildFragmentManager()))
                        .setOnLongClickListener((view, position, info) -> {
                            CollectionListDialog.newInstance(position, getChildFragmentManager(), "collection");
                            return true;
                        })
        );

        textView.setVisibility(collectionAll.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(collectionAll.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onCollectionDeletedNext(int position) {
        CollectionUtils.deleted(mAdapter.getData(position).getUrl());
        mAdapter.remove(position);
        if (mAdapter.getData().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }
}

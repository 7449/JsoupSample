package com.image.collection.list;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.framework.base.BaseFragment;
import com.framework.base.BaseRecyclerAdapter;
import com.image.R;
import com.image.collection.sql.CollectionModel;
import com.image.collection.sql.CollectionUtils;
import com.image.collection.sql.GreenDaoDbUtils;

import java.util.List;

/**
 * by y on 2017/3/26.
 */

public class CollectionListFragment extends BaseFragment
        implements BaseRecyclerAdapter.OnItemClickListener<CollectionModel>,
        BaseRecyclerAdapter.OnItemLongClickListener<CollectionModel>, CollectionDialog.CollectionListener {

    private RecyclerView recyclerView;
    private TextView textView;
    private CollectionListAdapter collectionListAdapter;

    public static CollectionListFragment newInstance() {
        return new CollectionListFragment();
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        textView = getView(R.id.tv_empty);
    }

    @Override
    protected void initActivityCreated() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        List<CollectionModel> collectionAll = GreenDaoDbUtils.getCollectionAll();
        collectionListAdapter = new CollectionListAdapter(collectionAll);
        collectionListAdapter.setOnItemClickListener(this);
        collectionListAdapter.setOnLongClickListener(this);
        recyclerView.setAdapter(collectionListAdapter);

        textView.setVisibility(collectionAll.isEmpty() ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(collectionAll.isEmpty() ? View.GONE : View.VISIBLE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onItemClick(View view, int position, CollectionModel info) {

    }

    @Override
    public void onLongClick(View view, int position, CollectionModel info) {
        CollectionDialog.newInstance(position, getChildFragmentManager(), "collection");
    }

    @Override
    public void onCollectionDeletedNext(int position) {
        CollectionUtils.deleted(collectionListAdapter.getData(position).getUrl());
        collectionListAdapter.remove(position);
        if (collectionListAdapter.getDatas().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }
}

package com.image.collection.list;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.framework.base.BaseFragment;
import com.image.R;
import com.image.collection.sql.GreenDaoDbUtils;

/**
 * by y on 2017/3/26.
 */

public class CollectionListFragment extends BaseFragment {

    private RecyclerView recyclerView;

    public static CollectionListFragment newInstance() {
        return new CollectionListFragment();
    }

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);

    }

    @Override
    protected void initActivityCreated() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new CollectionListAdapter(GreenDaoDbUtils.getCollectionAll()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }
}

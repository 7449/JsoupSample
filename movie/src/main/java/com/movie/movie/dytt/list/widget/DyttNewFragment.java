package com.movie.movie.dytt.list.widget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.framework.base.BaseFragment;
import com.framework.base.BaseRecyclerAdapter;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.movie.R;
import com.movie.movie.dytt.detail.widget.DyttVideoDetailActivity;
import com.movie.movie.dytt.list.model.DyttNewModel;
import com.movie.movie.dytt.list.presenter.DyttNewPresenter;
import com.movie.movie.dytt.list.presenter.DyttNewPresenterImpl;
import com.movie.movie.dytt.list.view.DyttNewView;

import java.util.ArrayList;
import java.util.List;

/**
 * by y on 2017/3/23
 * <p>
 * 最新发布170部影视 Fragment
 */

public class DyttNewFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        BaseRecyclerAdapter.OnItemClickListener<DyttNewModel>,
        DyttNewView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private DyttNewPresenter presenter;
    private DyttNewAdapter adapter;

    public static DyttNewFragment newInstance() {
        return new DyttNewFragment();
    }

    @Override
    protected void initById() {
        swipeRefreshLayout = getView(R.id.refresh_layout);
        recyclerView = getView(R.id.recyclerView);
    }

    @Override
    protected void initActivityCreated() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        initRecyclerView();
        presenter = new DyttNewPresenterImpl(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(this::onRefresh);
        setLoad();
    }

    private void initRecyclerView() {
        adapter = new DyttNewAdapter(new ArrayList<>());
        adapter.setOnItemClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dytt_new;
    }

    @Override
    public void onRefresh() {
        presenter.netWorkRequest();
    }

    @Override
    public void netWorkSuccess(List<DyttNewModel> data) {
        adapter.removeAll();
        data.remove(0);
        adapter.addAll(data);
    }

    @Override
    public void netWorkError(Throwable e) {
        if (getActivity() != null) {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), getString(R.string.network_error));
        }
    }

    @Override
    public void showProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onItemClick(View view, int position, DyttNewModel info) {
        if (ApkUtils.getIntent(ApkUtils.XL) != null) {
            DyttVideoDetailActivity.startIntent(info.detailUrl);
        } else {
            UIUtils.snackBar(getActivity().findViewById(R.id.coordinatorLayout), UIUtils.getString(R.string.xl));
        }
    }
}

package com.magnetic.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.framework.base.BaseFragment;
import com.framework.base.mvp.BasePresenterImpl;
import com.framework.utils.ApkUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.magnetic.R;
import com.magnetic.manager.DBManager;
import com.magnetic.mvp.model.CollectionModel;
import com.xadapter.OnItemClickListener;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

import io.reactivex.jsoup.network.bus.RxBus;
import io.reactivex.jsoup.network.bus.RxBusCallBack;

/**
 * by y on 04/07/2017.
 */

public class CollectionFragment extends BaseFragment implements RxBusCallBack<String>, OnItemClickListener<CollectionModel> {

    private LoadMoreRecyclerView recyclerView;
    private XRecyclerViewAdapter<CollectionModel> mAdapter;


    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }


    @Override
    protected void initActivityCreated() {
        RxBus.getInstance().register(getClass().getSimpleName(), this);
        mAdapter = new XRecyclerViewAdapter<>();

        List<CollectionModel> collectionContent = DBManager.getCollectionContent();

        if (collectionContent == null || collectionContent.isEmpty()) {
            setStatusViewStatus(StatusLayout.EMPTY);
            mAdapter.removeAll();
        } else {
            setStatusViewStatus(StatusLayout.SUCCESS);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(
                    mAdapter
                            .initXData(collectionContent)
                            .setLayoutId(R.layout.item_magnetic_list)
                            .onXBind((holder, position, magneticModel) -> holder.setTextView(R.id.tv_magnetic_name, magneticModel.getMagneticName()))
                            .setOnItemClickListener(this)
            );
        }

    }

    @Override
    protected void initById() {
        recyclerView = (LoadMoreRecyclerView) getView(R.id.recyclerView);
    }

    @Override
    protected BasePresenterImpl initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_collection;
    }

    /**
     * 这里  应该由RxBus post 得到 是删除还是添加收藏的区别，简单使用 不区分了
     * <p>
     * 偷懒，直接 刷新整个Adapter
     *
     * @param s RxBus 发送过来的消息
     */
    @Override
    public void onBusNext(String s) {
        mAdapter.removeAll();
        List<CollectionModel> collectionContent = DBManager.getCollectionContent();
        if (collectionContent == null || collectionContent.isEmpty()) {
            setStatusViewStatus(StatusLayout.EMPTY);
        } else {
            setStatusViewStatus(StatusLayout.SUCCESS);
            mAdapter.addAllData(collectionContent);
        }
    }

    @Override
    public void onBusError(Throwable throwable) {

    }

    @Override
    public Class<String> busOfType() {
        return String.class;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        RxBus.getInstance().unregister(getClass().getSimpleName());
    }

    @Override
    public void onItemClick(View view, int position, CollectionModel info) {
        new MaterialDialog
                .Builder(getActivity())
                .title(R.string.magnetic_title)
                .content(info.getUrl())
                .positiveText(R.string.xl)
                .negativeText(R.string.copy)
                .neutralText(R.string.collection_no)
                .onPositive((dialog, which) -> {
                    Intent xlIntent = ApkUtils.getXLIntent();
                    if (xlIntent == null) {
                        UIUtils.toast(R.string.xl_null);
                        return;
                    }
                    UIUtils.copy(info.getUrl());
                    startActivity(xlIntent);
                })
                .onNegative((dialog, which) -> {
                    UIUtils.copy(info.getUrl());
                    UIUtils.snackBar(coordinatorLayout, R.string.copy_success);
                })
                .onNeutral((dialog, which) -> {
                    DBManager.clearCollection(info.getUrl());
                    UIUtils.snackBar(coordinatorLayout, R.string.collection_no_success);
                    onBusNext(null);
                })
                .show();
    }
}

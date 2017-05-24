package com.fiction.ui.fragment;

import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.fiction.R;
import com.fiction.db.GreenDaoDbUtils;
import com.fiction.db.SqlBean;
import com.fiction.manager.ApiConfig;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.presenter.PresenterManager;
import com.fiction.mvp.presenter.SearchListPresenterImpl;
import com.fiction.mvp.view.ViewManager;
import com.fiction.ui.activity.FictionContentsActivity;
import com.framework.base.BaseFragment;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.FlowText;
import com.framework.widget.LoadMoreRecyclerView;
import com.google.android.flexbox.FlexboxLayout;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

public class SearchListFragment extends BaseFragment
        implements View.OnClickListener, ViewManager.SearchListView,
        LoadMoreRecyclerView.LoadMoreListener,
        View.OnFocusChangeListener,
        DialogInterface.OnClickListener {

    private PresenterManager.SearchListPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView recyclerView;
    private EditText editText;
    private int page = 0;
    private String fictionName;
    private AlertDialog alertDialog;
    private String searchType = ApiConfig.Type.ZW_81;
    private XRecyclerViewAdapter<FictionModel> mAdapter;

    @Override
    protected void initById() {
        recyclerView = getView(R.id.recyclerView);
        swipeRefreshLayout = getView(R.id.refresh_layout);
        getView(R.id.fa_btn).setOnClickListener(this);
    }

    @Override
    protected void initActivityCreated() {
        swipeRefreshLayout.setEnabled(false);
        presenter = new SearchListPresenterImpl(this);
        mAdapter = new XRecyclerViewAdapter<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLoadingMore(this);
        recyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_search)
                        .onXBind((holder, position, fictionModel) -> {
                            ImageLoaderUtils.display(holder.getImageView(R.id.search_iv_img), fictionModel.url);
                            holder.setTextView(R.id.search_tv_title, fictionModel.title);
                            holder.setTextView(R.id.search_tv_content, fictionModel.message);
                        })
                        .setOnItemClickListener((view, position, info) -> FictionContentsActivity.getInstance(ApiConfig.Type.ZW_81, info.detailUrl, info.title))
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fa_btn:
                startDialog();
                break;
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search;
    }


    @Override
    public void netWorkSuccess(List<FictionModel> data) {
        if (page == 0) {
            mAdapter.removeAll();
        }
        mAdapter.addAllData(data);
    }

    @Override
    public void netWorkError() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.network_error));
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void fictionNameEmpty() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.empty));
    }


    @Override
    public void onLoadMore() {
        if (editText != null) {
            ++page;
            presenter.startSearch(fictionName, page, searchType);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus && alertDialog.getWindow() != null) {
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        fictionName = editText.getText().toString().trim();
        startNetWork();
    }

    private void startNetWork() {
        page = 0;
        presenter.startSearch(fictionName, page, searchType);
    }

    private void startDialog() {
        List<SqlBean> fictionNameAll = GreenDaoDbUtils.getFictionNameAll();
        View view = View.inflate(getActivity(), R.layout.dialog_search, null);
        editText = (EditText) view.findViewById(R.id.search_et);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_view);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.zw:
                    searchType = ApiConfig.Type.ZW_81;
                    break;
                case R.id.biquge:
                    searchType = ApiConfig.Type.BI_QU_GE;
                    break;
                case R.id.ksw:
                    searchType = ApiConfig.Type.KSW;
                    break;
            }
        });

        FlexboxLayout flowLayout = (FlexboxLayout) view.findViewById(R.id.flow);
        flowLayout.removeAllViews();
        alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.fiction_name))
                .setView(view)
                .setNegativeButton(getString(R.string.dialog_unok), null)
                .setPositiveButton(getString(R.string.dialog_ok), this).create();
        alertDialog.show();
        for (int i = 0; i < fictionNameAll.size(); i++) {
            FlowText textView = new FlowText(flowLayout.getContext());
            textView.setText(fictionNameAll.get(i).getTitle());
            textView.setOnClickListener(v -> {
                FlowText flowText = (FlowText) v;
                fictionName = flowText.getText().toString().trim();
                startNetWork();
                alertDialog.dismiss();
            });
            flowLayout.addView(textView);
        }
        editText.setOnFocusChangeListener(this);
    }

    @Override
    public void noMore() {
        UIUtils.snackBar(getView(R.id.fa_btn), getString(R.string.data_empty));
    }
}

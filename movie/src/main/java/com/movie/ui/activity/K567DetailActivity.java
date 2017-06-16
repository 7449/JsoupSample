package com.movie.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.framework.base.BaseActivity;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.Constant;
import com.framework.widget.LoadMoreRecyclerView;
import com.framework.widget.StatusLayout;
import com.movie.R;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.presenter.K567DetailPresenterImpl;
import com.movie.mvp.view.ViewManager;
import com.socks.library.KLog;
import com.xadapter.adapter.XRecyclerViewAdapter;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.movie.R.id.recyclerView;

/**
 * by y on 2017/6/10.
 */

public class K567DetailActivity extends BaseActivity<K567DetailPresenterImpl>
        implements SwipeRefreshLayout.OnRefreshListener, ViewManager.K567DetailView {
    private static final String TITLE = "title";
    private static final String URL = "url";
    private static final String IMG = "img";

    private String url;
    private String title;
    private String imageUrl;

    private Toolbar mToolbar;
    private LoadMoreRecyclerView mRecyclerView;
    private SwipeRefreshLayout refreshLayout;
    private JCVideoPlayerStandard jcVideoPlayerStandard;

    private XRecyclerViewAdapter<MovieModel> mAdapter;

    public static void startIntent(String title, String url, String img) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(URL, url);
        bundle.putString(IMG, img);
        UIUtils.startActivity(K567DetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        Bundle extras = getIntent().getExtras();
        title = extras.getString(TITLE);
        url = extras.getString(URL);
        imageUrl = extras.getString(IMG);
        mToolbar.setTitle(title);

        refreshLayout.setEnabled(false);

        mAdapter = new XRecyclerViewAdapter<>();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(
                mAdapter.setLayoutId(R.layout.item_k567)
                        .onXBind((holder, position, movieModel) -> {
                            holder.setTextView(R.id.k567_item_tv, Html.fromHtml(movieModel.title));
                            ImageLoaderUtils.display(holder.getImageView(R.id.k567_item_iv), movieModel.url);
                        })
                        .setOnItemClickListener((view, position, info) -> {
                            finish();
                            state = Constant.TYPE_FINISH;
                            K567DetailActivity.startIntent(info.title, info.detailUrl, info.url);
                        })
        );

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.post(this::onRefresh);
        jcVideoPlayerStandard.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void clickNetWork() {
        super.clickNetWork();
        if (!refreshLayout.isRefreshing()) {
            onRefresh();
        }
    }

    @Override
    protected void initById() {
        mRecyclerView = getView(recyclerView);
        mToolbar = getView(R.id.toolbar);
        refreshLayout = getView(R.id.refresh_layout);
        jcVideoPlayerStandard = getView(R.id.videoPlay);
    }

    @Override
    protected K567DetailPresenterImpl initPresenterImpl() {
        return new K567DetailPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_k567_detail;
    }

    @Override
    public void onRefresh() {
        setStatusViewStatus(StatusLayout.SUCCESS);
        mPresenter.netWorkRequest(url);
    }

    @Override
    public void netWorkSuccess(List<MovieModel> data) {
        if (mStatusView != null) {
            if (TextUtils.isEmpty(data.get(0).playUrl)) {
                mAdapter.removeAll();
                mStatusView.setStatus(StatusLayout.ERROR);
                UIUtils.snackBar(mStatusView, R.string.video_error);
            } else {
                mAdapter.removeAll();
                mAdapter.addAllData(data);
                jcVideoPlayerStandard.setVisibility(View.VISIBLE);
                KLog.i(data.get(0).playUrl);
                jcVideoPlayerStandard.setUp(data.get(0).playUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, title);
                ImageLoaderUtils.display(jcVideoPlayerStandard.thumbImageView, imageUrl);
            }
        }
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null) {
            mAdapter.removeAll();
            setStatusViewStatus(StatusLayout.ERROR);
        }
    }

    @Override
    public void showProgress() {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (refreshLayout != null)
            refreshLayout.setRefreshing(false);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        if (jcVideoPlayerStandard != null) {
            jcVideoPlayerStandard = null;
        }
        super.onDestroy();
    }
}

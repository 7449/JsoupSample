package com.image.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.framework.base.BaseActivity;
import com.framework.base.BasePagerAdapter;
import com.framework.utils.ImageLoaderUtils;
import com.framework.utils.UIUtils;
import com.framework.widget.ExtendedViewPager;
import com.framework.widget.TouchImageView;
import com.image.R;
import com.image.manager.DBManager;
import com.image.mvp.model.ImageModel;
import com.image.mvp.presenter.ImageDetailPresenterImpl;
import com.image.mvp.view.ViewManager;

import java.util.ArrayList;
import java.util.List;


/**
 * by y on 2017/3/23
 */

public class ImageDetailActivity extends BaseActivity<ImageDetailPresenterImpl> implements ViewManager.ImageDetailView {
    private static final String URL = "URL";
    private static final String TYPE = "TYPE";
    private String type;
    private Toolbar toolbar;
    private ExtendedViewPager viewPager;
    private ContentLoadingProgressBar loadingProgressBar;
    private ImageDetailAdapter adapter;

    public static void startIntent(String type, String url) {
        Bundle bundle = new Bundle();
        bundle.putString(URL, url);
        bundle.putString(TYPE, type);
        UIUtils.startActivity(ImageDetailActivity.class, bundle);
    }

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        type = extras.getString(TYPE);

        adapter = new ImageDetailAdapter(new ArrayList<>());
        viewPager.setAdapter(adapter);

        setSupportActionBar(toolbar);
        setTitles(1, adapter.getCount());

        mPresenter.netWorkRequest(extras.getString(URL));

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setTitles(position + 1, adapter.getCount());
                invalidateOptionsMenu();
            }
        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.collection) {
                String imageUrl = adapter.getUrl(viewPager.getCurrentItem());
                if (TextUtils.isEmpty(imageUrl)) {
                    UIUtils.snackBar(mStatusView, R.string.collection_loading);
                } else {
                    if (DBManager.isEmpty(imageUrl)) {
                        DBManager.insert(imageUrl);
                        UIUtils.snackBar(mStatusView, R.string.collection_ok);
                    } else {
                        DBManager.clear(imageUrl);
                        UIUtils.snackBar(mStatusView, R.string.collection_deleted);
                    }
                    invalidateOptionsMenu();
                }
            }
            return true;
        });
    }

    @Override
    protected void initById() {
        toolbar = getView(R.id.toolbar);
        viewPager = getView(R.id.viewPager);
        loadingProgressBar = getView(R.id.progress_bar);
    }

    @Override
    protected ImageDetailPresenterImpl initPresenterImpl() {
        return new ImageDetailPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }


    @Override
    public void netWorkSuccess(List<ImageModel> data) {
        if (mStatusView != null) {
            adapter.addAll(data);
            setTitles(1, adapter.getCount());
            invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collection_menu, menu);
        MenuItem item = menu.findItem(R.id.collection);
        String url = adapter.getUrl(viewPager.getCurrentItem());
        if (!TextUtils.isEmpty(url)) {
            item.setIcon(DBManager.isEmpty(adapter.getUrl(viewPager.getCurrentItem())) ? R.drawable.ic_favorite_border : R.drawable.ic_favorite);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void netWorkError() {
        if (mStatusView != null)
            UIUtils.snackBar(mStatusView, R.string.network_error);
    }

    @Override
    public void showProgress() {
        if (loadingProgressBar != null)
            loadingProgressBar.show();
    }

    @Override
    public void hideProgress() {
        if (loadingProgressBar != null)
            loadingProgressBar.hide();
    }


    private void setTitles(int page, int imageSize) {
        toolbar.setTitle(type + "(" + page + "/" + imageSize + ")");
    }

    @NonNull
    @Override
    public String getType() {
        return type;
    }

    private static class ImageDetailAdapter extends BasePagerAdapter<ImageModel> {


        ImageDetailAdapter(List<ImageModel> datas) {
            super(datas);
        }

        @Override
        protected Object instantiate(ViewGroup container, int position, ImageModel data) {
            TouchImageView imageView = new TouchImageView(container.getContext());
            ImageLoaderUtils.display(imageView, data.url);
            container.addView(imageView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            return imageView;
        }
    }
}

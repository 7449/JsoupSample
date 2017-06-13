package com.movie.mvp.presenter;

import android.support.annotation.NonNull;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.K567JsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/6/10.
 */

public class K567DetailPresenterImpl extends BasePresenterImpl<List<MovieModel>, ViewManager.K567DetailView> implements PresenterManager.K567DetailPresenter {


    public K567DetailPresenterImpl(ViewManager.K567DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(@NonNull String url) {
        netWork(url);
    }


    @Override
    public List<MovieModel> getT(Document document) {
        return K567JsoupManager.get(document).getDetail();
    }
}

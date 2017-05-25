package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.Dytt2018JsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class Dy2018DetailPresenterImpl extends BasePresenterImpl<MovieModel, ViewManager.Dy2018DetailView> implements PresenterManager.Dy2018DetailPresenter {

    public Dy2018DetailPresenterImpl(ViewManager.Dy2018DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public MovieModel getT(Document document) {
        return Dytt2018JsoupManager.get(document).getDy2018Detail();
    }
}

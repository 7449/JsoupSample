package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/23
 */

public class DyttVideoDetailPresenterImpl
        extends BasePresenterImpl<MovieModel, ViewManager.DyttVideoDetailView>
        implements PresenterManager.DyttVideoDetailPresenter {

    public DyttVideoDetailPresenterImpl(ViewManager.DyttVideoDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public MovieModel getT(Document document) {
        return DyttJsoupManager.get(document).getDyttVideoDetail();
    }
}

package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.XiaoPianJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class XiaoPianDetailPresenterImpl extends BasePresenterImpl<MovieModel, ViewManager.XiaoPianDetailView> implements PresenterManager.XiaoPianDetailPresenter {

    public XiaoPianDetailPresenterImpl(ViewManager.XiaoPianDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public MovieModel getT(Document document) {
        return XiaoPianJsoupManager.get(document).getXiaoPianDetail();
    }
}

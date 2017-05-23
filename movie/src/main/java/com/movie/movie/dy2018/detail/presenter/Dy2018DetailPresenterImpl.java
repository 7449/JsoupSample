package com.movie.movie.dy2018.detail.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.Dytt2018JsoupManager;
import com.movie.movie.dy2018.detail.model.Dy2018DetailModel;
import com.movie.movie.dy2018.detail.view.Dy2018DetailView;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class Dy2018DetailPresenterImpl extends PresenterImplCompat<Dy2018DetailModel, Dy2018DetailView> implements Dy2018DetailPresenter {

    public Dy2018DetailPresenterImpl(Dy2018DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public Dy2018DetailModel getT(Document document) {
        return Dytt2018JsoupManager.get(document).getDy2018Detail();
    }
}

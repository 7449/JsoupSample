package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.Dytt2018JsoupManager;
import com.movie.manager.PiaoHuaJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class VideoDetailPresenterImpl extends BasePresenterImpl<MovieModel, ViewManager.Dy2018DetailView> implements PresenterManager.VideoDetailPresenter {

    private String url;

    public VideoDetailPresenterImpl(ViewManager.Dy2018DetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        this.url = url;
        netWork(url);
    }

    @Override
    public MovieModel getT(Document document) {
        if (url != null && url.contains("www.piaohua.com")) {
            return PiaoHuaJsoupManager.get(document).getDetail();
        }
        return Dytt2018JsoupManager.get(document).getDy2018Detail();
    }
}

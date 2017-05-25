package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.PiaoHuaJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/3/24.
 */

public class PiaoHuaDetailPresenterImpl extends BasePresenterImpl<MovieModel, ViewManager.PiaoHuaDetailView> implements PresenterManager.PiaoHuaDetailPresenter {

    public PiaoHuaDetailPresenterImpl(ViewManager.PiaoHuaDetailView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public MovieModel getT(Document document) {
        return PiaoHuaJsoupManager.get(document).getDetail();
    }
}

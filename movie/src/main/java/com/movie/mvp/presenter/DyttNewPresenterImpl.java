package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttNewPresenterImpl extends BasePresenterImpl<List<MovieModel>, ViewManager.DyttNewView> implements PresenterManager.DyttNewPresenter {


    public DyttNewPresenterImpl(ViewManager.DyttNewView view) {
        super(view);
    }

    @Override
    public void netWorkRequest() {
        netWork(ApiConfig.DYTT_URL);
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttNewList();
    }
}

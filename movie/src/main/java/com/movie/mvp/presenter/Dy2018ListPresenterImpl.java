package com.movie.mvp.presenter;

import com.framework.base.mvp.BasePresenterImpl;
import com.movie.manager.ApiConfig;
import com.movie.manager.Dytt2018JsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class Dy2018ListPresenterImpl extends BasePresenterImpl<List<MovieModel>, ViewManager.Dy2018ListView> implements PresenterManager.Dy2018ListPresenter {

    public Dy2018ListPresenterImpl(ViewManager.Dy2018ListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        netWork(getDy2018Url(type, page));
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return Dytt2018JsoupManager.get(document).getDy2018List();
    }

    private String getDy2018Url(int type, int page) {
        if (page == 1) {
            return ApiConfig.DY_2018_URL + type;
        } else {
            return String.format(ApiConfig.DY_2018_URL + type + "/index_%s.html", page);
        }
    }
}

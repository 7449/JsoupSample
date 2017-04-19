package com.movie.movie.dy2018.list.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.ApiConfig;
import com.movie.manager.Dytt2018JsoupManager;
import com.movie.movie.dy2018.list.model.Dy2018ListModel;
import com.movie.movie.dy2018.list.view.Dy2018ListView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24.
 */

public class Dy2018ListPresenterImpl extends PresenterImplCompat<List<Dy2018ListModel>, Dy2018ListView> implements Dy2018ListPresenter {

    public Dy2018ListPresenterImpl(Dy2018ListView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(int type, int page) {
        netWork(getDy2018Url(type, page));
    }

    @Override
    public List<Dy2018ListModel> getT(Document document) {
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

package com.movie.mvp.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.ApiConfig;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/23
 */

public class DyttChosenPresenterImpl extends PresenterImplCompat<List<MovieModel>, ViewManager.DyttChosenView>
        implements PresenterManager.DyttChosenPresenter {


    public DyttChosenPresenterImpl(ViewManager.DyttChosenView view) {
        super(view);
    }

    @Override
    public void netWorkRequest() {
        netWork(ApiConfig.DYTT_URL);
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttChosenList();
    }
}

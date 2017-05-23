package com.movie.mvp.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.DyttJsoupManager;
import com.movie.mvp.model.MovieModel;
import com.movie.mvp.view.ViewManager;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttXLMorePresenterImpl extends PresenterImplCompat<List<MovieModel>, ViewManager.DyttXLMoreView> implements PresenterManager.DyttXLMorePresenter {

    public DyttXLMorePresenterImpl(ViewManager.DyttXLMoreView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public List<MovieModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttMoreXLList();
    }
}

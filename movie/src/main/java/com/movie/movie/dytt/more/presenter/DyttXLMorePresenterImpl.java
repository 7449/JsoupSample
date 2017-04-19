package com.movie.movie.dytt.more.presenter;

import com.framework.base.mvp.PresenterImplCompat;
import com.movie.manager.DyttJsoupManager;
import com.movie.movie.dytt.more.model.DyttXLMoreModel;
import com.movie.movie.dytt.more.view.DyttXLMoreView;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/3/24
 */

public class DyttXLMorePresenterImpl extends PresenterImplCompat<List<DyttXLMoreModel>, DyttXLMoreView> implements DyttXLMorePresenter {

    public DyttXLMorePresenterImpl(DyttXLMoreView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String url) {
        netWork(url);
    }

    @Override
    public List<DyttXLMoreModel> getT(Document document) {
        return DyttJsoupManager.get(document).getDyttMoreXLList();
    }
}

package com.fiction.mvp.presenter;

import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupPtFictionHomeManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 03/07/2017.
 */

public class PtFictionHomePresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.PtFictionHomeView> implements PresenterManager.PtFictionHomePresenter {

    public PtFictionHomePresenterImpl(ViewManager.PtFictionHomeView view) {
        super(view);
    }

    @Override
    public void netWorkRequest() {
        netWork(ApiConfig.PIAO_TIAN_URL);
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupPtFictionHomeManager.get(document).getHomeList();
    }
}

package com.fiction.mvp.presenter;

import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionHomeManager;
import com.fiction.mvp.model.FictionModel;
import com.fiction.mvp.view.ViewManager;
import com.framework.base.mvp.BasePresenterImpl;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class FictionHomePresenterImpl extends BasePresenterImpl<List<FictionModel>, ViewManager.FictionHomeView> implements PresenterManager.FictionHomePresenter {

    public FictionHomePresenterImpl(ViewManager.FictionHomeView view) {
        super(view);
    }

    @Override
    public void netWorkRequest(String type) {
        switch (type) {
            case ApiConfig.Type.ZW_81:
                netWork(ApiConfig.ZW81_URL);
                break;
            case ApiConfig.Type.KSW:
                netWork(ApiConfig.KSW_URL);
                break;
            case ApiConfig.Type.BI_QU_GE:
                netWork(ApiConfig.BI_QU_GE_URL);
                break;
        }
    }

    @Override
    public List<FictionModel> getT(Document document) {
        return JsoupFictionHomeManager.get(document).getKswHome();
    }
}

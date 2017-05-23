package com.fiction.fiction.home.presenter;

import com.fiction.fiction.home.model.FictionHomeModel;
import com.fiction.fiction.home.view.FictionHomeView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupFictionHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class FictionHomePresenterImpl extends PresenterImplCompat<List<FictionHomeModel>, FictionHomeView> implements FictionHomePresenter {

    public FictionHomePresenterImpl(FictionHomeView view) {
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
    public List<FictionHomeModel> getT(Document document) {
        return JsoupFictionHomeManager.get(document).getKswHome();
    }
}

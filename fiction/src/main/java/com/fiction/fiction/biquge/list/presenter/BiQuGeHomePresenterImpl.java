package com.fiction.fiction.biquge.list.presenter;

import com.fiction.fiction.biquge.list.model.BiQuGeHomeModel;
import com.fiction.fiction.biquge.list.view.BiQuGeHomeView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class BiQuGeHomePresenterImpl extends PresenterImplCompat<List<BiQuGeHomeModel>, BiQuGeHomeView> implements BiQuGeHomePresenter {

    public BiQuGeHomePresenterImpl(BiQuGeHomeView view) {
        super(view);
    }

    @Override
    public void netWork() {
        netWork(ApiConfig.BI_QU_GE_URL);
    }

    @Override
    public List<BiQuGeHomeModel> getT(Document document) {
        return JsoupBiQuGeHomeManager.get(document).getbiqugeHome();
    }
}

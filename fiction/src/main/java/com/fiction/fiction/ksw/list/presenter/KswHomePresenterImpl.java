package com.fiction.fiction.ksw.list.presenter;

import com.fiction.fiction.ksw.list.model.KswHomeModel;
import com.fiction.fiction.ksw.list.view.KswHomeView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupKswHomeManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

import java.util.List;

/**
 * by y on 2017/4/20
 */

public class KswHomePresenterImpl extends PresenterImplCompat<List<KswHomeModel>, KswHomeView> implements KswHomePresenter {

    public KswHomePresenterImpl(KswHomeView view) {
        super(view);
    }

    @Override
    public void netWork() {
        netWork(ApiConfig.KSW_URL);
    }

    @Override
    public List<KswHomeModel> getT(Document document) {
        return JsoupKswHomeManager.get(document).getKswHome();
    }
}

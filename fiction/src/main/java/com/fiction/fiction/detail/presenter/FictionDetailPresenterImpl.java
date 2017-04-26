package com.fiction.fiction.detail.presenter;

import com.fiction.fiction.detail.model.FictionDetailModel;
import com.fiction.fiction.detail.view.FictionDetailView;
import com.fiction.manager.ApiConfig;
import com.fiction.manager.JsoupBiQuGeListManager;
import com.fiction.manager.JsoupKswListManager;
import com.fiction.manager.JsoupZwListManager;
import com.framework.base.mvp.PresenterImplCompat;

import org.jsoup.nodes.Document;

/**
 * by y on 2017/1/8.
 */

public class FictionDetailPresenterImpl extends PresenterImplCompat<FictionDetailModel, FictionDetailView> implements FictionDetailPresenter {

    private String type = ApiConfig.Type.ZW_81;

    public FictionDetailPresenterImpl(FictionDetailView view) {
        super(view);
    }

    @Override
    public void startDetail(String url, String type) {
        this.type = type;
        netWork(url);
    }

    @Override
    public FictionDetailModel getT(Document document) {
        switch (type) {
            case ApiConfig.Type.BI_QU_GE:
                return JsoupBiQuGeListManager.get(document).getBiqugeListDetail();
            case ApiConfig.Type.KSW:
                return JsoupKswListManager.get(document).getKswListDetail();
            case ApiConfig.Type.ZW_81:
                return JsoupZwListManager.get(document).getZwListDetail();
            default:
                return new FictionDetailModel();
        }
    }
}
